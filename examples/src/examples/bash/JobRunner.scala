package examples.bash

import bash4s._
import domain._
import os.CommandResult
import os.SubprocessException

sealed trait JobResult
object JobResult {
  case object Success extends JobResult
  case object Error extends JobResult
}

trait JobRunner[A] {
  def setup(job: Job): Unit
  def run(job: Job): Unit
  def checkResult(job: Job): JobResult
  def cleanup(job: Job): Unit
}

//TODO: implement the workDir script in the setup function
case class OsWrapper(job: Job) {

  val workdir = os.Path(s"${job.basePath.folders.mkString("/")}")
  val scriptName = job.name + ".sh"
  val scriptFile = workdir / scriptName
  val resultFile = workdir / "RESULT"
  val errorLog = workdir / "error.log"
  val outLog = workdir / "out.log"
  val exceptionLog = workdir / "exception.log"

  // merging workdir script with job script
  val jobScript = {
    import bash4s._
    (job.workDir o
      pushd"${job.workDir.WORKFOLDER}") ++ 
    job.script o 
    popd
  }.rename(
    job.script.name
  ).script

  def run(): Unit = {

    var procResult: CommandResult = null
    val scriptBase = os.Path(job.basePath.toString())
    val scriptPath = scriptBase / job.script.name

    os.makeDir.all(scriptBase)
    os.write.over(scriptPath, jobScript, "rwxrwxrwx")

    try {
      println(s"RUNNING: ${scriptPath}")
      procResult = os.proc(scriptPath.toString()).call(cwd = scriptBase)
    } catch {
      case SubprocessException(e) =>
        os.write(resultFile, e.exitCode.toString, "r--r--r--")
        //TODO: convert Array[Byte] to String
        os.write(errorLog, e.err.bytes.toString())
        os.write(exceptionLog, e.toString())
        throw new SubprocessException(e)
    }
    println(s"Finished")
    os.write(resultFile, procResult.exitCode.toString(), "r--r--r--")
  }

  def checkResult(): JobResult = 
    os.read.lines(resultFile).head match {
      case "0" => JobResult.Success
      case "1" => JobResult.Error
    }
  
  def cleanup(): Unit =
    os.remove.all(workdir)
}

object JobRunner {

  implicit def apply[A](implicit a: JobRunner[A]): JobRunner[A] = a

  implicit def osRunner(implicit env: OsWrapper): JobRunner[OsWrapper] = new JobRunner[OsWrapper]{
    override def setup(job: Job) = println()
    override def run(job: Job) = 
      env.run()

    override def checkResult(job: Job): JobResult =
      env.checkResult()

    override def cleanup(job: Job): Unit = 
      env.cleanup()
  }

}

object ops {
  implicit class JobRunnerSyntax[A](val job: Job)(
    implicit env: JobRunner[A]
  ) {

    def run() =
      env.run(job)

    def checkResult() =
      env.checkResult(job)

    def cleanup() =
      env.cleanup(job)  
  }
}

case class Job(
  name: String,
  basePath: FolderPath,
  workDir: WorkDir,
  script: Script
)
