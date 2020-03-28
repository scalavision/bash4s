package bash4s.scripts

import bash4s.domain._
import bash4s.bash4s._

class WorkDir(
  path: FolderPath
) extends Script {

  assert(
    path.folders.size >= 2, 
    s"Invalid path for workdir: $path, must have at least two levels, like /parent/child"
  )

  val parentFolder = path.parentFolderPath
  val workFolder = path.lastFolderName

  val NR_OF_SUBFOLDERS = Var
  val NEXT_FOLDER_NAME = Var

  def script: CommandOp = 
    If `[[` ! (-d(path)) `]]` Then {
      mkdir"-p $path"
    } Else {
      cd"${parentFolder}" || exit(1)              o
        NR_OF_SUBFOLDERS `=$`(find"." | wc"-l")   o
        //NEXT_FOLDER_NAME  `=$`()
        mv"${workFolder} (($NR_OF_SUBFOLDERS + 1))_${workFolder}" &&
          mkdir"-p ${path}"
    } Fi 
    echo"${path} was successfully created!"
}

object WorkDir {
  def apply(path: FolderPath): Script = new WorkDir(path)
}