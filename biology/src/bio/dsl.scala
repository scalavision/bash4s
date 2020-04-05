package bio

import bash4s.domain._
import biomodel._

object dsl {

  type Fasta = BiologyFileType[FastaFile]
  
//  type Fasta = BiologyFileType[FastaFile]
  type Read1 = BiologyFileType[Fastq with Gz]
  type Read2 = BiologyFileType[Fastq with Gz]
  type BwaIndex = BiologyFileType[BwaIndexed]

  implicit def liftBioToBash4s[Fasta]: BiologyFileType[Fasta] => CommandOp = {
    case bp: BiologyFilePath[Fasta] => 
      FilePath(bp.folderPath.root, bp.folderPath.folders, bp.fileName)
    case _ => throw new Exception("UNSUPPORTED!!!!")
  }

  implicit def liftCoresToBash4s: Cores => CommandOp = i => IntVariable(i.nrOfCores)

  implicit class BioFileSyntax(private val p: FilePath) extends AnyVal {
    def bam = BiologyFilePath[Bam](FolderPath(p.root, p.folderPath), p.fileName)
    def fasta = BiologyFilePath[FastaFile](FolderPath(p.root, p.folderPath), p.fileName)
    def fastq = BiologyFilePath[Fastq](FolderPath(p.root, p.folderPath), p.fileName)
    def gz = BiologyFilePath[Gz](FolderPath(p.root, p.folderPath), p.fileName)
    
  }

  implicit class BioSyntaxInt(i: Int) {
    def cores = Cores(i)
  }
  
  implicit class BioSyntax(private val s: StringContext) extends AnyVal {

    def bwa(args: Any*) = 
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))

    /*
    def file(args: Any*) = {
      val fileArgs = s.s(args:_*).split("/").toVector
      BiologyFilePath(FolderPath('/', fileArgs.dropRight(1) ), FileName(BaseName(fileArgs.last), Vector.empty[String]))
    }*/
    
  }
}