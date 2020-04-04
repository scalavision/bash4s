package bash4s.scripts

import scala.language.postfixOps
import bash4s.domain._
import bash4s.bash4s._
import bash4s.scripts.Annotations.doc
import bash4s.ScriptGenerator

case class WorkDir (
  @doc("path to the folder", "p")
  path: FolderPath 
) extends Script {

  assert(
    path.folders.size >= 2, 
    s"Invalid path for workdir: $path, must have at least two levels, like /parent/child"
  )

  def param = ScriptGenerator.gen[WorkDir](this.asInstanceOf[WorkDir])

  val WORKFOLDER = Var
  val FOLDER_PATH = Var
  val PARENT_FOLDER_PATH = Var
  val NR_OF_SUBFOLDERS = Var
  val BACKUP_FOLDER_NAME = Var
  val CREATION_DATE = Var

  def cmdOp: CommandOp = 
    FOLDER_PATH `=` param.$1(path)                                      o
    {
      If `[[` ! (-d(FOLDER_PATH.$)) `]]` Then {
        mkdir"-p $FOLDER_PATH"
      } Else {
        PARENT_FOLDER_PATH `=` $"{$FOLDER_PATH%/*}"                   o
        WORKFOLDER `=` $"{$FOLDER_PATH##*/}"                          o
        cd"${PARENT_FOLDER_PATH}" || exit(1)                          o
        NR_OF_SUBFOLDERS `=$`(find". -maxdepth 1 -type d" | wc"-l")   o
        BACKUP_FOLDER_NAME `=$`(m"${NR_OF_SUBFOLDERS} - 1")           o
        CREATION_DATE `=$`(date""""+%Y__%m_%d__%H_%M"""")             o
        mv"${WORKFOLDER} ${BACKUP_FOLDER_NAME}__${CREATION_DATE}_${WORKFOLDER}" &&
          mkdir"-p ${FOLDER_PATH}"
      } Fi 
    } o
    echo"${FOLDER_PATH} was successfully created!"


}

object WorkDir {
  def apply(path: FolderPath): Script = new WorkDir(path)

}