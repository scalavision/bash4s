package bio


import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bio.dsl._
import bash4s.dsl._


object TestCases {

  val path = file"/test/hello".fasta
  val bwaIndex = Bwa.BuildIndex(path)

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

}

object BioSpec extends DefaultRunnableSpec {
  import TestCases._

  val suite1 = suite("Bwa methods")(
    test("index fasta file") {
      pprint.pprintln(bwaIndex.script)
      assert(1)(equalTo(1))
    },

    test("map and align fastq files") {

      pprint.pprintln(mapAndAlign.script)

      assert(1)(equalTo(1))
    },
    
    test("combinations of scripts") {
      val all = 
        bwaIndex.op && 
          mapAndAlign.op

      pprint.pprintln(all.txt)
      
      assert(1)(equalTo(1))
    }
    
  )

  def spec = suite("BioSpec")(suite1)
  
}