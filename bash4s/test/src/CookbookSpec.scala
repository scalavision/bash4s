
package bash

// import scala.language.postfixOps
import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash._

object BashBibleSpec extends DefaultRunnableSpec {
  def spec = suite("Bash Dsl for Commands"){
    test("Test a simpel script") {


      assert(1)(equalTo(1))
    }}
  }