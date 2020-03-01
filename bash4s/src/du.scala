package bash.coreutil

import bash.domain._
import bash.BashCommandAdapter

case class duWrapper (
  args: CmdArgs = CmdArgs(Vector.empty[String])
) extends BashCommandAdapter { self =>
  def toCmd = SimpleCommand("du", args)
  def help = copy(args = self.args :+ "--help")
  def h = copy(args = self.args :+ "-h")
  def version = copy(args = self.args :+ "--version")
  def v = copy(args = self.args :+ "-v")
  def inFolder(p: Option[FileTypeOp] = None) = 
    copy(args = self.args :+ "-h" :+ "-du " :+ p.fold("."){_.path})
}
