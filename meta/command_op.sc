import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.templates

val tmpl = templates

val pipeSymbols = "| |& time !".list
val pipeNames = "PipeStdOut PipeStdOutWithErr TimedPipeline NegatePipelineExitStatus".list
val PipeOp = name
val CommandOp = name
val DebugString = name
val CommandArg = name
val CmdArgCtx = name
val CmdArgs = name
val CommandList = name
val SimpleCommand = name
val Loop = name
val ConditionalExpr = name
val Conditional = name
val CommandSubstitution = name
val SubCommand = name
val ProcessSubstitution = name
val BashVariable = name
val VariableValue = name
val ScriptBuilder = name
val FileTypeOp = name
val SheBang = name
val Redirections = name

// ; & and \n are also used as terminators for a command list
// not sure if this is something we want to model though ..
val commandListSymbols = "`;` o & && || `\\n`".list

val commandListWithLineTerminator = "&".list
val pipelineLineTerminator = "!".list

val commandListNames = "Semi Amper And Or NewLine".list

val loopSymbols = "Until For While".list
val loopCtrlSymbols = "In Done".list

val conditionalExprSymbols = "Do Then Else `[[`".list
val conditionalExprNames = conditionalExprSymbols.dropRight(1).map("C" + _) :+ "OpenSquareBracket"

val conditionalSymbols = "If Until Elif Fi True False `]]`".list
val conditionalNames = "CIf CUntil CElif CFi CTrue CFalse CloseSquareBracket".list

val commandSubstitutionNames = "SubCommandStart SubCommandEnd".list
val processSubstitutionNames = "ProcCommandStart ProcCommandEnd".list
val helpers = "END".list

val redirectionSymbols = 
  "> `2>` >> &> &>> `2>&1` <&- >&-".list
val redirectionNames = 
  "StdOut StdErr AppendStdOut StdOutWithStdErr AppendStdOutWithStdErr RedirectStdOutWithStdErr CloseStdOut CloseStdIn".list

// Some rearrangements going on:
val cmdListFns: List[(String, String)] = (commandListSymbols).zip(commandListNames.head +: "NewLine" +: commandListNames.tail)
val pipeFns: List[(String, String)] = pipeSymbols.zip(pipeNames)
val redirectionFns: List[(String, String)] = redirectionSymbols.zip(redirectionNames)

val loopFns: List[(String, String)] = loopSymbols.zip(loopSymbols.map("L" + _))
val loopCtrlFns: List[(String, String)] = loopCtrlSymbols.zip(loopCtrlSymbols.map("L" + _))
val conditionalFns: List[(String, String)] = conditionalSymbols.zip(conditionalNames)
val conditionalExprFns: List[(String, String)] = conditionalExprSymbols.zip(conditionalExprNames)

val shebangNames = "Bash Sh Zsh Scala Perl Python".list

val commandBuilder = s"""
  case class ${ScriptBuilder}(acc: Vector[CommandOp]) extends ${CommandOp} { self =>

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) =>
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    def `;\\n`(op: CommandOp) = self.copy(acc = (acc :+ Semi() :+ NewLine()) ++ decomposeOnion(op))
    ${((cmdListFns ++ pipeFns ++ redirectionFns).map(m => tmpl.toOpDef(m))).mkString("\n")}
    ${loopCtrlFns.filterNot(s => s == ("Done", "LDone")).map(tmpl.toOpDef).mkString("\n")}
    ${(conditionalFns.map(tmpl.toOpDef).mkString("\n"))}
    def Else(op: CommandOp) =
      self.copy(acc = (acc :+ CElse(op)) )
    def Do(op: CommandOp) =
      self.copy(acc = (acc :+ CDo(op)) )
    def Done(op: CommandOp) = 
      self.copy(acc = (acc :+ LDone()) ++ decomposeOnion(op))
    def `[[`(op: CommandOp) =
      self.copy(acc = acc :+ OpenSquareBracket(op))
    def Fi =
      self.copy(acc = acc :+ CFi())
    ${tmpl.toOpDefEmpty(("Done", "LDone"))}
    ${tmpl.toOpDefEmpty(("o", "NewLine"))}
    ${(commandListWithLineTerminator ++ pipelineLineTerminator).zip(List("Amper", "NegatePipelineExitStatus")).map(m => tmpl.toOpDefWithNewLineTerminator(m)).mkString("\n") }
    def < (file: FileTypeOp) = self.copy( acc = acc :+ file)

    def < (p: ScriptBuilder) = 
      self.copy( acc = (acc :+ ProcCommandStart()) ++ (p.acc.foldLeft(Vector.empty[CommandOp]){(acc1, op1) => acc1 ++ p.decomposeOnion(op1)} :+ ProcCommandEnd()))

    // This should have been $$, but it seems there is some infix presedence that
    // destroys the ordering of commands. Therefor we try to use % instead ..
    def %(p: ScriptBuilder) =
      self.copy(acc =
        (acc :+ SubCommandStart()) ++ (p.acc
          .foldLeft(Vector.empty[CommandOp]) { (acc1, op1) =>
            acc1 ++ p.decomposeOnion(op1)
          } :+ SubCommandEnd()))
      
    def >&-(fileDescriptor: Int) = self.copy( acc = acc :+ CloseFileDescriptor(fileDescriptor))
    def >&(desc1: Int, desc2: Int) = self.copy(acc = acc :+ MergeFileDescriptorsToSingleStream(desc1, desc2))
  }
"""

val domain = 
  s"""package bash

      object domain {

      sealed trait ${CommandOp}
      
      final case class ${DebugString}(value: String) extends ${CommandOp}
      final case class ConditionalExpression(s: String, op: CommandOp) extends CommandOp

      sealed trait ${CommandArg} extends ${CommandOp}
      final case class ${CmdArgCtx}(args: Vector[Any], strCtx: StringContext) extends ${CommandArg}
      final case class ${CmdArgs}(args: Vector[String]) extends ${CommandArg} { self =>
        def :+ (arg: String) = copy(args = self.args :+ arg)
      }
      final case class ${SimpleCommand}(name: String, args: ${CommandArg}) extends ${CommandOp}
      
      sealed trait ${VariableValue} extends ${CommandOp}
      final case class BString(value: String) extends ${VariableValue}
      final case class BSubCommand(value: Vector[CommandOp]) extends ${VariableValue}
      final case class BEmpty() extends ${VariableValue}
  
      final case class RefVariable(name: String, value: VariableValue) extends CommandOp
     
      ${tmpl.toAdtValue(SheBang, shebangNames)}

      final case class ${BashVariable}(name: String, value: VariableValue) extends ${CommandOp} {
        def `=` (text: String) = this.copy(value = BString(text))
        def `=` (op: ScriptBuilder) = {
           val cmdOps = op.acc.foldLeft(Vector.empty[CommandOp]){(acc, op1) =>
           acc ++ op.decomposeOnion(op1)
         }
         this.copy(value = BSubCommand(cmdOps))
        }
        def $$ = RefVariable(name, value)
      }
     
      final case class Host(value: String) extends AnyVal
      final case class Port(value: Int) extends AnyVal
      final case class FileDescriptor(value: Int) extends AnyVal
      final case class FileExtension(extension: Vector[String])
      final case class FolderPath(folders: Vector[String])
      final case class BaseName(value: String) extends AnyVal

      sealed trait ${FileTypeOp} extends ${CommandOp}
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
      
      ${helpers.map(c => tmpl.toCmdOp(CommandOp)(c)).mkString("\n")} 
      ${tmpl.toAdt(PipeOp, pipeNames)}
      ${tmpl.toAdt(CommandList, commandListNames)}
      ${tmpl.toAdtOpValue(Loop, loopSymbols.map("L" + _))}
      ${tmpl.toAdtOpValue(ConditionalExpr, conditionalExprNames)}
      ${tmpl.toAdt("LoopCtrl", loopCtrlSymbols.map("L" + _))}
      ${tmpl.toAdt(Conditional, conditionalNames)}
      ${tmpl.toAdt(CommandSubstitution, commandSubstitutionNames)}
      ${tmpl.toAdt(ProcessSubstitution, processSubstitutionNames)}
      ${tmpl.toAdt(Redirections, redirectionNames)}
      final case class CloseFileDescriptor(fileDescriptor: Int) extends Redirections
      final case class MergeFileDescriptorsToSingleStream(descriptor1: Int, descriptor2: Int) extends Redirections
      ${commandBuilder}
}
"""

val bash =
  s"""package bash
  package object bash {

  } 
 """