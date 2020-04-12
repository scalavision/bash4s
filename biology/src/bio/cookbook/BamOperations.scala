package bio.cookbook

import bash4s.domain._
import bash4s.dsl._
import bio.dsl._
import bio.tools._
import bio.{Cores, Sample}
import bash4s.scripts.Script
import bash4s.ScriptGenerator

sealed trait BamOperations extends Script {
  def param = ScriptGenerator.gen[BamOperations](this.asInstanceOf[BamOperations])
} 

object BamOperations {

  case class ReadDepthStatsAndVisualisation (
    bam: Bam,
    ref: Fasta,
    cores: Cores,
    sample: Sample,
    humanGenome: Genome,
    windowSize: Int,
    workFolder: FolderPath = dirPath"/tmp/mosdepth",
    mappingQuality: Int = 20,
    flags: Int = 0
  ) extends BamOperations {

    val readDepth = Mosdepth.PrBaseByWindow(
      bam, cores, windowSize, ref, mappingQuality, sample, flags
    )

    val bedGraphFile = (workFolder / (fileName"${sample.name}")).regions.bed.gz

    val convertToBigWig = BedGraphToBigWig(
      bedGraphFile, humanGenome
    )

    val MOSDEPTH_WORKDIR = Arg(param.$1(workFolder))
    override def setup = init(
      MOSDEPTH_WORKDIR o 
      (convertToBigWig.env) o
      readDepth.env  
    )
   
    val op = 
      rm"-rf ${MOSDEPTH_WORKDIR}"    o
      mkdir"-p ${MOSDEPTH_WORKDIR}"  o
      pushd"${MOSDEPTH_WORKDIR}" || exit(1) o 
      readDepth.op          o
      convertToBigWig.op    o
      popd || exit(1)
  }

}