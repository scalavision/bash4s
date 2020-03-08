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

def toLoop(loop: List[String]): String = {
  def t(name: String) =
    s"""def ${name}(op: CommandOp) = L${name}(op)"""
  loop.map(t).mkString("\n")
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

def commandTool(name: String) = 
  s"""def $name = clitools.${name.capFirst}Wrapper()"""

def commandTemplate = s"""
  ${commands.map(commandTool).mkString("\n")}
  implicit class CmdSyntax(s: StringContext)  {
    ${commands.map(implicitCommand).mkString("\n")}
  }
"""

def bashDsl = s"""package bash
import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}

object dsl {
  implicit def bashCommandAdapterToSimpleCommand: BashCommandAdapter => ScriptBuilder = 
    cmd => ScriptBuilder(Vector(cmd.toCmd))
}

package object bash {

  def Var(implicit name: sourcecode.Name) = 
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  def `/dev/stdin`  = domain.`/dev/stdin`
  def `/dev/stdout`  = domain.`/dev/stdout`
  def `/dev/stderr`  = domain.`/dev/stderr`

  def `/dev/fd`(fileDescriptor: FileDescriptor)  = domain.`/dev/fd`(fileDescriptor)
  def `/dev/tcp`(host: Host, port: Port)  = domain.`/dev/tcp`(host, port)
  def `/dev/udp`(host: Host, port: Port)  = domain.`/dev/udp`(host, port)
  def `/dev/null`  = domain.`/dev/null`
  def `/dev/random` = domain.`/dev/random`

  ${toDef(cmd.helpers)}

  ${toLoop(cmd.loopFns.map(_._1))}

  def Done = LDone()

  def $$(op: CommandOp) = 
    ScriptBuilder(Vector(SubCommandStart(), op, SubCommandEnd()))

  def * = RegexFileSearchApi()
    
  def time(op: CommandOp) = 
    ScriptBuilder(Vector(TimedPipeline(), op))

  ${commandTemplate}

}""".stripMargin

def commandToolClass(name: String) = 
    s"""
    package bash.clitools

    import bash.domain._
    import bash.BashCommandAdapter

    case class ${name.capFirst}Wrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("$name", args)
      def help = copy(args = self.args :+ "--help")
    }
    """

def createCommandToolClasses(path: os.Path): Unit =
  commands.map(commandToolClass).zip(commands).foreach {
    case (src, name)  =>
      os.write.over(path / s"${name.capFirst}.scala", src)
  }
