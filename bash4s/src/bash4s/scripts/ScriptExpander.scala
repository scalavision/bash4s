package bash4s.scripts

import bash4s.domain._
object ScriptIdentity {

  // Identity function, going through this function should not change anything
  def id(op: CommandOp): CommandOp =  op match {
    case DevFd(op0@_) => DevFd(op0)
    case DevTcp(op0@_, op1@_) => DevTcp(op0, op1) 
    case DevUdp(op0@_, op1@_) => DevUdp(op0, op1) 
    case AnsiCQuoted(op0@_) => AnsiCQuoted(op0)
    case ArithmeticExpression(op0@_) => ArithmeticExpression(op0)
    case ArrayVariable(op0@_) => ArrayVariable(op0)
    case BashCliArgVariable(op0@_, op1@_) => BashCliArgVariable(op0, id(op1)) 
    case BashCliFlagArgVariable(op0@_, op1@_, op2@_) => BashCliFlagArgVariable(op0, op1, op2) 
    case BashCliOptArgVariable(op0@_, op1@_, op2@_) => BashCliOptArgVariable(op0, op1, op2) 
    case BashCliVecArgVariable(op0@_, vec1@_, op2@_) => BashCliVecArgVariable(op0, vec1.map(id),op2) 
    case BashVariable(op0@_, op1@_, op2@_, op3@_) => BashVariable(op0, op1, op2, op3) 
    case CDo(op0@_) => CDo(id(op0)) 
    case CElif(op0@_) => CElif(id(op0)) 
    case CElse(op0@_) => CElse(id(op0)) 
    case CFileDescriptorIsOpenAndReferTerminal(op0@_, op1@_) => CFileDescriptorIsOpenAndReferTerminal(id(op0), op1) 
    case CFor(op0@_) => CFor(op0.map(id)) 
    case CGroupIdBitSet(op0@_, op1@_) => CGroupIdBitSet(id(op0), op1) 
    case CIf(op0@_, op1@_) => CIf(op0.map(id), op1.map(id)) 
    case CIfIsFile(op0@_, op1@_) => CIfIsFile(id(op0), op1) 
    case CIn(op0@_) => CIn(id(op0)) 
    case CIsBlock(op0@_, op1@_) => CIsBlock(id(op0), op1) 
    case CIsCharacter(op0@_, op1@_) => CIsCharacter(id(op0), op1) 
    case CIsDirectory(op0@_, op1@_) => CIsDirectory(id(op0), op1) 
    case CIsExecutable(op0@_, op1@_) => CIsExecutable(id(op0), op1) 
    case CIsFile(op0@_, op1@_) => CIsFile(id(op0), op1) 
    case CIsGreaterThanZero(op0@_, op1@_) => CIsGreaterThanZero(id(op0), op1) 
    case CIsModifiedSinceLastRead(op0@_, op1@_) => CIsModifiedSinceLastRead(id(op0), op1) 
    case CIsNamedPipe(op0@_, op1@_) => CIsNamedPipe(id(op0), op1) 
    case CIsOwnedByEffectiveGroupId(op0@_, op1@_) => CIsOwnedByEffectiveGroupId(id(op0), op1) 
    case CIsOwnedByEffectiveUserId(op0@_, op1@_) => CIsOwnedByEffectiveUserId(id(op0), op1) 
    case CIsReadAble(op0@_, op1@_) => CIsReadAble(id(op0), op1) 
    case CIsSocket(op0@_, op1@_) => CIsSocket(id(op0), op1) 
    case CIsSymbolLink(op0@_, op1@_) => CIsSymbolLink(id(op0), op1) 
    case CIsSymbolicLink(op0@_, op1@_) => CIsSymbolicLink(id(op0), op1) 
    case CIsWritable(op0@_, op1@_) => CIsWritable(id(op0), op1) 
    case CStickyBitSet(op0@_, op1@_) => CStickyBitSet(id(op0), op1) 
    case CThen(op0@_) => CThen(id(op0)) 
    case CUntil(op0@_, op1@_) => CUntil(op0.map(id), op1.map(id)) 
    case CUserIdBitSet(op0@_, op1@_) => CUserIdBitSet(id(op0), op1) 
    case CWhile(op0@_, op1@_) => CWhile(op0.map(id), op1.map(id)) 
    case CmdArgCtx(op0@_, op1@_) => CmdArgCtx(op0, op1) 
    case CmdArgs(op0@_) => CmdArgs(op0) 
    case CommandListBuilder(op0@_) => CommandListBuilder(op0.map(id)) 
    case CommentLine(op0@_, op1@_) => CommentLine(op0, op1) 
    case ConditionalBuilder(op0@_) => ConditionalBuilder(op0.map(id)) 
    case DebugValue(op0@_) => DebugValue(op0)
    case FileDescriptor(op0@_) => FileDescriptor(op0)
    case FileName(op0@_, op1@_) => FileName(op0, op1) 
    case FilePath(op0@_, op1@_, op2@_) => FilePath(op0, op1, op2) 
    case FolderPath(op0@_, op1@_) => FolderPath(op0, op1) 
    case HereDoc(op0@_, op1@_, op2@_) => HereDoc(op0, op1, op2) 
    case HereString(op0@_, op1@_) => HereString(op0, op1) 
    case IntVariable(op0@_) => IntVariable(op0) 
    case LocalizationString(op0@_) => LocalizationString(op0) 
    case ParameterExpander(op0@_) => ParameterExpander(op0) 
    case ParameterExpanderVariable(op0@_) => ParameterExpanderVariable(op0) 
    case PipelineBuilder(op0@_) => PipelineBuilder(op0.map(id)) 
    case RelFolderPath(op0@_) => RelFolderPath(op0) 
    case RelPath(op0@_, op1@_) => RelPath(op0, op1) 
    case SheBang(op0@_) => SheBang(op0) 
    case SimpleCommand(op0@_, op1@_, op2@_, op3@_) => SimpleCommand(op0, op1, op2.map(id), op3.map(id)) 
    case SubShellVariable(op0@_) => SubShellVariable(id(op0)) 
    case TextVariable(op0@_) => TextVariable(op0) 
    case a => a
  }

}
object ScriptExpander {

  private def searchCommandLineArguments(op: CommandOp): Vector[BashVariable] = op match {
    case PipelineBuilder(op0@_) => searchCommandLineArguments(op)
    case CommandListBuilder(op0@_) =>  searchCommandLineArguments(op)
    case ScriptBuilder(acc) => acc.collect { case b: BashVariable => b }
    case b: BashVariable => Vector(b)
    case _ => Vector.empty[BashVariable]
  } 

  def extractBashVariables(op: CommandOp): Map[String, BashVariable] = 
    searchCommandLineArguments(op).map { b =>
      b.name -> b
    }.toMap

  def expandBashVariable(op: CommandOp, mem: Map[String, BashVariable]): CommandOp = op match {
    
    case PipelineBuilder(op0@_) => 
      PipelineBuilder(op0.map(b => expandBashVariable(b, mem))) 

    case ScriptBuilder(acc) => 
      ScriptBuilder(acc.map(b => expandBashVariable(b, mem)))

    case CommandListBuilder(op0@_) => 
      CommandListBuilder(op0.map(b => expandBashVariable(b, mem))) 

    case SimpleCommand(name, arg, postCommands, preCommands) => 
      val cmdArgs = arg match {
        case CmdArgCtx(args, strCtx) => CmdArgCtx(args map {
          case b: BashVariable => 
            val optV: Option[BashVariable] = mem.get(b.name)      
            optV match {
              case None => 
                b
              case Some(x) => x.value
            }
          case a => a
        }, strCtx)
        case _ => arg
      }

      SimpleCommand(name, cmdArgs, postCommands, preCommands)

    case b: BashVariable =>
      val optV: Option[BashVariable] = mem.get(b.name)      
      optV match {
        case None => 
          b
        case Some(x) => 
          x.value
      }

    case a => a

  }

  def expandAll(op: CommandOp): CommandOp = op match {
    case DevFd(op0@_) => DevFd(op0)
    case DevTcp(op0@_, op1@_) => DevTcp(op0, op1) 
    case DevUdp(op0@_, op1@_) => DevUdp(op0, op1) 
    case AnsiCQuoted(op0@_) => AnsiCQuoted(op0)
    case ArithmeticExpression(op0@_) => ArithmeticExpression(op0)
    case ArrayVariable(op0@_) => ArrayVariable(op0)
    case BashCliArgVariable(op0@_, op1@_) => BashCliArgVariable(op0, expandAll(op1)) 
    case BashCliFlagArgVariable(op0@_, op1@_, op2@_) => BashCliFlagArgVariable(op0, op1, op2) 
    case BashCliOptArgVariable(op0@_, op1@_, op2@_) => BashCliOptArgVariable(op0, op1, op2) 
    case BashCliVecArgVariable(op0@_, vec1@_, op2@_) => BashCliVecArgVariable(op0, vec1.map(expandAll),op2) 
    case BashVariable(op0@_, op1@_, op2@_, op3@_) => BashVariable(op0, op1, op2, op3) 
    case CDo(op0@_) => CDo(expandAll(op0)) 
    case CElif(op0@_) => CElif(expandAll(op0)) 
    case CElse(op0@_) => CElse(expandAll(op0)) 
    case CFileDescriptorIsOpenAndReferTerminal(op0@_, op1@_) => CFileDescriptorIsOpenAndReferTerminal(expandAll(op0), op1) 
    case CFor(op0@_) => CFor(op0.map(expandAll)) 
    case CGroupIdBitSet(op0@_, op1@_) => CGroupIdBitSet(expandAll(op0), op1) 
    case CIf(op0@_, op1@_) => CIf(op0.map(expandAll), op1.map(expandAll)) 
    case CIfIsFile(op0@_, op1@_) => CIfIsFile(expandAll(op0), op1) 
    case CIn(op0@_) => CIn(expandAll(op0)) 
    case CIsBlock(op0@_, op1@_) => CIsBlock(expandAll(op0), op1) 
    case CIsCharacter(op0@_, op1@_) => CIsCharacter(expandAll(op0), op1) 
    case CIsDirectory(op0@_, op1@_) => CIsDirectory(expandAll(op0), op1) 
    case CIsExecutable(op0@_, op1@_) => CIsExecutable(expandAll(op0), op1) 
    case CIsFile(op0@_, op1@_) => CIsFile(expandAll(op0), op1) 
    case CIsGreaterThanZero(op0@_, op1@_) => CIsGreaterThanZero(expandAll(op0), op1) 
    case CIsModifiedSinceLastRead(op0@_, op1@_) => CIsModifiedSinceLastRead(expandAll(op0), op1) 
    case CIsNamedPipe(op0@_, op1@_) => CIsNamedPipe(expandAll(op0), op1) 
    case CIsOwnedByEffectiveGroupId(op0@_, op1@_) => CIsOwnedByEffectiveGroupId(expandAll(op0), op1) 
    case CIsOwnedByEffectiveUserId(op0@_, op1@_) => CIsOwnedByEffectiveUserId(expandAll(op0), op1) 
    case CIsReadAble(op0@_, op1@_) => CIsReadAble(expandAll(op0), op1) 
    case CIsSocket(op0@_, op1@_) => CIsSocket(expandAll(op0), op1) 
    case CIsSymbolLink(op0@_, op1@_) => CIsSymbolLink(expandAll(op0), op1) 
    case CIsSymbolicLink(op0@_, op1@_) => CIsSymbolicLink(expandAll(op0), op1) 
    case CIsWritable(op0@_, op1@_) => CIsWritable(expandAll(op0), op1) 
    case CStickyBitSet(op0@_, op1@_) => CStickyBitSet(expandAll(op0), op1) 
    case CThen(op0@_) => CThen(expandAll(op0)) 
    case CUntil(op0@_, op1@_) => CUntil(op0.map(expandAll), op1.map(expandAll)) 
    case CUserIdBitSet(op0@_, op1@_) => CUserIdBitSet(expandAll(op0), op1) 
    case CWhile(op0@_, op1@_) => CWhile(op0.map(expandAll), op1.map(expandAll)) 
    case CmdArgCtx(op0@_, op1@_) => CmdArgCtx(op0, op1) 
    case CmdArgs(op0@_) => CmdArgs(op0) 
    case CommandListBuilder(op0@_) => CommandListBuilder(op0.map(expandAll)) 
    case CommentLine(op0@_, op1@_) => CommentLine(op0, op1) 
    case ConditionalBuilder(op0@_) => ConditionalBuilder(op0.map(expandAll)) 
    case DebugValue(op0@_) => DebugValue(op0)
    case FileDescriptor(op0@_) => FileDescriptor(op0)
    case FileName(op0@_, op1@_) => FileName(op0, op1) 
    case FilePath(op0@_, op1@_, op2@_) => FilePath(op0, op1, op2) 
    case FolderPath(op0@_, op1@_) => FolderPath(op0, op1) 
    case HereDoc(op0@_, op1@_, op2@_) => HereDoc(op0, op1, op2) 
    case HereString(op0@_, op1@_) => HereString(op0, op1) 
    case IntVariable(op0@_) => IntVariable(op0) 
    case LocalizationString(op0@_) => LocalizationString(op0) 
    case ParameterExpander(op0@_) => ParameterExpander(op0) 
    case ParameterExpanderVariable(op0@_) => ParameterExpanderVariable(op0) 
    case PipelineBuilder(op0@_) => PipelineBuilder(op0.map(expandAll)) 
    case ScriptBuilder(acc) => ScriptBuilder(acc.map(expandAll))
    case RelFolderPath(op0@_) => RelFolderPath(op0) 
    case RelPath(op0@_, op1@_) => RelPath(op0, op1) 
    case SheBang(op0@_) => SheBang(op0) 
    case SimpleCommand(op0@_, op1@_, op2@_, op3@_) => SimpleCommand(op0, op1, op2.map(expandAll), op3.map(expandAll)) 
    case SubShellVariable(op0@_) => SubShellVariable(expandAll(op0)) 
    case TextVariable(op0@_) => TextVariable(op0) 
    case a => a
  }

  def apply(script: Script) = {
    println(script)
  }

  def handleAll(op: CommandOp) = op match {
    case ScriptBuilder(acc@_) => ???
    case DevFd(op0@_) => DevFd(op0)
    case DevTcp(op0@_, op1@_) => DevTcp(op0, op1) 
    case DevUdp(op0@_, op1@_) => DevUdp(op0, op1) 
    case DevNull =>  ???
    case DevRandom =>  ???
    case DevStdErr =>  ???
    case DevStdIn =>  ???
    case DevStdOut =>  ???
    case Amper() =>  ???
    case And() =>  ???
    case AnsiCQuoted(op0@_) => ???
    case AppendStdOut() =>  ???
    case AppendStdOutWithSdErr() =>  ???
    case ArithmeticExpression(op0@_) => ???
    case ArrayVariable(op0@_) => ???
    case BashCliArgVariable(op0@_, op1@_) => ???
    case BashCliFlagArgVariable(op0@_, op1@_, op2@_) => ???
    case BashCliOptArgVariable(op0@_, op1@_, op2@_) => ???
    case BashCliVecArgVariable(op0@_, op1@_, op2@_) => ???
    case BashVariable(op0@_, op1@_, op2@_, op3@_) => ???
    case BreakLine() =>  ???
    case CDo(op0@_) => ???
    case CDone() =>  ???
    case CElif(op0@_) => ???
    case CElse(op0@_) => ???
    case CFi() =>  ???
    case CFileDescriptorIsOpenAndReferTerminal(op0@_, op1@_) => ???
    case CFor(op0@_) => ???
    case CGroupIdBitSet(op0@_, op1@_) => ???
    case CIf(op0@_, op1@_) => ???
    case CIfIsFile(op0@_, op1@_) => ???
    case CIn(op0@_) => ???
    case CIsBlock(op0@_, op1@_) => ???
    case CIsCharacter(op0@_, op1@_) => ???
    case CIsDirectory(op0@_, op1@_) => ???
    case CIsExecutable(op0@_, op1@_) => ???
    case CIsFile(op0@_, op1@_) => ???
    case CIsGreaterThanZero(op0@_, op1@_) => ???
    case CIsModifiedSinceLastRead(op0@_, op1@_) => ???
    case CIsNamedPipe(op0@_, op1@_) => ???
    case CIsOwnedByEffectiveGroupId(op0@_, op1@_) => ???
    case CIsOwnedByEffectiveUserId(op0@_, op1@_) => ???
    case CIsReadAble(op0@_, op1@_) => ???
    case CIsSocket(op0@_, op1@_) => ???
    case CIsSymbolLink(op0@_, op1@_) => ???
    case CIsSymbolicLink(op0@_, op1@_) => ???
    case CIsWritable(op0@_, op1@_) => ???
    case CStickyBitSet(op0@_, op1@_) => ???
    case CThen(op0@_) => ???
    case CUntil(op0@_, op1@_) => ???
    case CUserIdBitSet(op0@_, op1@_) => ???
    case CWhile(op0@_, op1@_) => ???
    case CloseDoubleSquareBracket() =>  ???
    case CloseGroupInContext() =>  ???
    case CloseStdIn() =>  ???
    case CloseStdOut() =>  ???
    case CloseSubShellEnv() =>  ???
    case CloseSubShellExp() =>  ???
    case CmdArgCtx(op0@_, op1@_) => ???
    case CmdArgs(op0@_) => ???
    case CommandListBuilder(op0@_) => ???
    case CommentLine(op0@_, op1@_) => ???
    case ConditionalBuilder(op0@_) => ???
    case DebugValue(op0@_) => ???
    case Dollar() =>  ???
    case EmptyArg() =>  ???
    case FileDescriptor(op0@_) => ???
    case FileName(op0@_, op1@_) => ???
    case FilePath(op0@_, op1@_, op2@_) => ???
    case FolderPath(op0@_, op1@_) => ???
    case HereDoc(op0@_, op1@_, op2@_) => ???
    case HereString(op0@_, op1@_) => ???
    case IntVariable(op0@_) => ???
    case LocalizationString(op0@_) => ???
    case Negate() =>  ???
    case NewLine() =>  ???
    case NoOp() =>  ???
    case OpenDoubleSquareBracket() =>  ???
    case OpenGroupInContext() =>  ???
    case OpenSubShellEnv() =>  ???
    case OpenSubShellExp() =>  ???
    case Or() =>  ???
    case ParameterExpander(op0@_) => ???
    case ParameterExpanderVariable(op0@_) => ???
    case PipeWithError() =>  ???
    case PipeWithStdOut() =>  ???
    case PipelineBuilder(op0@_) => ???
    case RedirectStdOutWithStdErr() =>  ???
    case RelFolderPath(op0@_) => ???
    case RelPath(op0@_, op1@_) => ???
    case ScriptLine() =>  ???
    case Semi() =>  ???
    case SheBang(op0@_) => ???
    case SimpleCommand(op0@_, op1@_, op2@_, op3@_) => ???
    case StdErr() =>  ???
    case StdIn() =>  ???
    case StdOut() =>  ???
    case StdOutWithStdErr() =>  ???
    case SubShellVariable(op0@_) => ???
    case TextVariable(op0@_) => ???
    case UnsetArrayVariable() =>  ???
    case UnsetVariable() =>  ???

  }
}

