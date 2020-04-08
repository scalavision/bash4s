package bio.tools

import bio.dsl._
import bash4s.dsl._
import bash4s.ScriptGenerator
import bash4s.scripts._
import bash4s.domain.IntVariable
import bash4s.domain.TextVariable

sealed trait Gatk extends Script {

  def param = ScriptGenerator.gen[Gatk](this.asInstanceOf[Gatk])
  
  val BAM = Var
  val REF = Var
  val OUT_VCF = Var

  def args(bam: MarkdupSortedIndexedBam, ref: Fasta) = 
    BAM `=` param.$1(bam) o
    REF `=` param.$2(ref)

}

object Gatk extends ToolMetaInfo {
  val packageName = "gatk"
  val version = "4.1.6.0"

  val HaplotypeCaller = "HaplotypeCaller"

  case class HaplotypeCallerBasic(
   bam: MarkdupSortedIndexedBam,
   reference: Fasta,
   output: Vcf,
   bamOut: Option[Bam] = None
  ) extends Gatk {

    val BAM_OUT = Array
    override def setup = init(
      args(bam, reference) o
      OUT_VCF `=` param.$3(output) o
      BAM_OUT `=` param.$4(bamOut.op, "-bamout")
    )

    def op = 
      gatk"$HaplotypeCaller -R ${REF} -I ${BAM} -O ${OUT_VCF} ${BAM_OUT}"

  }
  
  case class HaplotypeCallerGVCF(
   bam: MarkdupSortedIndexedBam,
   reference: Fasta,
   output: GVcf
  ) extends Gatk {

    override def setup = init(
      args(bam, reference) o
      OUT_VCF `=` param.$3(output)
    )

    def op = 
      gatk"$HaplotypeCaller -R ${REF} -I ${BAM} -O ${OUT_VCF}"

  }

  case class GenotypeGVCFs(
    reference: Fasta,
    variants: GVcf,
    finalVariantsOutput: Vcf
  ) extends Gatk {

    val VARIANTS = Var

    override def setup = init(
      REF `=` param.$1(reference) o
      VARIANTS `=` param.$2(variants) o
      OUT_VCF `=` param.$3(finalVariantsOutput)
    )

    def op = 
      gatk"${name} -R ${REF} -V ${VARIANTS} -O ${OUT_VCF}"

  }
 
  case class CNNScoreVariants1D(
    vcfToAnnotate: Vcf,
    reference: Fasta,
    annotatedOut: Vcf
  ) extends Gatk {

    val VCF_TO_ANNOTATE = Var
    val ANNOTATED_VCF_OUT = Var

    val initVariables = 
      VCF_TO_ANNOTATE `=` param.$1(vcfToAnnotate) o
      REF `=` param.$2(reference) o
      ANNOTATED_VCF_OUT `=` param.$3(annotatedOut)

    override def setup = init(initVariables)

    def op = gatk"${name.dropRight(2)} -V ${VCF_TO_ANNOTATE} -R ${REF} -O ${ANNOTATED_VCF_OUT}"

  }
  
  case class CNNScoreVariants2D(
    vcfToAnnotate: Vcf,
    reference: Fasta,
    annotatedOut: Vcf,
    inferenceBatchSize: IntVariable,
    transferBatchSize: IntVariable,
    tensorType: TextVariable
  ) extends Gatk {

    val cnnScore1D = CNNScoreVariants1D(vcfToAnnotate, reference, annotatedOut)

    val INFERENCE_BATCH_SIZE = Var
    val TRANSFER_BATCH_SIZE = Var
    val TENSORTYPE = Var

    override def setup = init { 
      cnnScore1D.initVariables o 
      INFERENCE_BATCH_SIZE `=` param.$4(inferenceBatchSize) o
      TRANSFER_BATCH_SIZE `=` param.$5(transferBatchSize) o
      TENSORTYPE `=` param.$6(tensorType) 
    }

    def op = gatk"""${name.dropRight(2)} \\
      -V ${cnnScore1D.VCF_TO_ANNOTATE} \\
      -R ${REF} \\
      -O ${cnnScore1D.ANNOTATED_VCF_OUT} \\
      -inference-batch-size ${INFERENCE_BATCH_SIZE} \\
      -transfer-batch-size ${TRANSFER_BATCH_SIZE} \\
      -tensor-type ${TENSORTYPE}""" 
    } 
}