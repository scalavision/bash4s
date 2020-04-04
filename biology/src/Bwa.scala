package bio

import bash4s._
import scripts.Script
import bio._
import scripts.Annotations.doc
import scripts.Annotations.arg

object Bwa {

  @doc("""
  |Builds an index that would be used for map and alignment using bwa tool.
  |Some resources:
  |- https://www.biostars.org/p/53546/
  |- https://lh3.github.io/2017/11/13/which-human-reference-genome-to-use
  |- https://gatkforums.broadinstitute.org/gatk/discussion/2798/howto-prepare-a-reference-for-use-with-bwa-and-gatk
  """.stripMargin)
  case class BuildIndex(
    @arg("""path to fasta file""")
    fasta: Fasta
  ) extends Script {

    def param = ScriptGenerator.gen[BuildIndex](this.asInstanceOf[BuildIndex])

    val FASTA_FILE = bash4s.Var

    def op = 
      FASTA_FILE `=` param.$1(fasta) o
      bwa"index $FASTA_FILE"

  }
  
  case class MapAndAlign(
    r1: Read1,
    r2: Read2,
    bwaIndex: BwaIndex,
    nrOfCores: Int,
    readGroupInfo: String
  ) extends Script {

    def param = ScriptGenerator.gen[MapAndAlign](this.asInstanceOf[MapAndAlign])

    val READ1 = bash4s.Var
    val READ2 = bash4s.Var
    val BWA_INDEX = bash4s.Var
    val NR_OF_CORES = bash4s.Var
    val READ_GROUP_INFO = bash4s.Var

    def op 
      READ1 `=` param.$1(r1)              o
      READ2 `=` param.$2(r2)              o
      NR_OF_CORES `=` param.$3(nrOfCores) o
      READ_GROUP_INFO `=` param.$4(readGroupInfo) o
      bwa"mem -K 100000000 -R ${READ_GROUP_INFO} -t ${NR_OF_CORES} -M ${BWA_INDEX} $READ1 $READ2"

  }

}