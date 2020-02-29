object domain {

  sealed trait CommandOp
  case class SimpleCommand(name: String, args: Vector[String]) extends CommandOp

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

}
