package bio

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s.dsl._
import bio.dsl._
import bio.data._
//import bash4s.ScriptLinter

object BamFileSetup {
  val read1 = file"/path/to/fastq_R1".fastq.gz
  val read2 = file"/path/to/fastq_R2".fastq.gz
  val ref = file"/path/to/human38".fasta
}

import BamFileSetup._

object BioFormatSpec {

  val suite1 = suite(
    "Operations on Bam files"
  )(
    test("map and align") {
     
      val output = file"/path/to/sample".markdup.indexed.sorted.bam
      val metricsFile = file"/path/to/metrics.txt"
      
      val mapAndAlign = BamFormat.MapAndAlign(
        read1,
        read2,
        ref,
        4.cores,
        2.G,
        output, 
        None,
        metricsFile
      )

      println(mapAndAlign.script)
//      pprint.pprintln(script)
      
      pprint.pprintln(mapAndAlign.lint)
      pprint.pprintln(mapAndAlign.lint.txt)
//      pprint.pprintln(mapAndAlign.op)
      assert(1)(equalTo(1))
    }
  )

}
