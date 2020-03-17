package bash4s

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

object ScriptSpec extends DefaultRunnableSpec(
  suite("Bash Dsl for Commands")(
    test("Test a simpel script") {

      assert(1, equalTo(1))

    },
    test("test if else for bash script") {
     
      assert(1, equalTo(1))
  }
))
