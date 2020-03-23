package bash4s.scripts

import bash4s.domain._
import bash4s.bash4s._

class WorkDir(
  path: FolderPath
) extends Script {

  assert(path.folders.nonEmpty, "Invalid pathPath, must be a subpath")

  val parentFolder = path.folders.dropRight(1)
  val workFolder = path.folders.last

  val NR_OF_SUBFOLDERS = Var

  def src: CommandOp = 
    If `[[` ! (-d(path)) `]]` Then {
      mkdir"-p $path"
    } Else {
      cd"${parentFolder}"                   o
        NR_OF_SUBFOLDERS `=$`(ls | wc"-l")  o
        mv"${workFolder} (($NR_OF_SUBFOLDERS + 1))_${workFolder}" &&
        mkdir"-p ${path}"
    } Fi 
    echo"${path} was successfully created!"
}