package bio

import bash4s._
import scripts.Script
import bio._
import scripts.Annotations.doc

object Bwa {

  case class BuildIndex(
    @doc("path to fasta file", "i")
    fasta: Fasta
  ) extends Script {

    def param = ScriptGenerator.gen[BuildIndex](this.asInstanceOf[BuildIndex])

    val FASTA_FILE = bash4s.Var

    def cmdOp = 
      FASTA_FILE `=` param.$1(fasta) o
      bwa"index $FASTA_FILE"

  }

}