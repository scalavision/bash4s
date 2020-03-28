import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.command_op
import $file.templates
import scala.util.Sorting

val cmd = command_op
val tmpl = templates

final case class CmdMeta(name: String, description: String) extends Ordered[CmdMeta] {
  import scala.math.Ordered.orderingToOrdered

  def compare(that: CmdMeta): Int = this.name compare that.name

}

/*
object CmdMetaOrdering extends Ordering[CmdMeta] {
    override def compare(x: CmdMeta, y: CmdMeta): Int = {
      x.name.toString.compareTo(y.name.toString)
  }
}*/

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

import scala.collection.IterableLike
import scala.collection.generic.CanBuildFrom

class RichCollection[A, Repr](xs: IterableLike[A, Repr]){
  def distinctBy[B, That](f: A => B)(implicit cbf: CanBuildFrom[Repr, A, That]) = {
    val builder = cbf(xs.repr)
    val i = xs.iterator
    var set = Set[B]()
    while (i.hasNext) {
      val o = i.next
      val b = f(o)
      if (!set(b)) {
        set += b
        builder += o
      }
    }
    builder.result
  }
}

implicit def toRich[A, Repr](xs: IterableLike[A, Repr]) = new RichCollection(xs)


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

 // CONVERT TO HASHMAP
 def commands = ( (readDat("coreutils.dat"){
        case "false" => ""
        case "true" => ""
        case s: String => s
    }.filter(_.nonEmpty) ++ 
        readDat("basic_ops.dat"){ case s => s } ++ 
        readDat("builtins.dat"){ 
          case "wait" => "Wait"
          case s if s == "false" ||
            s == "true"  ||
            s == "import" ||
            s == "type" => ""
          case s => s
        }).map { s =>
          CmdMeta(s, "")
        } ++ fileUtils
      
      ).map { m =>
        m.copy(name = m.name.trim())
      }.distinctBy(s => s.name).toList.sorted

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
  
  object exit {
    def apply(code: Int) = SimpleCommand("exit", CmdArgs(Vector(code.toString())))
  }
  
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

  ${toDef(commands)}
  ${tmpl.cli(commands.map(c => tmpl.Command(c.name, c.description)))}
  
}
"""

def createCommandToolClasses(path: os.Path): Unit =
  commands.filter(_.name != "cat").map(c => tmpl.cliAlias(tmpl.Command(c.name, c.description))).zip(commands.filter(_.name != "cat")).foreach {
    case (src, cmd)  =>
      val file = path / s"${cmd.name.capFirst}.scala"
      os.write.over(file, src)
  }

  def deleteToMany(path: os.Path): Unit = 
    toMany.foreach {
      case name =>
        val file = path / s"${name.capFirst}.scala"
        os.remove(file)
    }
