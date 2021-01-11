package examples.bash

import bash4s._
import bash4s.io._
import domain._
import scripts.Annotations._

@doc("""
uses rsync to fully copy a file, and deletes
the source if the copy was successful
""")
case class FileSync(
  @arg("path to the source file", "s")
  source: FilePath,
  @arg("path to the target file", "t")
  target: FilePath
) extends Script { self =>
  
  assert(
    os.exists(source), s"the source file $source needs to exist"
  )

  def param = ScriptGenerator.gen[FileSync](self.asInstanceOf[FileSync])

  val SOURCE_FILE = Arg(param.$1(source))
  val TARGET_FILE = Arg(param.$2(target))

  override def args = 
    SOURCE_FILE o
    TARGET_FILE
  
  val TARGET_FOLDER = Var

  def op: CommandOp =
    TARGET_FOLDER `=$`(dirname"-- ${target}" ) o
    mkdir"-p ${TARGET_FOLDER}" o  
    rsync"--remove-source-files -auvh $source $target"
}
