package bio

import zio.test.{assert, suite, test}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash4s._
import bio._
import bio.cookbook._

object BamFileSetup {
  val read1 = file"/path/to/fastq_R1".fastq.gz
  val read2 = file"/path/to/fastq_R2".fastq.gz
  val ref = file"/path/to/human38".fasta
  val bamFile = file"/path/to/test".bam
  val genomeFile = file"/path/to/human".genome

  val readDepthOps = BamOperations.ReadDepthStatsAndVisualisation(
    bamFile, ref, 4.cores, Sample("HG002"), genomeFile, 50
  )
}

object SharpTest {

 val path = "/stash/usit/cluster/projects/p22/dev/p22-tomegil/data/workdata"
 val bamFile = file"${path}/DownSmp_Flex300".bam
 val genomeFile = file"${path}/human".genome
 val ref = file"/stash/usit/cluster/projects/p22/dev/p22-tomegil/ref/hum37/human_g1k_v37_decoy".fasta

 val readDepthOps = BamOperations.ReadDepthStatsAndVisualisation(
   bamFile, ref, 2.cores, Sample("DownSmp_Flex300"), genomeFile, 50
 )

}

//import BamFileSetup._
import SharpTest._

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


      pprint.pprintln(readDepthOps.script)

      assert(1)(equalTo(1))
    }
  )

}
