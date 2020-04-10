package bash4s

import domain._

object FileConversions {

  def convertToFilePath(txt: String): FilePath = {
    val root = txt.head
    val folderAndFile = txt.tail.split(root)
    val folders = folderAndFile.dropRight(1).toVector
    val baseName = BaseName(folderAndFile.last.takeWhile(_ != '.'))
    val extensensions = folderAndFile.last.dropWhile(_ != '.').drop(1).split('.').toVector.filter(_.nonEmpty)
    FilePath(root, folders, FileName(baseName, extensensions))
  }
  
  def convertToRelFilePath(txt: String): RelPath = {
    assert(txt.take(2) == "./" || txt.take(3) == "../", s"A relative file must start with ./ or ../, the errored file has this path: $txt")
    val folders = txt.split("/").dropRight(1).toVector
    val baseName = BaseName(txt.split("/").last.takeWhile(_ != '.'))
    val extensensions = txt.split("/").last.split(""".""").drop(1).toVector
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

   def convertToRelFolderPath(txt: String) : RelFolderPath = {
    val folders = txt.split(Constants.rootChar.toString())
    RelFolderPath(folders.toVector)
   }

}