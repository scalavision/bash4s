package bash4s

import scala.util.Try

object domain {

  sealed abstract class CommandOp() { self =>

    val serializer = ScriptSerializer.gen[CommandOp]

    def txt = ScriptLinter.lint(
      s"""
      |${serializer.apply(this)}
      |""".stripMargin
    )

    def print() = println(txt)
    def printRich() = pprint.pprintln(txt)

    def runScript(
      script: String,
      scriptPath: os.Path,
      runPath: os.Path
    ) = {
      os.write.over(scriptPath, script)
      os.perms.set(scriptPath, "rwxr-xr-x") 
      os.proc(scriptPath).call(runPath)
    }

    def run(name: String = "")(implicit implicitName: sourcecode.Name): Unit = {

      if(implicitName.value.isEmpty()) throw new Exception("You need to provide a name for the script!")

      val fileName = if(name.isEmpty()) implicitName.value else name
      val wd = Try(os.Path(System.getProperty("java.io.tmpdir"))).getOrElse (os.pwd)
      val result = runScript(txt, wd / s"$fileName", wd)
      println(result.exitCode)
      
    }

    def save(path: os.Path) = 
      os.write.over(path, txt)

  }

  final case class ScriptLine() extends CommandOp
  sealed trait CommandArg extends CommandOp

  final case class DebugValue(value: String) extends CommandOp
  final case class CommentLine(prefix: String, arg: CmdArgCtx) extends CommandOp { self =>
    def o(op: CommandOp) =
      ScriptBuilder(Vector(self, ScriptLine(), op))
  }
  
  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
      extends CommandArg
  
  final case class HereString(prefix: String, value: CmdArgCtx) extends CommandArg
  final case class HereDoc(prefix: String, value: CmdArgCtx, end: String = "") extends CommandArg {
    def END = copy(end = "END")
  }

  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
    def :+(arg: String) = copy(args = self.args :+ arg)
  }
  final case class EmptyArg() extends CommandArg
  final case class OpenGroupInContext() extends CommandOp
  final case class CDone() extends CommandOp
  final case class CFi() extends CommandOp
  final case class CloseGroupInContext() extends CommandOp
  final case class Dollar() extends CommandOp

  final case class ArithmeticExpression(value: CmdArgCtx) extends CommandOp
  final case class ParameterExpander(value: CmdArgCtx) extends CommandOp { self => 
  
  }

  sealed trait ConditionalExpression extends CommandOp

  final case class CIfIsFile(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsBlock(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsCharacter(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsDirectory(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsFile(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CGroupIdBitSet(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsSymbolLink(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CStickyBitSet(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsNamedPipe(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsReadAble(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsGreaterThanZero(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CFileDescriptorIsOpenAndReferTerminal(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CUserIdBitSet(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsWritable(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsExecutable(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsOwnedByEffectiveGroupId(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsSymbolicLink(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsModifiedSinceLastRead(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsOwnedByEffectiveUserId(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}


final case class CIsSocket(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}

  final case class ConditionalBuilder(cmds: Vector[CommandOp]) extends ConditionalExpression { self =>
  
    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) =>
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    def &&(op: CommandOp) = copy(cmds = (self.cmds :+ And()) ++ decomposeOnion(op))
    def ||(op: CommandOp) = copy(cmds = (self.cmds :+ Or()) ++ decomposeOnion(op))
    def unary_- = self
  }

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

  sealed trait BashParameter extends CommandOp
  sealed trait SpecialParameter extends BashParameter

  sealed trait VariableValue extends CommandOp
  final case class TextVariable(value: CmdArgCtx) extends VariableValue
  final case class IntVariable(value: Int) extends VariableValue
  final case class ArrayVariable(value: CmdArgCtx) extends VariableValue
  final case class ParameterExpanderVariable(value: ParameterExpander) extends VariableValue
  final case class SubShellVariable(value: CommandOp) extends VariableValue
  final case class UnsetVariable() extends VariableValue
  final case class UnsetArrayVariable() extends VariableValue
  final case class BashCliArgVariable(
    name: String,
    value: CommandOp
  ) extends VariableValue

  final case class BashCliOptArgVariable(
    name: String,
    value: Option[CommandOp],
    param: String
  ) extends VariableValue

  final case class BashVariable(
    name: String, 
    value: VariableValue = UnsetVariable(),
    isExpanded: Boolean = false
  ) extends BashParameter { self =>
    def $ = copy(isExpanded = true)
    def `=` (txt: TextVariable) = copy(value = txt)
    def `=` (cliArg: BashCliArgVariable) = copy(value = cliArg)
    def `=` (cliArg: BashCliOptArgVariable) = copy(value = cliArg)
    def `=` (array: ArrayVariable) = copy(value = array)
    def `=` (parameterExpander: ParameterExpander) = copy(value = ParameterExpanderVariable(parameterExpander))
    def `=$` (op: CommandOp) = copy(value = SubShellVariable(op))
    def expansionSafe: String = "\"" + "$" + "{" + name.trim() + "}" + "\""
    def o(op: CommandOp) =
      ScriptBuilder(Vector(self, ScriptLine(), op))
  }
  
  final case class LocalizationString(value: CmdArgCtx) extends CommandOp
  final case class AnsiCQuoted(value: CmdArgCtx) extends CommandOp
  // These probably have to part of the CmdArgCtx !!
  // TODO: rename accordingly
  final case class Negate() extends PipelineOp

  final case class SimpleCommand(
      name: String,
      arg: CommandArg,
      postCommands: Vector[CommandOp] = Vector.empty[CommandOp],
      preCommands: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends PipelineOp { self =>

    def >(op: CommandOp) = copy(postCommands = (self.postCommands :+ StdOut()) :+ op)
    def `2>`(op: CommandOp) = copy(postCommands = (self.postCommands :+ StdErr()) :+ op)
    def >>(op: CommandOp) = copy(postCommands = (self.postCommands :+ AppendStdOut()) :+ op)
    def &>(op: CommandOp) = copy(postCommands = (self.postCommands :+ StdOutWithStdErr()) :+ op)
    def &>>(op: CommandOp) =
      copy(postCommands = (self.postCommands :+ AppendStdOutWithSdErr()) :+ op)
    def <(op: CommandOp) = copy(postCommands = (self.postCommands :+ StdIn()) :+ op)
    def `2>&1`(op: CommandOp) =
      copy(postCommands = (self.postCommands :+ RedirectStdOutWithStdErr()) :+ op)
    def <&-(op: CommandOp) = copy(postCommands = (self.postCommands :+ CloseStdIn()) :+ op)
    def >&-(op: CommandOp) = copy(postCommands = (self.postCommands :+ CloseStdOut()) :+ op)

    def & = copy(postCommands = self.postCommands :+ Amper())
    def `;` = copy(postCommands = self.postCommands :+ Semi())
    def `\n` = copy(postCommands = self.postCommands :+ NewLine())

    def unary_! = self.copy(preCommands = Negate() +: self.preCommands)

    def %(cmdList: CommandListOp) =
      copy(postCommands = postCommands :+ OpenSubShellExp() :+ cmdList :+ CloseSubShellExp())

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

    def unary_! = self.copy(cmds = Negate() +: self.cmds)

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
  final case class CloseSubShellExp() extends CommandListOp

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

    def `}`(op: CommandOp) = 
      self.copy(cmds = cmds :+ CloseGroupInContext() :+ op)
    
    def `}` = 
      self.copy(cmds = cmds :+ CloseGroupInContext())
    
    def `)`(op: CommandOp) = 
      self.copy(cmds = cmds :+ CloseSubShellEnv() :+ op)
    
    def `)` = 
      self.copy(cmds = cmds :+ CloseSubShellEnv())

    def o(op: CommandOp) =
      ScriptBuilder(Vector(self, ScriptLine(), op))
  }

  final case class CloseDoubleSquareBracket() extends CommandOp
  final case class OpenDoubleSquareBracket() extends CommandOp


  final case class CDo(op: CommandOp) extends CommandOp
  final case class CIn(op: CommandOp) extends CommandOp

  final case class CWhile(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(doCommand: CDo) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ doCommand
      )

    def Done =
      ScriptBuilder(Vector(self, CDone()))

    def Done(op: CommandOp) =
      ScriptBuilder(Vector(self, CDone(), op))
  }

  final case class CFor(args: Vector[CommandOp]) extends CommandOp { self =>
    
    def In(op: CommandOp) =
      copy(args = self.args :+ CIn(op))

    def Do(op: CommandOp) =
      copy(args = self.args :+ CDo(op))

    def Done =
      ScriptBuilder(Vector(CFor(self.args :+ CDone())))

    def Done(op: CommandOp) =
      ScriptBuilder(Vector(CFor(self.args :+ CDone() :+ op)))
  }

  case class CUntil(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(doCommand: CDo) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ doCommand
      )

    def Done =
      ScriptBuilder(Vector(self, CDone()))

    def Done(op: CommandOp) =
      ScriptBuilder(Vector(self, CDone(), op))
  }


  final case class CThen(op: CommandOp) extends CommandOp
  final case class CElse(op: CommandOp) extends CommandOp
  final case class CElif(op: CommandOp) extends CommandOp 
  
  final case class CIf(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(thenCommand: CThen) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ thenCommand
      )

    def Elif(op: CommandListOp) = {
      copy(conseqCmds =
        self.conseqCmds :+ CElif(op)
      )
    }

    def Then(op: CommandOp) =
      copy(conseqCmds =
        self.conseqCmds :+ CThen(op)
      )

    def Else(op: CommandOp) =
      copy(conseqCmds = self.conseqCmds :+ CElse(op))

    // These are used for standalone CIf, or if script is started
    // with a CIf
    def Fi =
      ScriptBuilder(Vector(self, CFi(), NewLine()))

    def Fi(op: CommandOp) =
      ScriptBuilder(Vector(self, CFi(), NewLine(), op))

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

    // These are used when the Conditional or Loop are used within a script
    def Done(op: CommandOp) =
      self.copy(acc = acc :+ CDone() :+ ScriptLine() :+ op)

    def Done =
      self.copy(acc = acc :+ CDone() :+ ScriptLine())

    def Fi(op: CommandOp) =
      self.copy(acc = acc :+ CFi() :+ ScriptLine() :+ op)

    def Fi =
      self.copy(acc = acc :+ CFi() :+ ScriptLine())
 
    def o(op: CommandOp) =
      self.copy(acc = (acc :+ ScriptLine()) ++ decomposeOnion(op))
  }

  final case class SheBang(s: String) extends CommandOp

  final case class Host(value: String) extends AnyVal
  final case class Port(value: Int) extends AnyVal

  final case class BaseName(value: String)

  sealed trait FileType extends CommandOp
  final case class FileDescriptor(value: Int) extends FileType


  final case class FolderPath(root: Char, folders: Vector[String]) extends FileType {
    def / (fileName: FileName) = FilePath(root, folders, fileName)
    def lastFolderName = folders.last
    def parentFolderName = folders.dropRight(1).last
    def parentFolderPath = copy(folders = folders.dropRight(1))
  }

  sealed trait FileHandle extends FileType

  final case class FilePath(
      root: Char,
      folderPath: Vector[String],
      fileName: FileName
  ) extends FileHandle
  
  final case class FileName(baseName: BaseName, extension: Vector[String])
      extends FileHandle

  final case class RelPath(folderPath: Vector[String], fileName: FileName)
      extends FileHandle {
        
      }
  
 final case class RelFolderPath(folders: Vector[String])
      extends FileHandle{
        def / (fileName: FileName) = RelPath(folders, fileName)
        def lastFolderName = folders.last
        def parentFolderName = folders.dropRight(1).last
        def parentFolderPath = copy(folders = folders.dropRight(1))
      }

  final case object `/dev/stdin` extends FileType
  final case object `/dev/stdout` extends FileType
  final case object `/dev/stderr` extends FileType
  final case class `/dev/fd`(fileDescriptor: FileDescriptor) extends FileType
  final case class `/dev/tcp`(host: Host, port: Port) extends FileType
  final case class `/dev/udp`(host: Host, port: Port) extends FileType
  final case object `/dev/null` extends FileType
  final case object `/dev/random` extends FileType

}
