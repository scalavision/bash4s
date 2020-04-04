package bio

import bash4s._
import scripts.Script
import bio._
import scripts.Annotations.doc

object Bwa {

  // https://gatkforums.broadinstitute.org/gatk/discussion/2798/howto-prepare-a-reference-for-use-with-bwa-and-gatk 
  case class BuildIndexWgs(
    @doc("path to fasta file", "i")
    fasta: Fasta
  ) extends Script {

    def param = ScriptGenerator.gen[BuildIndexWgs](this.asInstanceOf[BuildIndexWgs])

    val FASTA_FILE = bash4s.Var

    def cmdOp = 
      FASTA_FILE `=` param.$1(fasta) o
      bwa"index -a bwtsw  $FASTA_FILE"

  }

}