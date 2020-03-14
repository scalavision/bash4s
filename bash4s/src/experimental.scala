package bash4s
object experimental {

  sealed abstract class CommandOp()
  final case class ScriptLine() extends CommandOp
  sealed trait CommandArg extends CommandOp

  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg

  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
    def :+(arg: String) = copy(args = self.args :+ arg)
  }
  final case class EmptyArg() extends CommandArg

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

    def &(cmdList: CommandListOp) =
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
      CommandListBuilder(Vector(SubShellStart(), self, Amper(), SubShellEnd()))

    def `;` = copy(cmds = self.cmds :+ Semi())
    CommandListBuilder(Vector(SubShellStart(), self, Semi(), SubShellEnd()))

    def `\n` =
      CommandListBuilder(
        Vector(SubShellStart(), self, NewLine(), SubShellEnd())
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

  final case class CommandListBuilder(cmds: Vector[CommandListOp])
      extends CommandListOp { self =>

    def unary_! = self.copy(Negate() +: cmds)

    def ||(pipelineOp: PipelineOp) =
      copy(cmds = (self.cmds :+ Or()) :+ pipelineOp)

    def &&(pipelineOp: PipelineOp) =
      copy(cmds = (self.cmds :+ And()) :+ pipelineOp)

    def &(cmdList: CommandListOp) =
      copy(cmds = (self.cmds :+ Amper()) :+ cmdList)

    def |(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)

    def |&(simpleCommand: SimpleCommand) =
      copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)
  }

  final case class SubShellStart() extends CommandListOp
  final case class SubShellEnd() extends CommandListOp

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

    def o(op: CommandOp) =
      self.copy(acc = (acc :+ ScriptLine()) ++ decomposeOnion(op))

  }

  final case class SheBang(s: String) extends CommandOp

  implicit class CmdSyntax(s: StringContext) {
    def du(args: Any*) =
      SimpleCommand("du", CmdArgCtx(args.toVector, s))
  }
}
