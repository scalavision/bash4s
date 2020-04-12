package bio

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s.dsl._
import bio.dsl._
import bio.cookbook._

object BamFileSetup {
  val read1 = file"/path/to/fastq_R1".fastq.gz
  val read2 = file"/path/to/fastq_R2".fastq.gz
  val ref = file"/path/to/human38".fasta
  val bamFile = file"/path/to/test".bam
}

import BamFileSetup._

object BioCookbookSpec {

  val suite1 = suite(
    "Operations on Bam files"
  )(
    /*
    test("map and align") {
     
      val output = file"/path/to/sample".markdup.indexed.sorted.bam
      val metricsFile = file"/path/to/metrics.txt"
      
      val mapAndAlign = FastqOperations.MapAndAlign(
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
      assert(1)(equalTo(1))
    },
    test("merge fastq files") {

      val readsLocation = dirPath"/path/to/reads"
      
      val mergeReads = FastqOperations.MergeReads(
        readsLocation,
        read1,
        read2
      )

      pprint.pprintln(mergeReads.script)
      assert(1)(equalTo(1))

    },*/
    test("read depth stats and bigWig conversion") {

      val readDepthOps = BamOperations.ReadDepthStatsAndVisualisation(
        bamFile, ref, 4.cores, Sample("HG002"), 50
      )

      pprint.pprintln(readDepthOps.script)

      assert(1)(equalTo(1))
    }
  )

}
