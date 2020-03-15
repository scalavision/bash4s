package old.bash

object domain {

  sealed trait CommandOp

  final case class DebugString(value: String) extends CommandOp
  final case class ConditionalExpression(s: String, op: CommandOp)
      extends CommandOp

  sealed trait CommandArg extends CommandOp
  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg
  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
    def :+(arg: String) = copy(args = self.args :+ arg)
  }
  final case class SimpleCommand(name: String, args: CommandArg)
      extends CommandOp

  sealed trait VariableValue extends CommandOp
  final case class BString(value: String) extends VariableValue
  final case class BSubCommand(value: Vector[CommandOp]) extends VariableValue
  final case class BEmpty() extends VariableValue

  final case class RefVariable(name: String, value: VariableValue)
      extends CommandOp

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
    def $ = RefVariable(name, value)
  }

  final case class Host(value: String) extends AnyVal
  final case class Port(value: Int) extends AnyVal
  final case class FileDescriptor(value: Int) extends AnyVal
  final case class FileExtension(extension: Vector[String])
  final case class FolderPath(folders: Vector[String])
  final case class BaseName(value: String) extends AnyVal

  sealed trait FileTypeOp extends CommandOp
  final case class FileName(baseName: BaseName, fileExtension: FileExtension)
      extends FileTypeOp
  final case class FilePath(
      root: Char,
      folderPath: FolderPath,
      fileName: FileName
  ) extends FileTypeOp
  final case class RelPath(folderPath: FolderPath, fileName: FileName)
      extends FileTypeOp
  final case class RegexFileSearch(value: String) extends FileTypeOp
  final case object `/dev/stdin` extends FileTypeOp
  final case object `/dev/stdout` extends FileTypeOp
  final case object `/dev/stderr` extends FileTypeOp
  final case class `/dev/fd`(fileDescriptor: FileDescriptor) extends FileTypeOp
  final case class `/dev/tcp`(host: Host, port: Port) extends FileTypeOp
  final case class `/dev/udp`(host: Host, port: Port) extends FileTypeOp
  final case object `/dev/null` extends FileTypeOp
  final case object `/dev/random` extends FileTypeOp

  final case class END() extends CommandOp

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
  final case class LUntil(op: CommandOp) extends Loop
  final case class LFor(op: CommandOp) extends Loop
  final case class LWhile(op: CommandOp) extends Loop

  sealed trait ConditionalExpr extends CommandOp
  final case class CDo(op: CommandOp) extends ConditionalExpr
  final case class CThen(op: CommandOp) extends ConditionalExpr
  final case class CElse(op: CommandOp) extends ConditionalExpr
  final case class OpenSquareBracket(op: CommandOp) extends ConditionalExpr

  sealed trait LoopCtrl extends CommandOp
  final case class LIn() extends LoopCtrl
  final case class LDone() extends LoopCtrl

  sealed trait Conditional extends CommandOp
  final case class CIf() extends Conditional
  final case class CUntil() extends Conditional
  final case class CElif() extends Conditional
  final case class CFi() extends Conditional
  final case class CTrue() extends Conditional
  final case class CFalse() extends Conditional
  final case class CloseSquareBracket() extends Conditional

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

    def `;\n`(op: CommandOp) =
      self.copy(acc = (acc :+ Semi() :+ NewLine()) ++ decomposeOnion(op))
    def `;`(op: CommandOp) =
      self.copy(acc = (acc :+ Semi()) ++ decomposeOnion(op))
    def o(op: CommandOp) =
      self.copy(acc = (acc :+ NewLine()) ++ decomposeOnion(op))
    def &(op: CommandOp) =
      self.copy(acc = (acc :+ Amper()) ++ decomposeOnion(op))
    def &&(op: CommandOp) =
      self.copy(acc = (acc :+ And()) ++ decomposeOnion(op))
    def ||(op: CommandOp) = self.copy(acc = (acc :+ Or()) ++ decomposeOnion(op))
    def `\n`(op: CommandOp) =
      self.copy(acc = (acc :+ NewLine()) ++ decomposeOnion(op))
    def |(op: CommandOp) =
      self.copy(acc = (acc :+ PipeStdOut()) ++ decomposeOnion(op))
    def |&(op: CommandOp) =
      self.copy(acc = (acc :+ PipeStdOutWithErr()) ++ decomposeOnion(op))
    def time(op: CommandOp) =
      self.copy(acc = (acc :+ TimedPipeline()) ++ decomposeOnion(op))
    def !(op: CommandOp) =
      self.copy(acc = (acc :+ NegatePipelineExitStatus()) ++ decomposeOnion(op))
    def >(op: CommandOp) =
      self.copy(acc = (acc :+ StdOut()) ++ decomposeOnion(op))
    def `2>`(op: CommandOp) =
      self.copy(acc = (acc :+ StdErr()) ++ decomposeOnion(op))
    def >>(op: CommandOp) =
      self.copy(acc = (acc :+ AppendStdOut()) ++ decomposeOnion(op))
    def &>(op: CommandOp) =
      self.copy(acc = (acc :+ StdOutWithStdErr()) ++ decomposeOnion(op))
    def &>>(op: CommandOp) =
      self.copy(acc = (acc :+ AppendStdOutWithStdErr()) ++ decomposeOnion(op))
    def `2>&1`(op: CommandOp) =
      self.copy(acc = (acc :+ RedirectStdOutWithStdErr()) ++ decomposeOnion(op))
    def <&-(op: CommandOp) =
      self.copy(acc = (acc :+ CloseStdOut()) ++ decomposeOnion(op))
    def >&-(op: CommandOp) =
      self.copy(acc = (acc :+ CloseStdIn()) ++ decomposeOnion(op))
    def In(op: CommandOp) =
      self.copy(acc = (acc :+ LIn()) ++ decomposeOnion(op))
    def If(op: CommandOp) =
      self.copy(acc = (acc :+ CIf()) ++ decomposeOnion(op))
    def Until(op: CommandOp) =
      self.copy(acc = (acc :+ CUntil()) ++ decomposeOnion(op))
    def Elif(op: CommandOp) =
      self.copy(acc = (acc :+ CElif()) ++ decomposeOnion(op))
    def Fi(op: CommandOp) =
      self.copy(acc = (acc :+ CFi()) ++ decomposeOnion(op))
    def True(op: CommandOp) =
      self.copy(acc = (acc :+ CTrue()) ++ decomposeOnion(op))
    def False(op: CommandOp) =
      self.copy(acc = (acc :+ CFalse()) ++ decomposeOnion(op))
    def `]]`(op: CommandOp) =
      self.copy(acc = (acc :+ CloseSquareBracket()) ++ decomposeOnion(op))
    def Else(op: CommandOp) =
      self.copy(acc = (acc :+ CElse(op)))
    def Do(op: CommandOp) =
      self.copy(acc = (acc :+ CDo(op)))
    def Done(op: CommandOp) =
      self.copy(acc = (acc :+ LDone() :+ NewLine()) ++ decomposeOnion(op))
    def `[[`(op: CommandOp) =
      self.copy(acc = acc :+ OpenSquareBracket(op))
    def Fi =
      self.copy(acc = acc :+ CFi())
    def Done = self.copy(acc = acc :+ LDone())
    def o = self.copy(acc = acc :+ NewLine())
    def &^(op: CommandOp) =
      self.copy(acc = (acc :+ Amper() :+ NewLine()) ++ decomposeOnion(op))
    def !^(op: CommandOp) =
      self.copy(acc =
        (acc :+ NegatePipelineExitStatus() :+ NewLine()) ++ decomposeOnion(op)
      )
    def <(file: FileTypeOp) = self.copy(acc = acc :+ file)

    def <(p: ScriptBuilder) =
      self.copy(acc =
        (acc :+ ProcCommandStart()) ++ (p.acc
          .foldLeft(Vector.empty[CommandOp]) { (acc1, op1) =>
            acc1 ++ p.decomposeOnion(op1)
          } :+ ProcCommandEnd())
      )

    // This should have been $, but it seems there is some infix presedence that
    // destroys the ordering of commands. Therefor we try to use % instead ..
    def %(p: ScriptBuilder) =
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