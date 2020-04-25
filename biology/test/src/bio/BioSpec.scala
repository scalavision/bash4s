package bio

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bio._
import bio.tools._
//import bio.data._
import bash4s._
import TestCases._
import bash4s.domain.IntVariable
object TestCases {

  val fastaFile = filePath"/test/hello".fasta
  val dictFile = filePath"/test/hello".dict

  val bwaIndex = Bwa.BuildIndex(fastaFile)

  val r1 = filePath"/path/to/fastq".fastq.gz
  val r2 = filePath"/path/to/fastq".fastq.gz
  val ref = filePath"/path/to/ref".fasta
  val cores = 12.cores
  val readGroupInfo = txt"@RG:Test"

  val mapAndAlign = Bwa.MapAndAlign(
    r1,
    r2,
    ref,
    cores,
    Some(readGroupInfo)
  )

  val bamAligned = filePath"/path/to/sample1".markdup.sorted.indexed.bam

}

object BioSpec extends DefaultRunnableSpec {

  val suite1 = suite("Bwa methods")(
    test("index fasta filePath") {

      val path = filePath"/test/hello".fasta
      println(path)

      pprint.pprintln(Bwa.BuildIndex(path).script)

      assert(1)(equalTo(1))
    },
    test("map and align fastq files") {

      val r1 = filePath"/path/to/fastq".fastq.gz
      val r2 = filePath"/path/to/fastq".fastq.gz
      val ref = filePath"/path/to/ref".fasta
      val cores = 12.cores
      val readGroupInfo = txt"@RG:Test"

      val mapAndAlign = Bwa.MapAndAlign(
        r1,
        r2,
        ref,
        cores,
        Some(readGroupInfo)
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

      val vcfOutput = filePath"/path/to/output".vcf
//      val bamOutput =filePath"/path/bam/haplotyped".bam

      //val haplotypeCaller = Gatk.HaplotypeCallerBasic(bamAligned, ref, vcfOutput, Some(bamOutput))
      val haplotypeCaller = Gatk.HaplotypeCallerBasic(bamAligned, ref, vcfOutput)
      pprint.pprintln(haplotypeCaller.script)
     
      val vcfToAnnotate = filePath"/path/to/vcf_to_annotate".vcf
      val annotatedVcf = filePath"/path/to/annotated".cnn2d.vcf

      val cnnAnno = Gatk.CNNScoreVariants1D(vcfToAnnotate, ref, annotatedVcf)
      pprint.pprintln(cnnAnno.script)

      val cnnAnno2D = Gatk.CNNScoreVariants2D(vcfToAnnotate, ref, annotatedVcf, IntVariable(2), IntVariable(2), txt"read-sensor")      
      pprint.pprintln(cnnAnno2D.script)

      val trancheFilteredVcf = filePath"/path/to/trancheFiltered".vcf

      val omni1000G = filePath"/path/to/1000G_omni2.5.b37.sites".vcf
      val hapmap = filePath"/path/to/hapmap_3.3.b37.sites".vcf

      val filterVariantTranches = Gatk.FilterVariantTranches(
        annotatedVcf,
        txt"CNN_2D",
        txt"99.9",
        txt"99.5",
        trancheFilteredVcf,
        true,
        omni1000G,
        hapmap 
      )

      pprint.pprintln(filterVariantTranches.script)
      
      assert(1)(equalTo(1))
    }
  )

  // BioCookbookSpec.suite1, SamtoolsSpec.suite1 BedGraphToBigWigSpec.suite1// 
 // def spec = suite("BioSpec")(bio.scripts.ScriptExpanderSpec.suite1)
  def spec = suite("BioSpec")(bio.BioCookbookSpec.suite1)
  
}
