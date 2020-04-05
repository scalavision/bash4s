package bash4s.scripts

import scala.language.postfixOps
import bash4s.domain._
import bash4s.dsl._
import bash4s.scripts.Annotations.arg
import bash4s.scripts.Annotations.doc
import bash4s.ScriptGenerator

@doc("""
  Creates a empty workdir at the given path. 
  If the workdir already exists, the original workfolder will be renamed to:
  <nr of existing folders>_<date as YYYY_mm_dd_hh_M>
  Thus, you have an idempotent work folder for your jobs
  """
)
case class WorkDir (
  @arg("path to the folder", "p")
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

  def op: CommandOp = 
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