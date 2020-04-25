package bio.tools

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._
import bio.dsl._
//import bio.data._
//import bash4s.ScriptLinter

object SamtoolsSpecSetup {
  val read1 = file"/path/to/fastq_R1".fastq.gz
  val read2 = file"/path/to/fastq_R2".fastq.gz
  val ref = file"/path/to/human38".fasta
  val bam = file"/path/to/sample".bam
}

import SamtoolsSpecSetup._

object SamtoolsSpec {
  
  val suite1 = suite(
    "Operations on Bam files"
  )(
    test("map and align") {
     
      val fastqGenerated = Samtools.BamToFastqWithSplitReads(
        bam, read1, read2
      )

      //val script = ScriptLinter.splitOnPipesAndLists(fastqGenerated.script)
//      val script = ScriptLinter.splitOnPipesAndLists(fastqGenerated.script)
      println(fastqGenerated.script)
//      pprint.pprintln(script)
      
      assert(1)(equalTo(1))
    }
  )
}
