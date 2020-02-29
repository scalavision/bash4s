package bash

object domain {

  sealed trait CommandOp

  sealed trait CommandArg extends CommandOp
  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg
  final case class CmdArgs(args: Vector[String]) extends CommandArg
  final case class SimpleCommand(name: String, args: CommandArg)
      extends CommandOp

  sealed trait VariableValue extends CommandOp
  final case class BString(value: String) extends VariableValue
  final case class BSubCommand(value: CommandOp) extends VariableValue
  final case class BEmpty() extends VariableValue

  final case class BashVariable(name: String, value: VariableValue)
      extends CommandOp {
    def `=`(text: String) = this.copy(value = BString(text))
    def =&(op: CommandOp) = this.copy(value = BSubCommand(op))
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

  case class ScriptBuilder[A <: CommandOp](acc: Vector[A]) extends CommandOp {
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

    def o[B <: A](op: CommandOp) =
      self.copy((acc :+ NewLine()) ++ decomposeOnion(op))
    def `;`[B <: A](op: CommandOp) =
      self.copy((acc :+ Semi()) ++ decomposeOnion(op))
    def &[B <: A](op: CommandOp) =
      self.copy((acc :+ Amper()) ++ decomposeOnion(op))
    def &&[B <: A](op: CommandOp) =
      self.copy((acc :+ And()) ++ decomposeOnion(op))
    def ||[B <: A](op: CommandOp) =
      self.copy((acc :+ Or()) ++ decomposeOnion(op))
    def `\n`[B <: A](op: CommandOp) =
      self.copy((acc :+ NewLine()) ++ decomposeOnion(op))
    def |[B <: A](op: CommandOp) =
      self.copy((acc :+ PipeStdOut()) ++ decomposeOnion(op))
    def |&[B <: A](op: CommandOp) =
      self.copy((acc :+ PipeStdOutWithErr()) ++ decomposeOnion(op))
    def time[B <: A](op: CommandOp) =
      self.copy((acc :+ TimedPipeline()) ++ decomposeOnion(op))
    def ![B <: A](op: CommandOp) =
      self.copy((acc :+ NegatePipelineExitStatus()) ++ decomposeOnion(op))
  }

}
