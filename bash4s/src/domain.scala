package bash

object domain {

  sealed trait CommandOp

  sealed trait CommandArg extends CommandOp
  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg
  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
    def :+(arg: String) = self.copy(args = args :+ arg)
  }
  final case class SimpleCommand(name: String, args: CommandArg)
      extends CommandOp

  sealed trait VariableValue extends CommandOp
  final case class BString(value: String) extends VariableValue
  final case class BSubCommand(value: Vector[CommandOp]) extends VariableValue
  final case class BEmpty() extends VariableValue

  sealed trait SheBang extends CommandOp
  final case class Bash(value: String) extends SheBang
  final case class Sh(value: String) extends SheBang
  final case class Zsh(value: String) extends SheBang
  final case class Scala(value: String) extends SheBang
  final case class Perl(value: String) extends SheBang
  final case class Python(value: String) extends SheBang

  final case class BashVariable(name: String, value: VariableValue)
      extends CommandOp {
    def `=`(text: String) = this.copy(value = BString(text))
    def `=`(op: ScriptBuilder) = {
      val cmdOps = op.acc.foldLeft(Vector.empty[CommandOp]) { (acc, op1) =>
        acc ++ op.decomposeOnion(op1)
      }
      this.copy(value = BSubCommand(cmdOps))
    }
  }

  final case class FileTypeOp(path: String) extends CommandOp

  final case class END() extends CommandOp
  final case class TRUE() extends CommandOp
  final case class FALSE() extends CommandOp

  sealed trait PipeOp extends CommandOp
  final case class PipeStdOut() extends PipeOp
  final case class PipeStdOutWithErr() extends PipeOp
  final case class TimedPipeline() extends PipeOp
  final case class NegatePipelineExitStatus() extends PipeOp

  sealed trait CommandList extends CommandOp
  final case class Semi() extends CommandList
  final case class Amper() extends CommandList
  final case class And() extends CommandList
  final case class Or() extends CommandList
  final case class NewLine() extends CommandList

  sealed trait Loop extends CommandOp
  final case class LUntil() extends Loop
  final case class LWhile() extends Loop
  final case class LFor() extends Loop
  final case class LDo() extends Loop
  final case class LDone() extends Loop
  final case class LIn() extends Loop

  sealed trait Conditional extends CommandOp
  final case class CIf() extends Conditional
  final case class CThen() extends Conditional
  final case class CElse() extends Conditional
  final case class CFi() extends Conditional

  sealed trait CommandSubstitution extends CommandOp
  final case class SubCommandStart() extends CommandSubstitution
  final case class SubCommandEnd() extends CommandSubstitution

  sealed trait ProcessSubstitution extends CommandOp
  final case class ProcCommandStart() extends ProcessSubstitution
  final case class ProcCommandEnd() extends ProcessSubstitution

  sealed trait Redirections extends CommandOp
  final case class StdOut() extends Redirections
  final case class StdErr() extends Redirections
  final case class AppendStdOut() extends Redirections
  final case class StdOutWithStdErr() extends Redirections
  final case class AppendStdOutWithStdErr() extends Redirections
  final case class RedirectStdOutWithStdErr() extends Redirections
  final case class CloseStdOut() extends Redirections
  final case class CloseStdIn() extends Redirections

  final case class CloseFileDescriptor(fileDescriptor: Int) extends Redirections
  final case class MergeFileDescriptorsToSingleStream(
      descriptor1: Int,
      descriptor2: Int
  ) extends Redirections

  case class ScriptBuilder(acc: Vector[CommandOp]) extends CommandOp { self =>

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) =>
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    def o(op: CommandOp) = self.copy((acc :+ NewLine()) ++ decomposeOnion(op))
    def `;`(op: CommandOp) = self.copy((acc :+ Semi()) ++ decomposeOnion(op))
    def &(op: CommandOp) = self.copy((acc :+ Amper()) ++ decomposeOnion(op))
    def &&(op: CommandOp) = self.copy((acc :+ And()) ++ decomposeOnion(op))
    def ||(op: CommandOp) = self.copy((acc :+ Or()) ++ decomposeOnion(op))
    def `\n`(op: CommandOp) =
      self.copy((acc :+ NewLine()) ++ decomposeOnion(op))
    def |(op: CommandOp) =
      self.copy((acc :+ PipeStdOut()) ++ decomposeOnion(op))
    def |&(op: CommandOp) =
      self.copy((acc :+ PipeStdOutWithErr()) ++ decomposeOnion(op))
    def time(op: CommandOp) =
      self.copy((acc :+ TimedPipeline()) ++ decomposeOnion(op))
    def !(op: CommandOp) =
      self.copy((acc :+ NegatePipelineExitStatus()) ++ decomposeOnion(op))
    def >(op: CommandOp) = self.copy((acc :+ StdOut()) ++ decomposeOnion(op))
    def `2>`(op: CommandOp) = self.copy((acc :+ StdErr()) ++ decomposeOnion(op))
    def >>(op: CommandOp) =
      self.copy((acc :+ AppendStdOut()) ++ decomposeOnion(op))
    def &>(op: CommandOp) =
      self.copy((acc :+ StdOutWithStdErr()) ++ decomposeOnion(op))
    def &>>(op: CommandOp) =
      self.copy((acc :+ AppendStdOutWithStdErr()) ++ decomposeOnion(op))
    def `2>&1`(op: CommandOp) =
      self.copy((acc :+ RedirectStdOutWithStdErr()) ++ decomposeOnion(op))
    def <&-(op: CommandOp) =
      self.copy((acc :+ CloseStdOut()) ++ decomposeOnion(op))
    def >&-(op: CommandOp) =
      self.copy((acc :+ CloseStdIn()) ++ decomposeOnion(op))
    def <(file: FileTypeOp) = self.copy(acc = acc :+ file)

    def <(p: ScriptBuilder) =
      self.copy(acc =
        (acc :+ ProcCommandStart()) ++ (p.acc
          .foldLeft(Vector.empty[CommandOp]) { (acc1, op1) =>
            acc1 ++ p.decomposeOnion(op1)
          } :+ ProcCommandEnd())
      )

    // This should have been $, but it seems there is some infix presedence that
    // destroys the ordering of commands. Therefor we try to use ^ instead ..
    def ^(p: ScriptBuilder) =
      self.copy(acc =
        (acc :+ SubCommandStart()) ++ (p.acc
          .foldLeft(Vector.empty[CommandOp]) { (acc1, op1) =>
            acc1 ++ p.decomposeOnion(op1)
          } :+ SubCommandEnd())
      )

    def >&-(fileDescriptor: Int) =
      self.copy(acc = acc :+ CloseFileDescriptor(fileDescriptor))
    def >&(desc1: Int, desc2: Int) =
      self.copy(acc = acc :+ MergeFileDescriptorsToSingleStream(desc1, desc2))
  }

}
