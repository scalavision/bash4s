package bio.cookbook

import bash4s.domain._
import bash4s.dsl._
import bio.dsl._
import bio.tools._
import bio.Cores
import bio.Memory
import bash4s.scripts.Script
import bash4s.ScriptGenerator
import bash4s.domain.TextVariable
import bash4s.scripts.Annotations.doc

sealed trait FastqOperations extends Script {
  def param = ScriptGenerator.gen[FastqOperations](this.asInstanceOf[FastqOperations])
}

object FastqOperations {

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
  ) extends FastqOperations {

    val sorted = relFile"./temporary_sorted".sorted.bam
    val sortedAndIndexed = relFile"./temporary_indexed".indexed.sorted.bam

    val bwaMem = Bwa.MapAndAlign(read1, read2, ref, cores, readGroupInfo)
    val bamSort = 
      Samtools.Sort(mem, cores.copy(value = if(cores.value > 4) 4 else cores.value), sorted)
    val bamIndex = Samtools.Index(sorted) 
    val markDup = Gatk.MarkDuplicates(
        sortedAndIndexed,
        bamOut,
        metricsFile,
        tmpDir
      )

    override def setup = init(
      bwaMem.env o 
      bamSort.env o 
      bamIndex.env o
      markDup.env
    )
    
    def op = 
      (bwaMem.op | 
        Samtools.ConvertFromSamToBam.- |
        bamSort.op.-
      ) && 
      bamIndex.op &&
      mv"${sorted.fileType} ${sortedAndIndexed.fileType}" &&
      markDup.op &&
      rm"-f ${sorted.fileType}" &&
      rm"-f ${sortedAndIndexed.fileType}"

  }

  @doc("Assumes the reads are sortable and annotated with R1 and R2 respectively")
  case class MergeReads(
    folderPath: FolderPath,
    read1: Read1,
    read2: Read2
  ) extends FastqOperations {

    val FASTQ_FOLDER = Arg(param.$1(folderPath))
    val READ1 = Arg(param.$2(read1))
    val READ2 = Arg(param.$3(read2))

    override def setup = 
      init(FASTQ_FOLDER o READ1 o READ2)

    private def mergeCmd(read: String) = 
      cat %(find"""-maxdepth 1 -name "*${read}*.fastq.gz" | sort""")

    def op = mergeCmd("R1" ) >> READ1.$ && 
      mergeCmd("R2") >> READ2.$
 
  }

}