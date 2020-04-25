package bio.scripts

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._
//import bash4s.scripts.ScriptExpander
import bash4s.scripts.ScriptIdentity
import bash4s.scripts.ScriptExpander
//import bio.dsl._
//import bio.cookbook._

object ScriptExpanderSetup {
  val script = bio.BamFileSetup.readDepthOps
}

import ScriptExpanderSetup._
object ScriptExpanderSpec {

  val suite1 = suite(
    "Expand Script for Inspection"
  )(
    test("identity function should not change anything") {
      val result = ScriptIdentity.id(script.op)
      assert(result)(equalTo(script.op))
    },
    test("expand BashVariables only") {
      val vars = ScriptExpander.extractBashVariables(script.args)
      val result = ScriptExpander.expandBashVariable(script.op, vars)
      pprint.pprintln(result.txt)
      pprint.pprintln(script.script)
      assert(1)(equalTo(1))
    }
  )
}