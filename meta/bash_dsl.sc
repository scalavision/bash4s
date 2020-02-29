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

def readDat(file: String)(transformer: String => String): List[String] = 
  os.read(os.pwd / "meta" / "commands" / file).lines.toList.map(_.trim()).map {
    case s if s.contains('-') => s.map {
      case '-' => '_'
      case c: Char => c
    }
    case s => s
  }
  .filter(_.nonEmpty).map {transformer}.filter(_.nonEmpty).toSet.toList

 def commands = readDat("coreutils.dat"){
        case "false" => ""
        case "true" => ""
        case s: String => s
    }.filter(_.nonEmpty) ++ readDat("basic_ops.dat"){ case s => s }

def implicitCommand(name: String) =
 s"""
     def $name(args: Any*) = 
       ScriptBuilder(Vector(SimpleCommand("$name", CmdArgCtx(args.toVector, s))))
 """

def commandTemplate = s"""
  implicit class CmdSyntax(s: StringContext)  {
    ${commands.map(implicitCommand).mkString("\n")}
  }
"""

def bashDsl = s"""package bash
import domain._

package object bash {

  def Var(implicit name: sourcecode.Name) = 
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  ${toDef(cmd.helpers)}

  def $$(op: CommandOp) = 
    ScriptBuilder(Vector(SubCommandStart(), op, SubCommandEnd()))

  def time(op: CommandOp) = 
    ScriptBuilder(Vector(TimedPipeline(), op))

  ${commandTemplate}

}""".stripMargin