package bio


import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._


object BioSpec extends DefaultRunnableSpec {

  val suite1 = suite("Bash Dsl in Scala")(
    test("hello world") {
      assert(1)(equalTo(1))
    }
  )

  def spec = suite("BioSpec")(suite1)
  
}