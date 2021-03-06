package bash4s.scripts

import bash4s.domain._
import bash4s.ScriptLinter

trait Scriptable[A] {
  def script(a: A): String
  def name: String
  def $1(a: CommandOp): Scriptable[A]
  def $2(a: CommandOp): Scriptable[A]
}

// trait ScriptParam[A] {
//   def $1(script: Scriptable[A]): 
//   //def param(a: A): ScriptMeta
// }

abstract class Script(implicit n: sourcecode.Name) { self =>
  def name = n.value
  def op: CommandOp
  def txt = op.txt
  def run() = op.run(name)
  def param: ScriptMeta
  def args: CommandOp = NoOp()

  def rename(newName: String) = new Script {
    override def name = newName
    def op = self.op
    override def txt = self.op.txt
    override def run() = self.op.run(self.name)
    override def param = self.param
    override def args = self.args
    override def script = self.script
  }

  def o(op2: CommandOp) = new Script {
    override def name = self.name
    def op = ScriptBuilder(Vector(self.op)).o(op2)
    override def txt = op.txt
    override def run() = op.run(self.name)
    override def param = self.param
    override def args = self.args
    override def script = self.script + "\n" + op2.txt
  }

  def ++ (that: Script) =
    append(that)

  def append(that: Script, otherName: Option[String] = None) = new Script {
    override def name = otherName.fold(self.name)(s => s)
    def op: CommandOp = self.op
    override def txt = self.txt + "\n" + that.txt
    def param: ScriptMeta = self.param
    override def args = (self.args, that.args) match {
      case (NoOp(), NoOp()) => NoOp()
      case (NoOp(), o) => o
      case (o, NoOp()) => o
      case (op1, op2) =>
        ScriptBuilder(Vector(op1)).o(op2)
    }
    override def script = {

      val mergedParams = {
        val params: Vector[BashVariable] = args match {
        case ScriptBuilder(acc) => acc collect {
          case b: BashVariable => b
        }
        case b: BashVariable => Vector(b)
        case NoOp() => Vector.empty[BashVariable]
        case _ => throw new Exception(s"Unhandled CommandOp: ${args}")
      }
        params.zipWithIndex.map {
          case (b, index) => b.value match {
            case bcli: BashCliOptArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString))
            case bcli: BashCliArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
            case bcli: BashCliVecArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
            case _ => b
          }
        }
      }
      

    s"""#!/usr/bin/env bash
        |set -euo pipefail
        |
        |${comments}
        |${argComments}
        |${that.argComments}
        |${mergedParams.map(_.txt).mkString("\n")}
        |${ScriptFormatter(self.op).txt}
        |${that.comments}
        |${ScriptFormatter(that.op).txt}
      """.stripMargin
    }

  }

//  def env: CommandOp = NoOp()

  def comments = param.description.foldLeft(""){(acc,c) =>
    c match {
      case '\n' => acc + "\n #  "
      case _ => acc + c
    }
  }.reverse.dropWhile(_ != '#').drop(1).reverse + "\n"

  def argComments = param.argOpt.zipWithIndex.foldLeft(""){ (acc, ia) =>
      val (a, index) = ia
      acc + s"\n # $$${index + 1} (${a.long}): ${a.description}" 
    }

  // If scripts are merged, their environment also needs to be merged,
    // hence we need to reindex the positional parameters, i.e. $1, $2 .. $8 etc.
  def argParam: Vector[BashVariable] = {
    val params: Vector[BashVariable] = args match {
        case ScriptBuilder(acc) => acc collect {
          case b: BashVariable => b
        }
        case b: BashVariable => Vector(b)
        case NoOp() => Vector.empty[BashVariable]
        case _ => throw new Exception(s"Unhandled CommandOp: ${args}")
    }
    params.zipWithIndex.map {
      case (b, index) => b.value match {
        case bcli: BashCliOptArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString))
        case bcli: BashCliArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
        case bcli: BashCliVecArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
        case _ => b
      }
    }
  }

  def script = {
    ScriptLinter.lint(
      s"""#!/usr/bin/env bash
      |set -euo pipefail
      |
      |${comments}
      |${argComments}
      |${argParam.map(_.txt).mkString("\n")}
      |${ScriptFormatter(op).txt}
      """.stripMargin
    )
  }
  
}

/* A list of most common CommandOp types, lacking some of the FileType types etc.
case AnsiCQuoted(_)=> ???
case AppendStdOut()=> ???
case AppendStdOutWithSdErr()=> ???
case ArithmeticExpression(_)=> ???
case ArrayVariable(_)=> ???
case BashCliArgVariable(_) => ???
case BashCliFlagArgVariable(_)=> ???
case BashCliOptArgVariable(_)=> ???
case BashCliVecArgVariable(_)=> ???
case BashVariable(_)=> ???
case CDo(_)=> ???
case CDone()=> ???
case CElif(_)=> ???
case CElse(_)=> ???
case CFi()=> ???
case CFileDescriptorIsOpenAndReferTerminal(_)=> ???
case CFor(_)=> ???
case CGroupIdBitSet(_)=> ???
case CIf(_)=> ???
case CIfIsFile(_)=> ???
case CIn(_)=> ???
case CIsBlock(_)=> ???
case CIsCharacter(_)=> ???
case CIsDirectory(_)=> ???
case CIsExecutable(_)=> ???
case CIsFile(_)=> ???
case CIsGreaterThanZero(_)=> ???
case CIsModifiedSinceLastRead(_)=> ???
case CIsNamedPipe(_)=> ???
case CIsOwnedByEffectiveGroupId(_)=> ???
case CIsOwnedByEffectiveUserId(_)=> ???
case CIsReadAble(_)=> ???
case CIsSocket(_)=> ???
case CIsSymbolLink(_)=> ???
case CIsSymbolicLink(_)=> ???
case CIsWritable(_)=> ???
case CStickyBitSet(_)=> ???
case CThen(_)=> ???
case CUntil(_)=> ???
case CUserIdBitSet(_)=> ???
case CWhile(_)=> ???
case CloseDoubleSquareBracket()=> ???
case CloseGroupInContext()=> ???
case CloseStdIn()=> ???
case CloseStdOut()=> ???
case CloseSubShellEnv()=> ???
case CloseSubShellExp()=> ???
case CmdArgCtx(_)=> ???
case CmdArgs(_)=> ???
case CommandListBuilder(_)=> ???
case CommentLine(_)=> ???
case ConditionalBuilder(_)=> ???
case DebugValue(_)=> ???
case Dollar()=> ???
case EmptyArg()=> ???
case FileDescriptor(_)=> ???
case FileName(_)=> ???
case FilePath(_)=> ???
case FolderPath(_)=> ???
case HereDoc(_)=> ???
case HereString(_)=> ???
case IntVariable(_)=> ???
case LocalizationString(_)=> ???
case Negate()=> ???
case NewLine()=> ???
case OpenDoubleSquareBracket()=> ???
case OpenGroupInContext()=> ???
case OpenSubShellEnv()=> ???
case OpenSubShellExp()=> ???
case Or()=> ???
case ParameterExpander(_)=> ???
case ParameterExpanderVariable(_)=> ???
case PipeWithError()=> ???
case PipeWithStdOut()=> ???
case PipelineBuilder(_)=> ???
case RedirectStdOutWithStdErr()=> ???
case RelFolderPath(_)=> ???
case RelPath(_)=> ???
case ScriptLine()=> ???
case Semi()=> ???
case SheBang(_)=> ???
case SimpleCommand(_)=> ???
case StdErr()=> ???
case StdIn()=> ???
case StdOut()=> ???
case StdOutWithStdErr()=> ???
case SubShellVariable(_)=> ???
case TextVariable(_)=> ???
case UnsetArrayVariable()=> ???
case UnsetVariable(_) => ???
*/