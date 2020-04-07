package bio.data

import bio.dsl._
import bash4s.dsl._
import bash4s.ScriptGenerator
import bash4s.scripts._

sealed trait FastaFormat {
    def param = ScriptGenerator.gen[FastaFormat](this.asInstanceOf[FastaFormat])
    val FASTA_FILE = Var
    def defineFasta(fastaFile: Fasta, meta: ScriptMeta) =
      FASTA_FILE `=` meta.$1(fastaFile)
}
  
object FastaFormat {

  case class CreateDictionary(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {
    
    val DICT_FILE = Var
    override def setup = init(
      defineFasta(fastaFile, param)    o
      DICT_FILE `=` param.$2(dictFile)
    )
    def op = 
      gatk"CreateSequenceDictionary -R ${FASTA_FILE} -O ${DICT_FILE}"
  }

  case class CreateIndex(
    fastaFile: Fasta
  ) extends Script with FastaFormat {
    override def setup = init(defineFasta(fastaFile, param))
    def op = 
      samtools"faidx ${FASTA_FILE}"
  }

  case class CreateBwaIndex(
    fastaFile: Fasta
  ) extends Script with FastaFormat {
    override def setup = init(defineFasta(fastaFile, param))
    def op = 
      bwa"index ${FASTA_FILE}"
  }

  case class CreateAllIndexes(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {

    val createIndex = CreateIndex(fastaFile)
    val createBwaIndex = CreateBwaIndex(fastaFile)
    val createDictionary = CreateDictionary(fastaFile, dictFile)

    override def setup = 
      createDictionary.setup

    def op = 
      createIndex.op && 
        createBwaIndex.op && 
          createDictionary.op
  }

  case class CreateAllIndexesInParallel(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {

    val createIndex = CreateIndex(fastaFile)
    val createBwaIndex = CreateBwaIndex(fastaFile)
    val createDictionary = CreateDictionary(fastaFile, dictFile)
    
    override def setup = createDictionary.setup
    
    def op = 
      createIndex.op.&          o
        createBwaIndex.op.&     o
          createDictionary.op.& o
          await()

  }

}