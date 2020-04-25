package bio.data

import bio.dsl._
import bash4s._
import bash4s.domain._
import bash4s.ScriptGenerator
import bash4s.scripts._

sealed trait FastaFormat {
    val param = ScriptGenerator.gen[FastaFormat](this.asInstanceOf[FastaFormat])
    val FASTA_FILE = Var
    def defineFasta(fastaFile: Fasta, meta: ScriptMeta) =
      FASTA_FILE `=` meta.$1(fastaFile)
}

/**
  * Resources:
  * - https://gatkforums.broadinstitute.org/gatk/discussion/1601/how-can-i-prepare-a-fasta-file-to-use-as-reference
  */
object FastaFormat {

  case class CreateDictionary(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {
    
    val DICT_FILE = Var
    override def args = (
      defineFasta(fastaFile, param)    o
      DICT_FILE `=` param.$2(dictFile)
    )
    def op = 
      gatk"CreateSequenceDictionary -R ${FASTA_FILE} -O ${DICT_FILE}"
  }

  case class CreateIndex(
    fastaFile: Fasta
  ) extends Script with FastaFormat {
    override def args = (defineFasta(fastaFile, param))
    def op = 
      samtools"faidx ${FASTA_FILE}"
  }
 
  case class SplitByChromosome(
    fastaFile: Fasta,
    workDir: FolderPath,
    resultDir: FolderPath
  ) extends Script {

    val param = 
      ScriptGenerator.gen[SplitByChromosome](this.asInstanceOf[SplitByChromosome])

    val WORKDIR = Var
    val FASTA_FILE = Var
    val RESULTDIR = Var

    val CHR_FILE = Var 
    val CHR_NAME = Var

    override def args = (
      FASTA_FILE `=` param.$1(fastaFile) o
      WORKDIR `=` param.$2(workDir) o
      RESULTDIR `=` param.$3(resultDir) 
    )

    def op = mkdir"-p ${WORKDIR}" && 
      cd"${WORKDIR}" && 
      csplit"-s -z ${FASTA_FILE} '/>/' '{*}'"                          o
      For(CHR_FILE.$).In(txt"xx{00..23}").Do {
        __#"Extracting chromosome name from the file"                  o
        CHR_NAME `=$`(sed"""'s/>// ; s/ .*// ; 1q' "${CHR_FILE}"""")   o
        mv"${CHR_FILE} ${CHR_NAME}.fa"
      }.Done 
   
  }

  case class CreateBwaIndex(
    fastaFile: Fasta
  ) extends Script with FastaFormat {
    override def args = (defineFasta(fastaFile, param))
    def op = 
      bwa"index ${FASTA_FILE}"
  }

  case class CreateAllIndexes(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {

    lazy val createIndex = CreateIndex(fastaFile)
    lazy val createBwaIndex = CreateBwaIndex(fastaFile)
    lazy val createDictionary = CreateDictionary(fastaFile, dictFile)

    override def args = 
      createDictionary.args

    def op = 
      createIndex.op && 
        createBwaIndex.op && 
          createDictionary.op
  }

  case class CreateAllIndexesInParallel(
    fastaFile: Fasta,
    dictFile: Dict
  ) extends Script with FastaFormat {

    lazy val createIndex = CreateIndex(fastaFile)
    lazy val createBwaIndex = CreateBwaIndex(fastaFile)
    lazy val createDictionary = CreateDictionary(fastaFile, dictFile)
    
    override def args = createDictionary.args
    
    def op = 
      createIndex.op.&          o
        createBwaIndex.op.&     o
          createDictionary.op.& o
          await

  }

}