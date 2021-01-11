package bio.tools

import bash4s.ScriptGenerator
import bio.{Cores, Sample, Memory}
import bio._
import bash4s._
import bash4s.scripts.Script

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
    val ST_SORTED_BAM_OUT = Arg(param.$4(bamOut))

    override def args = 
      MEM o 
      SAMTOOLS_SORT_CORES o 
      TMP_NAME o 
      ST_SORTED_BAM_OUT

    val op = 
      samtools"sort -m ${MEM} -o ${ST_SORTED_BAM_OUT} -T ${TMP_NAME} -@ ${SAMTOOLS_SORT_CORES}"

  }

  case class Index(
    bam: Bam
  ) extends Samtools {

    val BAMFILE_TO_INDEX= Arg(param.$1(bam))
   
    override def args = BAMFILE_TO_INDEX
    val op =
      samtools"index $BAMFILE_TO_INDEX"

  }

  case class BamToFastq(
    bam: Bam
  ) extends Samtools {

    val BAM_FILE = Arg(param.$1(bam))
    override def args = BAM_FILE 
  
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

    val TMP_FASTQ = Arg(param.$2(tmpFile))
    val READ1 = Arg(param.$3(read1))
    val READ2 = Arg(param.$3(read2))
    val TMP_READ1 = Var
    val TMP_READ2 = Var
    
    override def args = 
      bamToFastq.args o 
      TMP_FASTQ o
      READ1 o
      READ2

    def op = 
      __#"removing the .gz part of the *.fastq.gz extension" o
      TMP_READ1 `=` $"{$READ1%.*}"                           o
      TMP_READ2 `=` $"{$READ2%.*}"                           o
      bamToFastq.op > tmpFile.fileType &&
      (cat"${tmpFile.fileType}" | grep"'^@.*/1$$' -A 3 --no-group-separator" > TMP_READ1.$) &&
      (cat"${tmpFile.fileType}" | grep"'^@.*/2$$' -A 3 --no-group-separator" > TMP_READ2.$) &&
      bgzip"${TMP_READ1}" &&
      bgzip"${TMP_READ2}" &&
      rm"${TMP_FASTQ}"

  }

}
