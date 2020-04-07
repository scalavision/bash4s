package bio

import bash4s.domain._
import biomodel._

object dsl {

  type Fasta = BiologyFileType[FastaFile]
  
//  type Fasta = BiologyFileType[FastaFile]
  type Read1 = BiologyFileType[Fastq with Gz]
  type Read2 = BiologyFileType[Fastq with Gz]
  type BwaIndex = BiologyFileType[BwaIndexed]
  type Dict = BiologyFileType[DictFile]

  implicit def liftBioToBash4s[Fasta]: BiologyFileType[Fasta] => CommandOp = {
    case bp: BiologyFilePath[Fasta] => 
      bp.fileType
    case _ => throw new Exception("UNSUPPORTED!!!!")
  }

  implicit def liftCoresToBash4s: Cores => CommandOp = i => IntVariable(i.nrOfCores)

  implicit class BioFileSyntax(private val p: FilePath) extends AnyVal {
    def bam = BiologyFilePath[Bam](p.copy(fileName = p.fileName.copy(extension = p.fileName.extension :+ "bam")))
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