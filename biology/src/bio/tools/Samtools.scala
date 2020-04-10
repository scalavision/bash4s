package bio.tools

import bash4s.ScriptGenerator
import bio.{Cores, Sample, Memory}
import bio.dsl._
import bash4s.dsl._
import bash4s.scripts.Script
import bio.Memory

sealed trait Samtools extends Script {
  def param = ScriptGenerator.gen[Samtools](this.asInstanceOf[Samtools])
}
object Samtools {
 
  def ConvertFromSamToBam = samtools"view -Sb"

  case class Sort(
    mem: Memory,
    cores: Cores,
    bamOut: SortedBam,
    sample: Option[Sample] = None
  ) extends Samtools {

    val tempName = sample.fold(txt"tmp"){s => txt"tmp_${s.name}"}

    val MEM = Arg(param.$1(mem))
    val SAMTOOLS_SORT_CORES = Arg(param.$2(cores))
    val TMP_NAME = Arg(param.$3(tempName))
    val BAM_OUT = Arg(param.$4(bamOut))

    val env = 
      MEM o 
      SAMTOOLS_SORT_CORES o 
      TMP_NAME o 
      BAM_OUT

    override def setup = init(env)
    val op = 
      samtools"sort -m ${MEM} -o ${BAM_OUT} -T ${TMP_NAME} -@ ${SAMTOOLS_SORT_CORES}"

  }

  case class Index(
    bam: Bam
  ) extends Samtools {

    val BAM = Arg(param.$1(bam))
    
    override def setup = init(BAM) 

    val op =
      samtools"index $BAM"

  }

}