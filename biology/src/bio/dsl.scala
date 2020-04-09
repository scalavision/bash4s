package bio

import bash4s.domain._
import biomodel._
//import scala.reflect.ClassTag
import scala.reflect.runtime.universe._
import com.github.ghik.silencer.silent

object dsl {

  type Fasta = BiologyFileType[FastaFile]
  
//  type Fasta = BiologyFileType[FastaFile]
  type Read1 = BiologyFileType[Fastq with Gz]
  type Read2 = BiologyFileType[Fastq with Gz]
  type MarkdupSortedIndexedBam = BiologyFileType[Markdup with Sorted with Indexed with BamFile]
  type BwaIndex = BiologyFileType[BwaIndexed]
  type Dict = BiologyFileType[DictFile]
  type Vcf = BiologyFileType[VcfFile]
  type CnnScored2DVcf = BiologyFileType[CnnScored_2D with VcfFile]
  type Bam = BiologyFileType[BamFile]
  type GVcf = BiologyFileType[G with VcfFile]

  implicit def liftBioToBash4s[A]: BiologyFileType[A] => CommandOp = {
    case bp: BiologyFilePath[A] => bp.fileType
    case _ => throw new Exception("UNSUPPORTED!!!!")
  }

  @silent
  implicit def liftBioOptToBash4s[A](implicit tag: TypeTag[A]): Option[BiologyFileType[A]] => Option[CommandOp] = {
      case Some(a) => a match {
        case BiologyFilePath(fileType) => Some(fileType)
      }
      case _ => None
  }
  
  implicit class liftBioToBash4s[A](b: BiologyFileType[A]) {
    def op = b match {
      case bp: BiologyFilePath[A] => bp.fileType
    }
  }
  
  implicit class liftBamBioOptToBash4s[A](f: Option[BiologyFileType[A]]) {
    def op = f match {
      case Some(a) => a match {
        case BiologyFilePath(fileType) => Some(fileType)
      }
      case _ => None
    }

  }
  
  implicit class liftBamBioSeqToBash4s[A](f: Seq[BiologyFileType[A]]) {
    def op: Seq[FileHandle] = f.map { 
        case BiologyFilePath(fileType) => fileType
    }
  }
  
  implicit def liftCoresToBash4s: Cores => CommandOp = i => IntVariable(i.nrOfCores)

  implicit class BioFileSyntax(private val p: FilePath) extends AnyVal {
    def bam = BiologyFilePath[BamFile](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "bam")))
    def markdup = BiologyFilePath[Markdup](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "markdup")))
    def vcf = BiologyFilePath[VcfFile](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "vcf")))
    def cnn2d = BiologyFilePath[CnnScored_2D](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "cnn2d")))
    def fasta = BiologyFilePath[FastaFile](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "fasta")))
    def fastq = BiologyFilePath[Fastq](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "fastq")))
    def gz = BiologyFilePath[Gz](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "gz")))
    def dict = BiologyFilePath[DictFile](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "dict")))
  }

  implicit class BioSyntaxInt(i: Int) {
    def cores = Cores(i)
  }
  
  implicit class BioSyntax(private val s: StringContext) extends AnyVal {

    def bwa(args: Any*) = 
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))
    
    def gatk(args: Any*) = 
      SimpleCommand("gatk", CmdArgCtx(args.toVector, s))
    
    def samtools(args: Any*) = 
      SimpleCommand("samtools", CmdArgCtx(args.toVector, s))
    
  }
}