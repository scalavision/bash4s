package bash4s

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._

object ScriptIOSpecSetup {

  val testPath = dirPath"/tmp/bash4s/test"
  val testFile =  testPath / fileName"test.txt"

  def testSetup() = 
    (mkdir"-p $testPath" &&
     touch"$testFile").run()

  def testExit() =
    rm"-rf $testPath".run()
  
}

import ScriptIOSpecSetup._

object ScriptIOSpec {

  val suite1 = suite("Simple script run support") {
   
    test("capture output from process as a Vector[String]") {
      testSetup()
      val captureOutput =  ls"-halt ${testPath}".lines()
      pprint.pprintln(captureOutput)
      testExit()
      assert(1)(equalTo(1))
    }
  }

}