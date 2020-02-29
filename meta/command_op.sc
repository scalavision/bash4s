import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.templates

val tmpl = templates

val pipeSymbols = "| |& time !".list
val pipeNames = "PipeStdOut PipeStdOutWithErr TimedPipeline NegatePipelineExitStatus".list
val PipeOp = name
val CommandOp = name
val CommandList = name
val Loop = name
val Conditional = name
val CommandSubstitution = name
val ProcessSubstitution = name

// ; & and \n are also used as terminators for a command list
// not sure if this is something we want to model though ..
val commandListSymbols = "`;` & && || `\n`".list
val commandListNames = "Semi Amper And Or NewLine".list
val loopSymbols = "Until For While In Do Done".list
val loopNames = "LUntil LWhile LFor LDo LDone LIn".list

val conditionalSymbols = "If Then Else Fi".list
val conditionalNames = "CIf CThen CElse CFi".list

val commandSubstitutionNames = "SubCommandStart SubCommandEnd".list
val processSubstitutionNames = "ProcCommandStart ProcCommandEnd".list

val domain = 
  s"""|
      object domain {

      sealed trait ${CommandOp}
      case class SimpleCommand(name: String, args: Vector[String]) extends ${CommandOp}

      ${tmpl.toAdt(PipeOp, pipeNames)}
      ${tmpl.toAdt(CommandList, commandListNames)}
      ${tmpl.toAdt(Loop, loopNames)}
      ${tmpl.toAdt(Conditional, conditionalNames)}
      ${tmpl.toAdt(CommandSubstitution, commandSubstitutionNames)}
      ${tmpl.toAdt(ProcessSubstitution, processSubstitutionNames)}
}
|""".stripMargin