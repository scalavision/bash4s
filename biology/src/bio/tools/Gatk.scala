package bio.tools

import bio.dsl._
import bash4s.dsl._
import bash4s.ScriptGenerator
import bash4s.scripts._

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
  
}