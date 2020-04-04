package bio


import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bio._
//import bash4s._

object BioSpec extends DefaultRunnableSpec {

  val suite1 = suite("Bwa methods")(
    test("index fasta file") {

      val path = file"/test/hello".fasta
      println(path)

      pprint.pprintln(Bwa.BuildIndex(path).script)

      assert(1)(equalTo(1))
    }
  )

  def spec = suite("BioSpec")(suite1)
  
}