package bio

import bash4s.domain._
import domain._

package object bio {

//  type Fasta = domain.Fasta
  
  type Fasta = BiologyFileType[FastaFile]
  type Read1 = BiologyFileType[FastqGz]
  type Read2 = BiologyFileType[FastqGz]
  type BwaIndex = BiologyFileType[BwaIndexed]

  implicit def liftBioToBash4s[Fasta]: BiologyFileType[Fasta] => CommandOp = {
    case bp: BiologyFilePath[Fasta] => 
      FilePath(bp.folderPath.root, bp.folderPath.folders, bp.fileName)
    case _ => throw new Exception("UNSUPPORTED!!!!")
  }

  implicit class BioFileSyntax(p: FilePath) {
    def bam = BiologyFilePath[Bam](FolderPath(p.root, p.folderPath), p.fileName)
    def fasta = BiologyFilePath[Fasta](FolderPath(p.root, p.folderPath), p.fileName)
    
  }

  implicit class BioSyntax(s: StringContext) {

    def bwa(args: Any*) = 
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))
  
    def file(args: Any*) = {
      val fileArgs = s.s(args:_*).split("/").toVector
      BiologyFilePath(FolderPath('/', fileArgs.dropRight(1) ), FileName(BaseName(fileArgs.last), Vector.empty[String]))
    }
    
  }
}