import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.templates

val tmpl = templates

val pipeSymbols = "| |& time !".list
val pipeNames = "PipeStdOut PipeStdOutWithErr TimedPipeline NegatePipelineExitStatus".list
val PipeOp = name
val CommandOp = name
val CommandArg = name
val CmdArgCtx = name
val CmdArgs = name
val CommandList = name
val SimpleCommand = name
val Loop = name
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
val commandListSymbols = "o `;` & && || `\\n`".list
val commandListNames = "Semi Amper And Or NewLine".list
val loopSymbols = "Until For While In Do Done".list
val loopNames = "LUntil LWhile LFor LDo LDone LIn".list

val conditionalSymbols = "If Then Else Fi".list
val conditionalNames = "CIf CThen CElse CFi".list

val commandSubstitutionNames = "SubCommandStart SubCommandEnd".list
val processSubstitutionNames = "ProcCommandStart ProcCommandEnd".list
val helpers = "END TRUE FALSE".list

val redirectionSymbols = 
  "> `2>` >> &> &>> `2>&1` <&- >&-".list

val redirectionNames = 
  "StdOut StdErr AppendStdOut StdOutWithStdErr AppendStdOutWithStdErr RedirectStdOutWithStdErr CloseStdOut CloseStdIn".list

val cmdListFns: List[(String, String)] = (commandListSymbols).zip("NewLine" +: commandListNames)
val pipeFns: List[(String, String)] = pipeSymbols.zip(pipeNames)
val redirectionFns: List[(String, String)] = redirectionSymbols.zip(redirectionNames)

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

    ${((cmdListFns ++ pipeFns ++ redirectionFns).map(m => tmpl.toOpDef(m))).mkString("\n")}
    def < (file: FileTypeOp) = self.copy( acc = acc :+ file)

    def < (p: ScriptBuilder) = 
      self.copy( acc = (acc :+ ProcCommandStart()) ++ (p.acc.foldLeft(Vector.empty[CommandOp]){(acc1, op1) => acc1 ++ p.decomposeOnion(op1)} :+ ProcCommandEnd()))

    // This should have been $$, but it seems there is some infix presedence that
    // destroys the ordering of commands. Therefor we try to use ^ instead ..
    def ^(p: ScriptBuilder) =
      self.copy(acc =
        (acc :+ SubCommandStart()) ++ (p.acc
          .foldLeft(Vector.empty[CommandOp]) { (acc1, op1) =>
            acc1 ++ p.decomposeOnion(op1)
          } :+ SubCommandEnd()))
      
    def >&-(fileDescriptor: Int) = self.copy( acc = acc :+ CloseFileDescriptor(fileDescriptor))
    def >&(from: Int, to: Int) = self.copy(acc = acc :+ MergeFileDescriptorsToSingleStream(from, to))
  }
"""

val domain = 
  s"""package bash

      object domain {

      sealed trait ${CommandOp}

      sealed trait ${CommandArg} extends ${CommandOp}
      final case class ${CmdArgCtx}(args: Vector[Any], strCtx: StringContext) extends ${CommandArg}
      final case class ${CmdArgs}(args: Vector[String]) extends ${CommandArg}
      final case class ${SimpleCommand}(name: String, args: ${CommandArg}) extends ${CommandOp}
      
      sealed trait ${VariableValue} extends ${CommandOp}
      final case class BString(value: String) extends ${VariableValue}
      final case class BSubCommand(value: Vector[CommandOp]) extends ${VariableValue}
      final case class BEmpty() extends ${VariableValue}
     
      ${tmpl.toAdtValue(SheBang, shebangNames)}

      final case class ${BashVariable}(name: String, value: VariableValue) extends ${CommandOp} {
        def `=` (text: String) = this.copy(value = BString(text))
        def `=` (op: ScriptBuilder) = {
           val cmdOps = op.acc.foldLeft(Vector.empty[CommandOp]){(acc, op1) =>
           acc ++ op.decomposeOnion(op1)
         }
         this.copy(value = BSubCommand(cmdOps))
        }
      }
      
      final case class ${FileTypeOp}(path: String) extends ${CommandOp}
      
      ${helpers.map(c => tmpl.toCmdOp(CommandOp)(c)).mkString("\n")} 
      ${tmpl.toAdt(PipeOp, pipeNames)}
      ${tmpl.toAdt(CommandList, commandListNames)}
      ${tmpl.toAdt(Loop, loopNames)}
      ${tmpl.toAdt(Conditional, conditionalNames)}
      ${tmpl.toAdt(CommandSubstitution, commandSubstitutionNames)}
      ${tmpl.toAdt(ProcessSubstitution, processSubstitutionNames)}
      ${tmpl.toAdt(Redirections, redirectionNames)}
      final case class CloseFileDescriptor(fileDescriptor: Int) extends Redirections
      final case class MergeFileDescriptorsToSingleStream(from: Int, to: Int) extends Redirections
      ${commandBuilder}
}
"""

val bash =
  s"""package bash
  package object bash {

  } 
 """