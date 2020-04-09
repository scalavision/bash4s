package bio

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s.dsl._
import bio.dsl._
import bio.data._
import bash4s.ScriptLinter

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
      
      val mapAndAlign = BamFormat.MapAndAlign(
        read1,
        read2,
        ref,
        4.cores,
        2.G,
        file"/path/to/sample".markdup.indexed.sorted.bam,
        None
      )

      pprint.pprintln(ScriptLinter.splitLongLines(mapAndAlign.script))
      
      assert(1)(equalTo(1))
    }
  )

}
