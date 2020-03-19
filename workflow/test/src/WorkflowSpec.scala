package workflow

import zio.test._
import zio.clock.nanoTime
import Assertion._

//import zio.test.Assertion.{isRight, isSome,equalTo, isGreaterThanEqualTo, not, hasField}

final case class Address(country:String, city:String)
final case class User(name:String, age:Int, address: Address)

object AllSuites extends DefaultRunnableSpec {

val clockSuite = suite("clock") (
  testM("time is non-zero") {
    assertM(nanoTime)(isGreaterThan(0L))
  }
)
  val suite1 = suite("suite1") (
  testM("s1.t1") {assertM(nanoTime)(isGreaterThanEqualTo(0L))},
  testM("s1.t2") {assertM(nanoTime)(isGreaterThanEqualTo(0L))}
)
// suite1: Spec[clock.package.Clock, TestFailure[Nothing], TestSuccess] = Spec(
//   SuiteCase("suite1", zio.ZIO$Succeed@2f08ef56, None)
// )
val suite2 = suite("suite2") (
  testM("s2.t1") {assertM(nanoTime)(isGreaterThanEqualTo(0L))},
  testM("s2.t2") {assertM(nanoTime)(isGreaterThanEqualTo(0L))},
  testM("s2.t3") {assertM(nanoTime)(isGreaterThanEqualTo(0L))}
)
// suite2: Spec[clock.package.Clock, TestFailure[Nothing], TestSuccess] = Spec(
//   SuiteCase("suite2", zio.ZIO$Succeed@1a35894f, None)
// )
val suite3 = suite("suite3") (
  testM("s3.t1") {assertM(nanoTime)(isGreaterThanEqualTo(0L))},
  test("Rich checking") {
    assert(
      User("Jonny", 26, Address("Denmark", "Copenhagen"))
    )(
      hasField("age", (u:User) => u.age, isGreaterThanEqualTo(18)) &&
      hasField("country", (u:User) => u.address.country, not(equalTo("USA")))
    )
  } 
)
// suite3: Spec[clock.package.Clock, TestFailure[Nothing], TestSuccess] = Spec(
//   SuiteCase("suite3", zio.ZIO$Succeed@3a4d94fc, None)
// ) 



  def spec = suite("All tests")(suite1, clockSuite, suite2, suite3)

}