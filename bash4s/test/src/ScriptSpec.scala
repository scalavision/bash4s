package bash4s

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._
//import zio.test.TestAspect._

//import domain._
import bash4s._
import scripts._

object TargetScript{
  def apply(scriptName: String): String =
    os.read(
      os.pwd / "bash4s" / "test" / "resources" / s"$scriptName"
    ).linesIterator.toList.mkString("\n") + "\n"
}

object ScriptSpec extends DefaultRunnableSpec {

  val testDir = dirPath"/tmp/workspace/job1"
  val workDir = WorkDir(testDir) 

  val suite1 = suite("Bash Dsl in Scala")(

    test("workDir example") {
      assert(workDir.txt)(equalTo(TargetScript("WorkDir")))
    },

    test("test if else for bash script") {
      assert(1)(equalTo(1))
    }
  )

  val suite2 = suite("Bash script generator")(

    test("generate script with support for arguments") {
      pprint.pprintln(workDir.script)
      assert(1)(equalTo(1))
    }

  )

  def spec = suite("TestSuite for BashDsl")(suite2)

}
