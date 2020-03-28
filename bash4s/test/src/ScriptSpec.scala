package bash4s

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._
import scripts._

object ScriptSpec extends DefaultRunnableSpec {

  val suite1 = suite("Bash Dsl in Scala")(

    test("workDir example") {

      val testDir = dirPath"/tmp/workspace/job1"

      val workDir = WorkDir(testDir) 

      workDir.script.printRich()
      workDir.script.save(os.Path("/tmp/wd"))
      pprint.pprintln(workDir.name)
//      val result = workDir.run()
 //     pprint.pprintln(result)
      assert(1)(equalTo(1))
    },

    test("test if else for bash script") {
      assert(1)(equalTo(1))
    }
  )

  def spec = suite("TestSuite for BashDsl")(suite1)

}
