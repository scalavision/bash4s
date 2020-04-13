package bio.scripts

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s.dsl._
import bash4s.scripts.ScriptExpander
//import bio.dsl._
//import bio.cookbook._

object ScriptExpanderSpec {

  val suite1 = suite(
    "Expand Script for Inspection"
  )(
  test("expand bash variables") {
    val script = bio.BamFileSetup.readDepthOps
    val result = ScriptExpander(script)
    pprint.pprintln(result)
    assert(1)(equalTo(1))
  }
  )
}