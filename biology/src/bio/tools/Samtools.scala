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

  case class BamToFastq(
    bam: Bam
  ) extends Samtools {

    val BAM_FILE = Arg(param.$1(bam))
    val env = BAM_FILE 
    override def setup = init(env)
  
    // Prints to stdout
    def op = samtools"bam2fq ${BAM_FILE}" 
    
  }

  case class BamToFastqWithSplitReads(
    bam: Bam,
    read1: Read1,
    read2: Read2
  ) extends Samtools {

    val bamToFastq = BamToFastq(bam)
    val tmpFile = relFile"./tmp".fastq
    val tmpRead1 = read1.dropLastExtension
    val tmpRead2 = read1.dropLastExtension

    val TMP_FASTQ = Arg(param.$2(tmpFile))
    val TMP_READ1 = Arg(param.$3(tmpRead1))
    val TMP_READ2 = Arg(param.$3(tmpRead2))
    
    override def setup = init(
      bamToFastq.env o 
      TMP_FASTQ o
      TMP_READ1 o
      TMP_READ2
    )

    def op = bamToFastq.op > tmpFile &&
      (cat"${tmpFile}" | grep"'^@.*/1$$' -A 3 --no-group-separator '" > TMP_READ1.$) &&
      (cat"${tmpFile}" | grep"'^@.*/2$$' -A 3 --no-group-separator '" > TMP_READ2.$) &&
      bgzip"${TMP_READ1}" &&
      bgzip"${TMP_READ2}" &&
      rm"$TMP_READ1" && 
      rm"$TMP_READ2" &&
      rm"${TMP_FASTQ}"

  }

}