package bash4s
object dsl {

  sealed abstract class CommandOp()
  final case class ScriptLine() extends CommandOp
  sealed trait CommandArg extends CommandOp

  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg

  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
    def :+(arg: String) = copy(args = self.args :+ arg)
  }
  final case class EmptyArg() extends CommandArg
  final case class OpenCommandList() extends CommandOp
  final case class CThen() extends CommandOp
  final case class CElse() extends CommandOp
  final case class CElseIf() extends CommandOp
  final case class CUntil() extends CommandOp
  final case class CFor() extends CommandOp
  final case class CDone() extends CommandOp
  final case class CIf() extends CommandOp
  final case class CFi() extends CommandOp
  final case class CloseCommandList() extends CommandOp
  final case class Dollar() extends CommandOp

  sealed trait CommandRedirection extends CommandOp
  final case class StdOut() extends CommandRedirection
  final case class StdErr() extends CommandRedirection
  final case class AppendStdOut() extends CommandRedirection
  final case class StdOutWithStdErr() extends CommandRedirection
  final case class AppendStdOutWithSdErr() extends CommandRedirection
  final case class StdIn() extends CommandRedirection
  final case class RedirectStdOutWithStdErr() extends CommandRedirection
  final case class CloseStdIn() extends CommandRedirection
  final case class CloseStdOut() extends CommandRedirection

  sealed trait PipelineOp extends CommandListOp
  final case class PipeWithStdOut() extends PipelineOp
  final case class PipeWithError() extends PipelineOp

  final case class Negate() extends PipelineOp

  final case class SimpleCommand(
      name: String,
      arg: CommandArg,
      cmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends PipelineOp { self =>

    def >(op: CommandOp) = copy(cmds = (self.cmds :+ StdOut()) :+ op)
    def `2>`(op: CommandOp) = copy(cmds = (self.cmds :+ StdErr()) :+ op)
    def >>(op: CommandOp) = copy(cmds = (self.cmds :+ AppendStdOut()) :+ op)
    def &>(op: CommandOp) = copy(cmds = (self.cmds :+ StdOutWithStdErr()) :+ op)
    def &>>(op: CommandOp) =
      copy(cmds = (self.cmds :+ AppendStdOutWithSdErr()) :+ op)
    def <(op: CommandOp) = copy(cmds = (self.cmds :+ StdIn()) :+ op)
    def `2>&1`(op: CommandOp) =
      copy(cmds = (self.cmds :+ RedirectStdOutWithStdErr()) :+ op)
    def <&-(op: CommandOp) = copy(cmds = (self.cmds :+ CloseStdIn()) :+ op)
    def >&-(op: CommandOp) = copy(cmds = (self.cmds :+ CloseStdOut()) :+ op)

    def & = copy(cmds = self.cmds :+ Amper())
    def `;` = copy(cmds = self.cmds :+ Semi())
    def `\n` = copy(cmds = self.cmds :+ NewLine())

    def $(cmdList: CommandListOp) =
      copy(cmds = cmds :+ OpenSubShellExp() :+ cmdList :+ CloseSubShellEnv())

    def &(cmdList: CommandListOp) =
      CommandListBuilder(Vector(self, Amper(), cmdList))

    def `;`(cmdList: CommandListOp) =
      CommandListBuilder(Vector(self, Amper(), cmdList))

    def o(op: CommandOp) =
      ScriptBuilder(Vector(self, ScriptLine(), op))

    def |(simpleCommand: SimpleCommand) =
      PipelineBuilder(Vector(self, PipeWithStdOut(), simpleCommand))

    def |&(simpleCommand: SimpleCommand) =
      PipelineBuilder(Vector(self, PipeWithStdOut(), simpleCommand))

    def ||(pipelineOp: PipelineOp) =
      CommandListBuilder(Vector(self, Or(), pipelineOp))

    def &&(pipelineOp: PipelineOp) =
      CommandListBuilder(Vector(self, And(), pipelineOp))
  }

  final case class PipelineBuilder(cmds: Vector[CommandOp]) extends PipelineOp {
    self =>

    def unary_! = self.copy(Negate() +: cmds)

    def & =
      CommandListBuilder(
        Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), Amper())
      )

    def `;` = copy(cmds = self.cmds :+ Semi())
    CommandListBuilder(
      Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), Semi())
    )

    def `\n` =
      CommandListBuilder(
        Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), NewLine())
      )

    def |(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)

    def |&(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)

    def ||(pipelineOp: PipelineOp) =
      CommandListBuilder(Vector(self, Or(), pipelineOp))

    def &&(pipelineOp: PipelineOp) =
      CommandListBuilder(Vector(self, And(), pipelineOp))
  }

  sealed trait CommandListOp extends CommandOp
  final case class Or() extends CommandListOp
  final case class And() extends CommandListOp
  final case class Amper() extends CommandListOp
  final case class Semi() extends CommandListOp
  final case class NewLine() extends CommandListOp
  final case class OpenSubShellEnv() extends CommandListOp
  final case class CloseSubShellEnv() extends CommandListOp
  final case class OpenSubShellExp() extends CommandListOp

  final case class CommandListBuilder(cmds: Vector[CommandOp])
      extends CommandListOp { self =>

    def unary_! = self.copy(Negate() +: cmds)

    def ||(cmdListOp: CommandListOp) =
      copy(cmds = (self.cmds :+ Or()) :+ cmdListOp)

    def &&(cmdListOp: CommandListOp) =
      copy(cmds = (self.cmds :+ And()) :+ cmdListOp)

    def & =
      self.copy(cmds =
        (OpenSubShellEnv() +: self.cmds) ++ Vector(CloseSubShellEnv(), Amper())
      )

    def &(cmdList: CommandListOp) =
      copy(cmds = (self.cmds :+ Amper()) :+ cmdList)

    def `;`(cmdList: CommandListOp) =
      copy(cmds = (self.cmds :+ Semi()) :+ cmdList)

    def |(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)

    def |&(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)

    def `]]` = copy(cmds = self.cmds :+ CloseDoubleSquareBracket())

  }

  final case class CloseDoubleSquareBracket() extends CommandOp
  final case class OpenDoubleSquareBracket() extends CommandOp

  def `[[`(op: CommandOp): CommandListBuilder = CommandListBuilder(
    Vector(OpenDoubleSquareBracket(), op)
  )

  final case class CDo(op: CommandOp) extends CommandOp

  final case class CWhile(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]` (doCommand: CDo) =
      copy(conseqCmds = self.conseqCmds :+ CloseDoubleSquareBracket() :+ doCommand)

    def Done =
      ScriptBuilder(Vector(self, CDone()))
      
  }

  def Do(op: CommandOp) = CDo(op)

  object While {
    def `[[`(op: CommandOp) = CWhile(Vector(OpenDoubleSquareBracket(), op))
  }

  final case class ScriptBuilder(acc: Vector[CommandOp]) extends CommandOp {
    self =>

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) =>
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    def END = self
    
    def Done(op: CommandOp) =
      self.copy(acc = acc :+ CDone() :+ ScriptLine() :+ op)


    def o(op: CommandOp) =
      self.copy(acc = (acc :+ ScriptLine()) ++ decomposeOnion(op))


  }

  final case class SheBang(s: String) extends CommandOp

  implicit class CmdSyntax(s: StringContext) {
    def du(args: Any*) =
      SimpleCommand("du", CmdArgCtx(args.toVector, s))
  }
}
