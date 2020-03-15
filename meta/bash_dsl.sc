import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.command_op
import $file.templates

val cmd = command_op
val tmpl = templates

def toDef(helpers: List[String]): String = {

  def t(name: String) = 
    s"""
    def ${name.head + name.tail.map(_.toLower)}(args: String *) = clitools.${name.capFirst}Wrapper(CmdArgs(args.toVector))
    def ${name.head + name.tail.map(_.toLower)} = clitools.${name.capFirst}Wrapper()
    """

  helpers.map(t).mkString("\n")

}

def toLoop(prefix: Char)(loop: List[String]): String = {
  def t(name: String) =
    s"""def ${name}(op: CommandOp) = ${prefix}${name}(op)"""
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

def bashDsl = s"""
package bash4s

import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}

package object bash4s {

  implicit def cmdAliasConverter: 
    BashCommandAdapter => SimpleCommand = _.toCmd
  
  object Until {
    def `[[`(op: CommandOp) = CUntil(Vector(OpenDoubleSquareBracket(), op))
  }

  object While {
    def `[[`(op: CommandOp) = CWhile(Vector(OpenDoubleSquareBracket(), op))
  }
  
  object If {
    def `[[`(op: CommandOp) = CIf(Vector(OpenDoubleSquareBracket(), op))
  }

  def `[[`(op: CommandOp) =
    CommandListBuilder(Vector(OpenDoubleSquareBracket(), op))

  def &&(op: CommandOp) = Vector(And(), op)
  
  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)
  
  ${toDef(commands.sorted)}
  ${tmpl.cli(commands.sorted)}
  
}
"""

/*
def bashDslOld = s"""package bash
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

  ${toLoop('L')(cmd.loopFns.map(_._1))}
  ${toLoop('C')(cmd.conditionalExprSymbols.filter(_ != """`[[`"""))}
  def `[[`(op: CommandOp) = ScriptBuilder(Vector(OpenSquareBracket(op)))
  def If = ScriptBuilder(Vector(CIf()))
  def Until = ScriptBuilder(Vector(CUntil()))
  def Elif = ScriptBuilder(Vector(CElif()))
//  def Done = ScriptBuilder(Vector(LDone()))
  def True = CTrue()
  def False = CFalse()

  case object - {
    def a(op: CommandOp) = ConditionalExpression("a", op)
  }

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
*/

def createCommandToolClasses(path: os.Path): Unit =
  commands.map(tmpl.cliAlias).zip(commands).foreach {
    case (src, name)  =>
      os.write.over(path / s"${name.capFirst}.scala", src)
  }
