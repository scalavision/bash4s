package bio.tools

import bio.dsl._
import bio.{Cores, Sample}
import bash4s.dsl._
import bash4s.domain._
import bash4s.ScriptGenerator
import bash4s.scripts._
import bash4s.domain.IntVariable

sealed trait Mosdepth extends Script {

  def param = ScriptGenerator.gen[Mosdepth](this.asInstanceOf[Mosdepth])

  val MOSDEPTH_BAM = Var
  val MOSDEPTH_REF = Var 

  def args(bam: Bam, ref: Fasta) =
    MOSDEPTH_BAM `=` param.$1(bam) o
    MOSDEPTH_REF `=` param.$2(ref)
}

object Mosdepth {

  case class PrBaseByWindow(
    bam: Bam,
    cores: Cores,
    windowSize: Int,
    ref: Fasta,
    mappingQuality: Int,
    sample: Sample,
    flags: Int = 0
  ) extends Mosdepth {

    val MOSDEPTH_WINDOW_SIZE = Arg(param.$3(IntVariable(windowSize)))
    val MOSDEPTH_CORES = Arg(param.$4(cores))
    val MOSDEPTH_MAPPING_QUALITY = Arg(param.$5(IntVariable(mappingQuality)))
    val MOSDEPTH_SAMPLE = Arg(param.$6(txt"${sample.name}"))
    val MOSDEPTH_FLAGS = Arg(param.$7(IntVariable(flags)))

    override def args =  
      MOSDEPTH_WINDOW_SIZE o
      MOSDEPTH_CORES o
      MOSDEPTH_MAPPING_QUALITY o
      MOSDEPTH_SAMPLE o
      MOSDEPTH_FLAGS o
      args(bam, ref)
    def op = 
      mosdepth"-t $MOSDEPTH_CORES -b $MOSDEPTH_WINDOW_SIZE -f $MOSDEPTH_REF -F $MOSDEPTH_FLAGS -Q $MOSDEPTH_MAPPING_QUALITY $MOSDEPTH_SAMPLE $MOSDEPTH_BAM"

  }

}