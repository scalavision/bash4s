package bio

import bash4s.dsl._
import bash4s.domain._
import bash4s._
import bash4s.scripts._
import bio.dsl._
import bash4s.scripts.Annotations._

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

    val FASTA_FILE = Var

    def op = 
      FASTA_FILE `=` param.$1(fasta) o
      bwa"index $FASTA_FILE"

  }
  
  doc("""
  |Map and aligns fastq files with bwa
  |Some resources:
  |- https://github.com/CCDG/Pipeline-Standardization/blob/master/PipelineStandard.md
  """.stripMargin)
  case class MapAndAlign(
    r1: Read1,
    r2: Read2,
    @arg("The fasta file used as a reference, there must be a bwa index file available in the same folder for bwa to find")
    bwaIndexedFasta: Fasta,
    @arg("The number of cores that will be used")
    nrOfCores: Cores,
    @arg("Read Group info that should be added to the bam file")
    readGroupInfo: TextVariable
  ) extends Script {

    def param = ScriptGenerator.gen[MapAndAlign](this.asInstanceOf[MapAndAlign])

    val READ1 = Var
    val READ2 = Var
    val BWA_INDEX = Var
    val NR_OF_CORES = Var
    val READ_GROUP_INFO = Var

    /**
      * bwa mem -K 100000000 -t 6 -M /work/bio/ref/GCA_000001405.15_GRCh38_no_alt_analysis_set.fna /stash/data/hg00x/HG002C2c-000200PM-Mendel-KIT-wgs_S8_L008_R1_001.fastq.gz /stash/data/hg00x/HG002C2c-000200PM-Mendel-KIT-wgs_S8_L008_R2_001.fastq.gz
      *
      * @return
      */
    def op =
      READ1 `=` param.$1(r1)              o
      READ2 `=` param.$2(r2)              o
      NR_OF_CORES `=` param.$3(nrOfCores) o
      READ_GROUP_INFO `=` param.$4(readGroupInfo) o
      cd"/hello/world" &&
        bwa"mem -K 100000000 -R ${READ_GROUP_INFO} -t ${NR_OF_CORES} -M ${BWA_INDEX} $READ1 $READ2"

  }

}