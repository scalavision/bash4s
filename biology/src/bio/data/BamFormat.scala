package bio.data

import bash4s.domain._
import bash4s.dsl._
import bio.dsl._
import bio.tools._
import bio.Cores
import bio.Memory
import bash4s.scripts.Script
import bash4s.ScriptGenerator
import bash4s.domain.TextVariable

sealed trait BamFormat extends Script {
  def param = ScriptGenerator.gen[BamFormat](this.asInstanceOf[BamFormat])
}

object BamFormat {

  case class MapAndAlign(
    read1: Read1,
    read2: Read2,
    ref: Fasta,
    cores: Cores,
    mem: Memory,
    bamOut: MarkdupIndexedSortedBam,
    readGroupInfo: Option[TextVariable],
    metricsFile: FilePath,
    tmpDir: Option[FolderPath] = None
  ) extends BamFormat {

    val bwaMem = Bwa.MapAndAlign(read1, read2, ref, cores, readGroupInfo)
    val sorted = relFile"./temporary_sorted".sorted.bam
    val sortedAndIndexed = relFile"./temporary_indexed".indexed.sorted.bam

    val bamSort = 
      Samtools.Sort(mem, cores.copy(value = if(cores.value > 4) 4 else cores.value), sorted)

    val markDup = Gatk.MarkDuplicates(
        sortedAndIndexed,
        bamOut,
        metricsFile,
        tmpDir
      )

    override def setup = init(
      bwaMem.env o 
      bamSort.env o 
      markDup.env
    )
    def op = 
      (bwaMem.op | 
        Samtools.ConvertFromSamToBam.- |
        bamSort.op.-
      ) && Samtools.Index(sorted).op &&
      mv"${sorted.fileType} ${sortedAndIndexed.fileType}" &&
      markDup.op


  }

}