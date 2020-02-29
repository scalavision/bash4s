import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.command_op
import $file.templates

val cmd = command_op
val tmpl = templates

def toDef(helpers: List[String]): String = {

  def t(name: String) = 
    s"""def ${name.head + name.tail.map(_.toLower)} = $name()"""

  helpers.map(t).mkString("\n")

}

val bashDsl = s"""package bash
import domain._

package object bash {

  def Var(implicit name: sourcecode.Name) = 
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  ${toDef(cmd.helpers)}

  def $$(op: CommandOp) = 
    ScriptBuilder[CommandOp](Vector(SubCommandStart(), op, SubCommandEnd()))

  def time(op: CommandOp) = 
    ScriptBuilder[CommandOp](Vector(TimedPipeline(), op))

  def ls = ScriptBuilder(Vector(SimpleCommand("ls", CmdArgs(Vector.empty[String]))))

  implicit class CmdSyntax(s: StringContext)  {
     def ls(args: Any*) = 
       ScriptBuilder(Vector(SimpleCommand("ls", CmdArgCtx(args.toVector, s))))
  }

}""".stripMargin