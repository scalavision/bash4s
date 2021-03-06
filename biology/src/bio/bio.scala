package object bio {

import scala.reflect.runtime.universe._
import bash4s.domain._
import bash4s._
import biomodel._
//import scala.reflect.ClassTag
import com.github.ghik.silencer.silent

  type Fasta = BiologyFileType[FastaFile]
  
//  type Fasta = BiologyFileType[FastaFile]
  type Read1 = BiologyFileType[Fastq with GzFile]
  type Read2 = BiologyFileType[Fastq with GzFile]
  type MarkdupIndexedSortedBam = BiologyFileType[Markdup with Indexed with Sorted with BamFile]
  type BwaIndex = BiologyFileType[BwaIndexed]
  type IndexedSortedBam = BiologyFileType[Indexed with Sorted with BamFile]
  type Dict = BiologyFileType[DictFile]
  type Ped = BiologyFileType[PedigreeFile]
  type Vcf = BiologyFileType[VcfFile]
  type CnnScored2DVcf = BiologyFileType[CnnScored_2D with VcfFile]
  type Bam = BiologyFileType[BamFile]
  type SortedBam = BiologyFileType[Sorted with BamFile]
  type GVcf = BiologyFileType[G with VcfFile]
  type RegionsBedGz = BiologyFileType[RegionsFile with BedFile with GzFile]
  type Genome = BiologyFileType[GenomeFile]

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

  implicit def optSample: Option[Sample] => CommandOp = {
    case None => DebugValue("")
    case Some(s) => txt"${s.name}"
  }
  
  implicit def optFileType[A]: Option[BiologyFileType[A]] => CommandOp = {
    case None => DebugValue("")
    case Some(ft) =>  ft match {
      case BiologyFilePath(fileType) => fileType
    }
  }
  
  implicit def liftCoresToBash4s: Cores => CommandOp = i => IntVariable(i.value)
  implicit def liftMemToBash4s: Memory => CommandOp = i => txt"$i"
  implicit def liftSampleNameToBash4s: Sample => CommandOp = i => txt"$i"

  implicit class BioFileSyntax(private val p: FileHandle) extends AnyVal {
    def bam = BiologyFilePath[BamFile](p.appendExtension("bam"))
    def markdup = BiologyFilePath[Markdup](p.appendExtension("markdup"))
    def vcf = BiologyFilePath[VcfFile](p.appendExtension("vcf"))
    def cnn2d = BiologyFilePath[CnnScored_2D](p.appendExtension("cnn2d"))
    def fasta = BiologyFilePath[FastaFile](p.appendExtension("fasta"))
    def fastq = BiologyFilePath[Fastq](p.appendExtension("fastq"))
    def gz = BiologyFilePath[GzFile](p.appendExtension("gz"))
    def dict = BiologyFilePath[DictFile](p.appendExtension("dict"))
    def ped = BiologyFilePath[DictFile](p.appendExtension("ped"))
    def sorted = BiologyFilePath[Sorted](p.appendExtension("sorted"))
    def indexed = BiologyFilePath[Indexed](p.appendExtension("indexed"))
    def region = BiologyFilePath[RegionFile](p.appendExtension("region"))
    def regions = BiologyFilePath[RegionsFile](p.appendExtension("regions"))
    def genome = BiologyFilePath[GenomeFile](p.appendExtension("genome"))
  }

  implicit class BioSyntaxInt(i: Int) {
    def cores = Cores(i)
    def G = Memory(s"${i}G")
    def Gb = Memory(s"${i}Gb")
    def GB = Memory(s"${i}GB")
    def M = Memory(s"${i}M")
    def Mb = Memory(s"${i}Mb")
    def MB = Memory(s"${i}MB")
  }
  
  implicit class BioSyntax(private val s: StringContext) extends AnyVal {

    def bwa(args: Any*) = 
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))
    
    def gatk(args: Any*) = 
      SimpleCommand("gatk", CmdArgCtx(args.toVector, s))
    
    def samtools(args: Any*) = 
      SimpleCommand("samtools", CmdArgCtx(args.toVector, s))
    
    def bgzip(args: Any*) = 
      SimpleCommand("bgzip", CmdArgCtx(args.toVector, s))
    
    def mosdepth(args: Any*) = 
      SimpleCommand("mosdepth", CmdArgCtx(args.toVector, s))
    
    def tabix(args: Any*) = 
      SimpleCommand("tabix", CmdArgCtx(args.toVector, s))
      
    def bedGraphToBigWig(args: Any*) = 
      SimpleCommand("bedGraphToBigWig", CmdArgCtx(args.toVector, s))
    
  }
}