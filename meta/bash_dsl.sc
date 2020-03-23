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

 def commands = (readDat("coreutils.dat"){
        case "false" => ""
        case "true" => ""
        case s: String => s
    }.filter(_.nonEmpty) ++ 
      readDat("basic_ops.dat"){ case s => s } ++ 
      readDat("all.dat"){case s => s}.filter(_.nonEmpty))
      .toSet.toList.sorted

def bashDsl = s"""
package bash4s

import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}

package object bash4s {
  
  implicit def cmdAliasConverter: BashCommandAdapter => SimpleCommand = _.toCmd

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

  def `{`(op: CommandOp) =
    CommandListBuilder(Vector(OpenGroupInContext(), op))

  def `(`(op: CommandOp) =
    CommandListBuilder(Vector(OpenSubShellEnv(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  def < (op: CommandOp) = ScriptBuilder(Vector(StdIn(), op))
  
  object For {
    def apply(indexVariable: CommandOp) =
      CFor(Vector(indexVariable))
  }

  def cat(hereStr: HereString) = 
    SimpleCommand("cat", hereStr)

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)

   // True if file exists
  def a(op: CommandOp) = CIfIsFile(op)


  // True if file exists and is a block special file
  def b(op: CommandOp) = CIsBlock(op)


  // True if file exists and is a character special file
  def c(op: CommandOp) = CIsCharacter(op)


  // True if file exists and is a directory
  def d(op: CommandOp) = CIsDirectory(op)


  // True if file exists
  def e(op: CommandOp) = CIsFile(op)


  // True if file exists and is a regular file
  def f(op: CommandOp) = CGroupIdBitSet(op)


  // True if file exists and its set-group-id bit is set
  def g(op: CommandOp) = CIsSymbolLink(op)


  // True if file exists and is a symbolic link
  def h(op: CommandOp) = CStickyBitSet(op)


  // True if file exists and its "sticky" bit is set
  def k(op: CommandOp) = CIsNamedPipe(op)


  // True if file exists and is a named pipe (FIFO)
  def p(op: CommandOp) = CIsReadAble(op)


  // True if file exists and is readable
  def r(op: CommandOp) = CIsGreaterThanZero(op)


  // True if file exists and has a size greater than zero
  def s(op: CommandOp) = CFileDescriptorIsOpenAndReferTerminal(op)


  // True if file descriptor fd is open and refers to a terminal
  def t(op: CommandOp) = CUserIdBitSet(op)


  // True if file exists and its set-user-id bit is set
  def u(op: CommandOp) = CIsWritable(op)


  // True if file exists and is writable
  def w(op: CommandOp) = CIsExecutable(op)


  // True if file exists and is executable
  def x(op: CommandOp) = CIsOwnedByEffectiveGroupId(op)


  // True if file exists and is owned by the effective group id
  def G(op: CommandOp) = CIsSymbolicLink(op)


  // True if file exists and is a symbolic link
  def L(op: CommandOp) = CIsModifiedSinceLastRead(op)


  // True if file exists and has been modified since it was last read
  def N(op: CommandOp) = CIsOwnedByEffectiveUserId(op)


  // True if file exists and is owned by the effective user id
  def O(op: CommandOp) = CIsSocket(op)


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
