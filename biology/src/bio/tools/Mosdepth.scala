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

  val BAM = Var
  val REF = Var 

  def args(bam: Bam, ref: Fasta) =
    BAM `=` param.$1(bam) o
    REF `=` param.$2(ref)
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

    val WINDOW_SIZE = Arg(param.$3(IntVariable(windowSize)))
    val CORES = Arg(param.$4(cores))
    val MAPPING_QUALITY = Arg(param.$5(IntVariable(mappingQuality)))
    val SAMPLE = Arg(param.$6(sample))
    val FLAGS = Arg(param.$7(IntVariable(flags)))

    override def setup = init(
      args(bam, ref) o
      WINDOW_SIZE o
      CORES o
      MAPPING_QUALITY o
      SAMPLE o
      FLAGS
    )

    def op = 
      mosdepth"-t $CORES -b $WINDOW_SIZE -F $FLAGS -Q $MAPPING_QUALITY $SAMPLE $BAM"

  }

}