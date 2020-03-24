package bash4s

import domain._

object FileConversions {

  def convertToFilePath(txt: String): FilePath = {
    val root = txt.head
    val folderAndFile = txt.tail.split(root)
    val folders = FolderPath(root, folderAndFile.dropRight(1).toVector.map(FolderName))
    val baseName = BaseName(folderAndFile.last.takeWhile(_ != '.'))
    val extensensions = FileExtension(folderAndFile.last.dropWhile(_ != '.').drop(1).split('.').toVector)
    FilePath(folders, FileName(baseName, extensensions))
  }
  
  def convertToRelFilePath(txt: String): RelPath = {
    val root = txt.head
    val folderAndFile = txt.tail.split(root)
    val folders = SubFolderPath(folderAndFile.dropRight(1).toVector.map(FolderName))
    val baseName = BaseName(folderAndFile.last.takeWhile(_ != '.'))
    val extensensions = FileExtension(folderAndFile.last.dropWhile(_ != '.').drop(1).split('.').toVector)
    RelPath(folders, FileName(baseName, extensensions))
  }

   def convertToFileName(txt: String) : FileName = {
    val fileNameComponents = txt.split("\\.")
    FileName(BaseName(fileNameComponents.head), FileExtension(fileNameComponents.tail.toVector))
  }

   def convertToFolderPath(txt: String) : FolderPath = {
    val folders = txt.split(Constants.rootChar.toString())
    FolderPath(Constants.rootChar, folders.toVector.map(FolderName))
  }

}