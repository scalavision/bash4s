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
val ProcessSubstitution = name
val BashVariable = name
val VariableValue = name
val ScriptBuilder = name
val FileTypeOp = name

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

val cmdListFns: List[(String, String)] = (commandListSymbols).zip("NewLine" +: commandListNames)
val pipeFns: List[(String, String)] = pipeSymbols.zip(pipeNames)

val commandBuilder = s"""
  case class ${ScriptBuilder}[A <: ${CommandOp}](acc: Vector[A]) extends ${CommandOp} { self =>

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) => 
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    ${((cmdListFns ++ pipeFns).map(m => tmpl.toCmdOp(m))).mkString("\n")}
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
      final case class BSubCommand(value: CommandOp) extends ${VariableValue}
      final case class BEmpty() extends ${VariableValue}

      final case class END() extends ${CommandOp}

      final case class ${BashVariable}(name: String, value: VariableValue) extends ${CommandOp} {
        def `=` (text: String) = this.copy(value = BString(text))
        def =& (op: CommandOp) = this.copy(value = BSubCommand(op))
      }
      
      final case class ${FileTypeOp}(path: String) extends ${CommandOp}

      ${tmpl.toAdt(PipeOp, pipeNames)}
      ${tmpl.toAdt(CommandList, commandListNames)}
      ${tmpl.toAdt(Loop, loopNames)}
      ${tmpl.toAdt(Conditional, conditionalNames)}
      ${tmpl.toAdt(CommandSubstitution, commandSubstitutionNames)}
      ${tmpl.toAdt(ProcessSubstitution, processSubstitutionNames)}
      ${commandBuilder}
}
"""


val bash =
  s"""package bash
  package object bash {

  } 
 """