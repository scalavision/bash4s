package bio.tools

import bio._
import bio.Sample
import bash4s._
import bash4s.domain._
import bash4s.ScriptGenerator
import bash4s.scripts._
import bash4s.domain.IntVariable
import bash4s.domain.TextVariable

sealed trait Gatk extends Script {

  def param = ScriptGenerator.gen[Gatk](this.asInstanceOf[Gatk])
  
  val BAM = Var
  val REF = Var
  val OUT_VCF = Var

  def setup(bam: MarkdupIndexedSortedBam, ref: Fasta) = 
    BAM `=` param.$1(bam) o
    REF `=` param.$2(ref)
}

object Gatk { 

  val packageName = "gatk"
  val version = "4.1.6.0"

  val HaplotypeCaller = "HaplotypeCaller"

  case class MarkDuplicates(
    bam: IndexedSortedBam,
    bamOut: MarkdupIndexedSortedBam,
    metricsFile: FilePath,
    tmpDir: Option[FolderPath]
  ) extends Gatk {

    val MD_BAM_INPUT = Arg(param.$1(bam))
    val MD_BAM_OUTPUT = Arg(param.$2(bamOut))
    val METRICS = Arg(param.$3(metricsFile))
    val MD_TMPDIR = BArray

    override def args = 
      MD_BAM_INPUT o
      MD_BAM_OUTPUT o
      METRICS o
      MD_TMPDIR `=` param.$4(tmpDir, "--TMP_DIR")

    def op = 
      gatk"$name -I $MD_BAM_INPUT -O $MD_BAM_OUTPUT -M $METRICS $MD_TMPDIR"

  }

  case class HaplotypeCallerBasic(
   bam: MarkdupIndexedSortedBam,
   reference: Fasta,
   output: Vcf,
   bamOut: Option[Bam] = None
  ) extends Gatk {

    val BAM_OUT = BArray
    override def args =
      setup(bam, reference) o
      OUT_VCF `=` param.$3(output) o
      BAM_OUT `=` param.$4(bamOut.op, "-bamout")

    def op = 
      gatk"$HaplotypeCaller -R ${REF} -I ${BAM} -O ${OUT_VCF} ${BAM_OUT}"

  }
  
  case class HaplotypeCallerGVCF(
   bam: MarkdupIndexedSortedBam,
   reference: Fasta,
   output: GVcf
  ) extends Gatk {

    override def args =
      setup(bam, reference) o
      OUT_VCF `=` param.$3(output)

    def op = 
      gatk"$HaplotypeCaller -R ${REF} -I ${BAM} -O ${OUT_VCF} -ERC GVCF"

  }

  case class GenotypeGVCFs(
    reference: Fasta,
    variants: GVcf,
    finalVariantsOutput: Vcf
  ) extends Gatk {

    val VARIANTS = Var

    override def args =
      REF `=` param.$1(reference) o
      VARIANTS `=` param.$2(variants) o
      OUT_VCF `=` param.$3(finalVariantsOutput)

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

    override def args = 
      VCF_TO_ANNOTATE `=` param.$1(vcfToAnnotate) o
      REF `=` param.$2(reference) o
      ANNOTATED_VCF_OUT `=` param.$3(annotatedOut)

    def op = gatk"${name.dropRight(2)} -V ${VCF_TO_ANNOTATE} -R ${REF} -O ${ANNOTATED_VCF_OUT}"

  }
  
  case class CNNScoreVariants2D(
    vcfToAnnotate: Vcf,
    reference: Fasta,
    annotatedOut: CnnScored2DVcf,
    inferenceBatchSize: IntVariable,
    transferBatchSize: IntVariable,
    tensorType: TextVariable
  ) extends Gatk {

    val VCF_TO_ANNOTATE = Arg(param.$1(vcfToAnnotate))
    val ANNOTATED_VCF_OUT = Arg(param.$3(annotatedOut))
    val INFERENCE_BATCH_SIZE = Arg(param.$4(inferenceBatchSize))
    val TRANSFER_BATCH_SIZE = Arg(param.$5(transferBatchSize))
    val TENSORTYPE = Arg(param.$6(tensorType))

    override def args = 
      VCF_TO_ANNOTATE o
      REF `=` param.$2(reference) o
      ANNOTATED_VCF_OUT o
      INFERENCE_BATCH_SIZE o
      TRANSFER_BATCH_SIZE o
      TENSORTYPE
      
    def op = gatk"""${name.dropRight(2)} \\
      -V ${VCF_TO_ANNOTATE} \\
      -R ${REF} \\
      -O ${ANNOTATED_VCF_OUT} \\
      -inference-batch-size ${INFERENCE_BATCH_SIZE} \\
      -transfer-batch-size ${TRANSFER_BATCH_SIZE} \\
      -tensor-type ${TENSORTYPE}""" 
    } 

    case class FilterVariantTranches(
      cnnScored2D: CnnScored2DVcf,
      infoKey: TextVariable,
      snpTranche: TextVariable,
      indelTranche: TextVariable,
      filtered2DOutput: Vcf,
      invalidatePreviousFilters: Boolean,
      resources: Vcf*
    ) extends Gatk {

      val CNN_SCORED_2D = Arg(param.$1(cnnScored2D))
      val INFO_KEY = Arg(param.$3(infoKey))
      val SNP_TRANCHE = Arg(param.$4(snpTranche))
      val INDEL_TRANCHE = Arg(param.$5(indelTranche))
      val FILTERED_2D_OUTPUT = Arg(param.$6(filtered2DOutput))
      val RESOURCES = BArray
      val INVALIDATE_PREVIOUS_FILTERS = BArray 
    
      override def args =
        CNN_SCORED_2D o
        RESOURCES `=` param.$2(resources.op, "--resources") o
        INFO_KEY  o
        SNP_TRANCHE o
        INDEL_TRANCHE o
        FILTERED_2D_OUTPUT o
        INVALIDATE_PREVIOUS_FILTERS `=` param.$7(invalidatePreviousFilters, "--invalidate-previous-filters")

      def op = 
        gatk"""${name} \\
          -V ${CNN_SCORED_2D} \\
          ${RESOURCES} \\
          --info-key ${INFO_KEY} \\
          --snp-tranche ${SNP_TRANCHE} \\
          --indel-tranche ${INDEL_TRANCHE} \\
          -O ${FILTERED_2D_OUTPUT} \\
          ${INVALIDATE_PREVIOUS_FILTERS}
        """
    }


  case class CalculateGenotypePosteriors (
    ref: Fasta,
    input: Vcf,
    family: Ped,
    population: Vcf,
    output: Vcf
  ) extends Gatk {


    val INPUT = Arg(param.$2(input))
    val FAMILY = Arg(param.$3(family))
    val POPULATION = Arg(param.$4(population))
    val OUTPUT = Arg(param.$5(output))

    override def args =
      REF `=` param.$1(ref) o
      INPUT o
      FAMILY o
      POPULATION o
      OUTPUT

    def op = 
      gatk"${name} -R $REF -V $INPUT -ped $FAMILY -supporting $POPULATION -O $OUTPUT"

  }

  case class VariantFiltration(
    ref: Fasta,
    input: Vcf,
    genotypeFilterExpression: TextVariable,
    genotypeFilterName: TextVariable,
    output: Vcf
  ) extends Gatk {

    val INPUT = Arg(param.$2(input))
    val FILTER_EXPRESSION = Arg(param.$3(genotypeFilterExpression))
    val FILTER_NAME = Arg(param.$4(genotypeFilterName))
    val OUTPUT = Arg(param.$5(output))

    override def args =
      REF `=` param.$1(ref)  o
      INPUT o
      FILTER_EXPRESSION o
      FILTER_NAME o
      OUTPUT

    def op = 
      gatk"${name} -R $REF -V $INPUT --genotype-filter-expression $FILTER_EXPRESSION --genotype-filter-name $FILTER_NAME -O $OUTPUT"
  }

  def FilterLowConfidenceGQ(
    ref: Fasta,
    input: Vcf,
    output: Vcf
  ) = 
  VariantFiltration(ref, input, txt"GC<20", txt"lowGQ", output)

  case class VariantEval(
    ref: Fasta,
    eval: Vcf,
    truthSet: Vcf,
    output: Vcf
  ) extends Gatk {

    val EVAL = Arg(param.$2(eval))
    val TRUTHSET = Arg(param.$3(truthSet))
    val OUTPUT = Arg(param.$4(output))

    override def args =
      REF `=` param.$1(ref) o
      EVAL o
      TRUTHSET o
      OUTPUT

    def op = 
      gatk"$name -R $REF -eval $EVAL --comp $TRUTHSET -o $OUTPUT"

  }

  case class GenotypeConcordance(
    call: Vcf,
    sample: Sample,
    truth: Vcf, 
    output: Vcf,
    truthSample: Option[Sample] = None
  ) extends Gatk {
   
    val CALL_VCF = Arg(param.$1(call))
    val SAMPLE = Arg(param.$2(sample))
    val TRUTH_VCF = Arg(param.$3(truth))
    val OUTPUT = Arg(param.$4(output))
    val TRUTH_SAMPLE = Arg(param.$5(truthSample))

    def op = gatk"${name}"

  }
}
