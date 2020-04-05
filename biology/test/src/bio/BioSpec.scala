package bio


import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bio.dsl._
import bash4s.dsl._

object BioSpec extends DefaultRunnableSpec {

  val suite1 = suite("Bwa methods")(
    test("index fasta file") {

      val path = file"/test/hello".fasta
      println(path)

      pprint.pprintln(Bwa.BuildIndex(path).script)

      assert(1)(equalTo(1))
    },
    test("map and align fastq files") {

      val r1 = file"/path/to/fastq".fastq.gz
      val r2 = file"/path/to/fastq".fastq.gz
      val ref = file"/path/to/ref".fasta
      val cores = 12.cores
      val readGroupInfo = txt"@RG:Test"

      val mapAndAlign = Bwa.MapAndAlign(
        r1,
        r2,
        ref,
        cores,
        readGroupInfo
      )

      pprint.pprintln(mapAndAlign.script)

      assert(1)(equalTo(1))
    }
  )

  def spec = suite("BioSpec")(suite1)
  
}