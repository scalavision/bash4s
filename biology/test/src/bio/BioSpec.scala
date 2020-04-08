package bio

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bio.dsl._
import bio.tools._
//import bio.data._
import bash4s.dsl._
import TestCases._
object TestCases {

  val fastaFile = file"/test/hello".fasta
  val dictFile = file"/test/hello".dict

  val bwaIndex = Bwa.BuildIndex(fastaFile)

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

  val bamAligned = file"/path/to/sample1".markdup.sorted.indexed.bam

}

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
    },
    
    test("combinations of scripts") {
        bwaIndex.op && 
          mapAndAlign.op
      
      assert(1)(equalTo(1))
    },

    test("fastaFormat operations") {

      /*
      val createDictionary = FastaFormat.CreateDictionary(
        fastaFile, dictFile        
      )

      val createBwaIndex = FastaFormat.CreateBwaIndex(fastaFile)

      val createFastaIndex = FastaFormat.CreateIndex(fastaFile)

      val createFastaAllIndexes = FastaFormat.CreateAllIndexes(
        fastaFile, dictFile
      )
      
      val createFastaAllIndexesInParallel = FastaFormat.CreateAllIndexesInParallel(
        fastaFile, dictFile
      )

      val workDir = dirPath"/tmp/splitChr"
      val resultFolder = dirPath"/bio/ref"
      val splitByChromosome = FastaFormat.SplitByChromosome(fastaFile, workDir, resultFolder)

      pprint.pprintln(createDictionary.script)
      pprint.pprintln(createBwaIndex.script)
      pprint.pprintln(createFastaIndex.script)
      pprint.pprintln(createFastaAllIndexes.script)
      pprint.pprintln(createFastaAllIndexesInParallel.script)
      pprint.pprintln(splitByChromosome.script)
      */

      assert(1)(equalTo(1))
    },
    test("gatk operations") {

      val vcfOutput = file"/path/to/output".vcf
//      val bamOutput =file"/path/bam/haplotyped".bam

      //val haplotypeCaller = Gatk.HaplotypeCallerBasic(bamAligned, ref, vcfOutput, Some(bamOutput))
      val haplotypeCaller = Gatk.HaplotypeCallerBasic(bamAligned, ref, vcfOutput)
      pprint.pprintln(haplotypeCaller.script)
      
      assert(1)(equalTo(1))
    }
  )

  def spec = suite("BioSpec")(suite1)
  
}
