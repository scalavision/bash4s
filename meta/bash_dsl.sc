import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.command_op
import $file.templates
import scala.util.Sorting

val cmd = command_op
val tmpl = templates

final case class CmdMeta(name: String, description: String) 
object CmdMetaOrdering extends Ordering[CmdMeta] {
    override def compare(x: CmdMeta, y: CmdMeta): Int = {
      x.name.toString.compareTo(y.name.toString)
  }
}

def toDef(helpers: List[CmdMeta]): String = {

  def t(cmd: CmdMeta) = 
    s"""
    ${if(cmd.description.nonEmpty) s"//${cmd.description}" else ""}
    def ${cmd.name.head + cmd.name.tail.map(_.toLower)}(args: String *) = clitools.${cmd.name.capFirst}Wrapper(CmdArgs(args.toVector))
    def ${cmd.name.head + cmd.name.tail.map(_.toLower)} = clitools.${cmd.name.capFirst}Wrapper()
    """

  helpers.map(t).mkString("\n")

}

def toLoop(prefix: Char)(loop: List[String]): String = {
  def t(name: String) =
    s"""def ${name}(op: CmdMetaOp) = ${prefix}${name}(op)"""
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

val fileUtils =  os.read(os.pwd / "meta" / "commands" / "file_utils.dat")
  .lines.toList.filterNot(_.isEmpty)
  .sliding(size=2, step=2).toList.map{ l =>
    CmdMeta(l.head, l.last)
  }

val toMany = readDat("all.dat"){
        case s if s.startsWith("7") || 
        s.startsWith("gimp") ||
        s.contains("perl5.30.0") ||
        s.startsWith("alsa") ||
        s.startsWith("zsh") ||
        s.contains('.') ||
        s.contains('+') ||
        s == "false" ||
        s == "true"  ||
        s == "import" ||
        s == "type"
        => ""
        case s => s
      }.filter(_.nonEmpty)

 def commandsUnsorted = ((readDat("coreutils.dat"){
        case "false" => ""
        case "true" => ""
        case s: String => s
    }.filter(_.nonEmpty) ++ 
      readDat("basic_ops.dat"){ case s => s } ++ 
      readDat("builtins.dat"){ 
        case s if s == "type" => ""
        case s => s
      }).map{ s =>
        CmdMeta(s, "")
      } ++ fileUtils)
      .filter(_.name != "cat")
      .toSet.toList

def commands: List[CmdMeta] =  {
  var sorted = commandsUnsorted.toArray
  Sorting.quickSort[CmdMeta](sorted)(CmdMetaOrdering)
  sorted.toList
}

def bashDsl = s"""
package bash4s

import domain._

trait BashCmdMetaAdapter {
  def toCmd: SimpleCmdMeta
}

package object bash4s {
  
  implicit def cmdAliasConverter: BashCmdMetaAdapter => SimpleCmdMeta = _.toCmd

  object Until {
    def `[[`(op: CmdMetaOp) = CUntil(Vector(OpenDoubleSquareBracket(), op))
  }

  object While {
    def `[[`(op: CmdMetaOp) = CWhile(Vector(OpenDoubleSquareBracket(), op))
  }

  object If {
    def `[[`(op: CmdMetaOp) = CIf(Vector(OpenDoubleSquareBracket(), op))
  }

  def `[[`(op: CmdMetaOp) =
    CmdMetaListBuilder(Vector(OpenDoubleSquareBracket(), op))

  def `{`(op: CmdMetaOp) =
    CmdMetaListBuilder(Vector(OpenGroupInContext(), op))

  def `(`(op: CmdMetaOp) =
    CmdMetaListBuilder(Vector(OpenSubShellEnv(), op))

  def &&(op: CmdMetaOp) = Vector(And(), op)

  def Do(op: CmdMetaOp) = CDo(op)

  def Then(op: CmdMetaOp) = CThen(op)

  def < (op: CmdMetaOp) = ScriptBuilder(Vector(StdIn(), op))
  
  object exit {
    def apply(code: Int) = SimpleCmdMeta("exit", CmdArgs(Vector(code.toString())))
  }
  
  object For {
    def apply(indexVariable: CmdMetaOp) =
      CFor(Vector(indexVariable))
  }

  def cat(hereStr: HereString) = 
    SimpleCmdMeta("cat", hereStr)

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)

   // True if file exists
  def a(op: CmdMetaOp) = CIfIsFile(op)


  // True if file exists and is a block special file
  def b(op: CmdMetaOp) = CIsBlock(op)


  // True if file exists and is a character special file
  def c(op: CmdMetaOp) = CIsCharacter(op)


  // True if file exists and is a directory
  def d(op: CmdMetaOp) = CIsDirectory(op)


  // True if file exists
  def e(op: CmdMetaOp) = CIsFile(op)


  // True if file exists and is a regular file
  def f(op: CmdMetaOp) = CGroupIdBitSet(op)


  // True if file exists and its set-group-id bit is set
  def g(op: CmdMetaOp) = CIsSymbolLink(op)


  // True if file exists and is a symbolic link
  def h(op: CmdMetaOp) = CStickyBitSet(op)


  // True if file exists and its "sticky" bit is set
  def k(op: CmdMetaOp) = CIsNamedPipe(op)


  // True if file exists and is a named pipe (FIFO)
  def p(op: CmdMetaOp) = CIsReadAble(op)


  // True if file exists and is readable
  def r(op: CmdMetaOp) = CIsGreaterThanZero(op)


  // True if file exists and has a size greater than zero
  def s(op: CmdMetaOp) = CFileDescriptorIsOpenAndReferTerminal(op)


  // True if file descriptor fd is open and refers to a terminal
  def t(op: CmdMetaOp) = CUserIdBitSet(op)


  // True if file exists and its set-user-id bit is set
  def u(op: CmdMetaOp) = CIsWritable(op)


  // True if file exists and is writable
  def w(op: CmdMetaOp) = CIsExecutable(op)


  // True if file exists and is executable
  def x(op: CmdMetaOp) = CIsOwnedByEffectiveGroupId(op)


  // True if file exists and is owned by the effective group id
  def G(op: CmdMetaOp) = CIsSymbolicLink(op)


  // True if file exists and is a symbolic link
  def L(op: CmdMetaOp) = CIsModifiedSinceLastRead(op)


  // True if file exists and has been modified since it was last read
  def N(op: CmdMetaOp) = CIsOwnedByEffectiveUserId(op)


  // True if file exists and is owned by the effective user id
  def O(op: CmdMetaOp) = CIsSocket(op)


  ${toDef(commands)}
  ${tmpl.cli(commands.map(c => tmpl.Command(c.name, c.description)))}
  
}
"""

/*
def bashDslOld = s"""package bash
import domain._

trait BashCmdMetaAdapter {
  def toCmd: SimpleCmdMeta
}

object dsl {
  implicit def bashCmdMetaAdapterToSimpleCmdMeta: BashCmdMetaAdapter => ScriptBuilder = 
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
  def `[[`(op: CmdMetaOp) = ScriptBuilder(Vector(OpenSquareBracket(op)))
  def If = ScriptBuilder(Vector(CIf()))
  def Until = ScriptBuilder(Vector(CUntil()))
  def Elif = ScriptBuilder(Vector(CElif()))
//  def Done = ScriptBuilder(Vector(LDone()))
  def True = CTrue()
  def False = CFalse()

  case object - {
    def a(op: CmdMetaOp) = ConditionalExpression("a", op)
  }

  def $$(op: CmdMetaOp) = 
    ScriptBuilder(Vector(SubCmdMetaStart(), op, SubCmdMetaEnd()))

  def * = RegexFileSearchApi()

  def time(op: CmdMetaOp) = 
    ScriptBuilder(Vector(TimedPipeline(), op))

  ${commandTemplate}

}""".stripMargin

def commandToolClass(name: String) = 
    s"""
    package bash.clitools

    import bash.domain._
    import bash.BashCmdMetaAdapter

    case class ${name.capFirst}Wrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCmdMetaAdapter { self =>
      def toCmd = SimpleCmdMeta("$name", args)
      def help = copy(args = self.args :+ "--help")
    }
    """
*/

def createCommandToolClasses(path: os.Path): Unit =
  commands.map(c => tmpl.cliAlias(tmpl.Command(c.name, c.description))).zip(commands).foreach {
    case (src, cmd)  =>
      val file = path / s"${cmd.name.capFirst}.scala"
      if(!os.exists(file)) {
        os.write(file, src)
      }
  }

  def deleteToMany(path: os.Path): Unit = 
    toMany.foreach {
      case name =>
        val file = path / s"${name.capFirst}.scala"
        os.remove(file)
    }
