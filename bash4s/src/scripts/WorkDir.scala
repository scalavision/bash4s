package bash4s.scripts

import bash4s.domain._
import bash4s.bash4s._
import bash4s.scripts.Annotations.doc
case class WorkDir (
  @doc("path to the folder", "p")
  path: FolderPath 
) extends Script {

  assert(
    path.folders.size >= 2, 
    s"Invalid path for workdir: $path, must have at least two levels, like /parent/child"
  )

  val parentFolder = path.parentFolderPath
  val workFolder = path.lastFolderName

  val NR_OF_SUBFOLDERS = Var
  val BACKUP_FOLDER_NAME = Var
  val CREATION_DATE = Var

  def cmdOp: CommandOp = 
    If `[[` ! (-d(path)) `]]` Then {
      mkdir"-p $path"
    } Else {
      cd"${parentFolder}" || exit(1)              o
        NR_OF_SUBFOLDERS `=$`(find". -maxdepth 1 -type d" | wc"-l")   o
        BACKUP_FOLDER_NAME `=$`(m"${NR_OF_SUBFOLDERS} - 1")           o
        CREATION_DATE `=$`(date""""+%Y__%m_%d__%H_%M"""") o
        mv"${workFolder} ${BACKUP_FOLDER_NAME}__${CREATION_DATE}_${workFolder}" &&
          mkdir"-p ${path}"
    } Fi 
    echo"${path} was successfully created!"
}

object WorkDir {
  def apply(path: FolderPath): Script = new WorkDir(path)

}