package bio

import bash4s.domain._
import biomodel._

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

  implicit def liftCoresToBash4s: Cores => CommandOp = i => IntVariable(i.nrOfCores)

  implicit class BioFileSyntax(p: FilePath) {
    def bam = BiologyFilePath[Bam](FolderPath(p.root, p.folderPath), p.fileName)
    def fasta = BiologyFilePath[Fasta](FolderPath(p.root, p.folderPath), p.fileName)
    
  }

  implicit class BioSyntaxInt(i: Int) {
    def cores = Cores(i)
  }
  
  implicit class BioSyntax(private val s: StringContext) extends AnyVal {

    def bwa(args: Any*) = 
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))
  
    def file(args: Any*) = {
      val fileArgs = s.s(args:_*).split("/").toVector
      BiologyFilePath(FolderPath('/', fileArgs.dropRight(1) ), FileName(BaseName(fileArgs.last), Vector.empty[String]))
    }
    
  }
}