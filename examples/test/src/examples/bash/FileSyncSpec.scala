package examples.bash

import bash4s._
import bash4s.io._
import examples.bash._
import examples.bash.ops._

import zio.test._
import zio.test.Assertion._

object FileSyncSpec {

  // import scala.language.implicitConversions
  // implicit def convertFolderPathToOsPath(folder: domain.FolderPath): os.Path =
  //   os.Path(folder.toString())

  val baseDir = dirPath"/tmp/scripts"
  val jobName = "test1"
  val testDir = dirPath"/tmp/workspace/$jobName"
  
  val workDir: WorkDir = WorkDir(testDir).asInstanceOf[WorkDir]
  //val script = workDir.script
  val dataDir = dirPath"/tmp/fm"
  val fm = filePath"$dataDir/move.me"
  val ft = filePath"$dataDir/moved"

  
  def setupFileSync(): Unit = {
    println("setting up files")
    os.makeDir.all(dataDir)
    os.write(fm, "hello world 2")
  }

  def fileMovedProperly(): Boolean = 
    !os.exists(fm) && os.exists(ft)
  
  def suite1 = suite(
    "Testing filesync")(
      zio.test.test("sync a simple file"){
        setupFileSync()
        val mvFile = FileSync(
          fm, ft
        )

      pprint.pprintln(filePath"$dataDir/move.me")

      val job = Job("move_files", baseDir, workDir, mvFile)
      implicit val runner = OsWrapper(job)
      
        job.run()
        job.checkResult() match {
          case JobResult.Success => 
            println(s"SUCCESS: ${job.name}")
            job.cleanup()
          case JobResult.Error =>
            println(
              s"""|Job Failed, check out why:
                  |${runner.workdir}
              |""".stripMargin
            )
        }
      assert(fileMovedProperly())(equalTo(true))
    }
  )

}
