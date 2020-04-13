package bash4s.scripts

import bash4s.domain._
import bash4s.ScriptLinter


abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def op: CommandOp
  def txt = op.txt
  def run() = op.run(name)
  def param: ScriptMeta
  def args: CommandOp = NoOp()
//  def env: CommandOp = NoOp()

  def script = {
   
    val comments = param.description.foldLeft(""){(acc,c) =>
      c match {
        case '\n' => acc + "\n #  "
        case _ => acc + c
      }
    }.reverse.dropWhile(_ != '#').drop(1).reverse + "\n"

    val argComments = param.argOpt.zipWithIndex.foldLeft(""){ (acc, ia) =>
      val (a, index) = ia
      acc + s"\n # $$${index + 1} (${a.long}): ${a.description}" 
    }

    // If scripts are merged, their environment also needs to be merged,
    // hence we need to reindex the positional parameters, i.e. $1, $2 .. $8 etc.
    val argParam: Vector[BashVariable] = {
      val params: Vector[BashVariable] = args match {
          case ScriptBuilder(acc) => acc collect {
            case b: BashVariable => b
          }
          case _ => throw new Exception("You can only use BashVariable as a command line argument")
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