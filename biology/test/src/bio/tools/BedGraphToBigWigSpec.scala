package bio.tools

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._
import bio._
//import bio.data._
//import bash4s.ScriptLinter

object BedGraphToBigWigSpecSetup {
  val ref = filePath"/path/to/human38".fasta
  val bam = filePath"/path/to/sample".bam
  val bedGraph = filePath"/path/to/bedGraph".regions.bed.gz
}

import BedGraphToBigWigSpecSetup._

object BedGraphToBigWigSpec {
  
  val suite1 = suite(
    "Operations on Bam files"
  )(
    test("map and align") {
 
      val genomeFile = filePath"/path/to/human".genome
      val convertBedGraphToBigWig = BedGraphToBigWig(
        bedGraph,
        genomeFile
      )

      pprint.pprintln(convertBedGraphToBigWig.script) 
      assert(1)(equalTo(1))
    }
  )
}
