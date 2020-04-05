package bash4s

import domain._

object FileConversions {

  def convertToFilePath(txt: String): FilePath = {
    val root = txt.head
    val folderAndFile = txt.tail.split(root)
    val folders = folderAndFile.dropRight(1).toVector
    val baseName = BaseName(folderAndFile.last.takeWhile(_ != '.'))
    val extensensions = folderAndFile.last.dropWhile(_ != '.').drop(1).split('.').toVector
    FilePath(root, folders, FileName(baseName, extensensions))
  }
  
  def convertToRelFilePath(txt: String): RelPath = {
    val root = txt.head
    val folderAndFile = txt.tail.split(root)
    val folders = folderAndFile.dropRight(1).toVector
    val baseName = BaseName(folderAndFile.last.takeWhile(_ != '.'))
    val extensensions = folderAndFile.last.dropWhile(_ != '.').drop(1).split('.').toVector
    RelPath(folders, FileName(baseName, extensensions))
  }

   def convertToFileName(txt: String) : FileName = {
    val fileNameComponents = txt.split("\\.")
    FileName(BaseName(fileNameComponents.head), fileNameComponents.tail.toVector)
  }

   def convertToFolderPath(txt: String) : FolderPath = {
    val folders = txt.split(Constants.rootChar.toString())
    FolderPath(Constants.rootChar, folders.toVector)
  }

}