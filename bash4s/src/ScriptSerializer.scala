package bash4s

import magnolia._
import dsl._

import scala.language.experimental.macros
import scala.annotation.implicitNotFound

@implicitNotFound(
  """Cannot find an BiologySerializer for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologySerializer for other types that could be nested in ${T}.
  """
)
trait ScriptSerializer[T] {
  def apply(t: T): String
}
object ScriptSerializer {

  type Typeclass[T] = ScriptSerializer[T]

  def pure[A](func: A => String): ScriptSerializer[A] =
    new ScriptSerializer[A] {
      def apply(value: A): String = func(value)
    }

  implicit val stringSerializer: ScriptSerializer[String] = pure[String] {
    _.toString()
  }
  implicit val intSerializer: ScriptSerializer[Int] = pure[Int] { _.toString() }
  implicit val charSerializer: ScriptSerializer[Char] = pure[Char] {
    _.toString()
  }

  implicit def cmdArgCtx(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CmdArgCtx] = pure[CmdArgCtx] {
    case CmdArgCtx(args: Vector[Any], stringContext) =>
      val serializedArgs = args.map {
        case c: CommandOp => enc.apply(c)
        case other        => other
      }
      stringContext.s(serializedArgs: _*)
  }

  implicit def simpleCommand(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[SimpleCommand] = pure[SimpleCommand] { sc =>
    s"""${sc.name} ${sc.arg match {
      case CmdArgs(args) => args.mkString(" ")
      case c: CmdArgCtx  => enc.apply(c)
      case EmptyArg()    => "()"
    }} ${sc.cmds.map(enc.apply).mkString(" ")}"""
  }

  implicit def vectorSerializerAny(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[Vector[Any]] =
    pure[Vector[Any]] {
      _.map {
        case c: CommandOp => enc.apply(c)
        case a: Any       => a.toString()
      }.mkString(" ")
    }

  implicit def vectorSerializer[A](
      implicit enc: ScriptSerializer[A]
  ): ScriptSerializer[Vector[A]] =
    pure[Vector[A]] { _.map(enc.apply).mkString(" ") }

  def combine[T](
      caseClass: CaseClass[ScriptSerializer, T]
  ): ScriptSerializer[T] = new ScriptSerializer[T] {
    def apply(t: T) = {
      val paramString = caseClass.parameters.map { p =>
        p.typeclass.apply(p.dereference(t))
      }
      paramString.mkString("")
    }
  }

  def dispatch[T](
      sealedTrait: SealedTrait[ScriptSerializer, T]
  ): ScriptSerializer[T] = new ScriptSerializer[T] {
    def apply(t: T) = {
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }
    }
  }

  implicit def gen[T]: ScriptSerializer[T] = macro Magnolia.gen[T]

  implicit def dollarSerializer: ScriptSerializer[Dollar] = pure[Dollar] { _ =>
    "$"
  }

  implicit def subShellExpSerializer: ScriptSerializer[OpenSubShellExp] =
    pure[OpenSubShellExp] { _ => "$(" }

  implicit def openDoubleSquareBracketSerializer
      : ScriptSerializer[OpenDoubleSquareBracket] =
    pure[OpenDoubleSquareBracket] { _ => "[[" }
  implicit def openSubShellEnvSerializer: ScriptSerializer[OpenSubShellEnv] =
    pure[OpenSubShellEnv] { _ => "(" }
  implicit def openCommandListSerializer: ScriptSerializer[OpenCommandList] =
    pure[OpenCommandList] { _ => "{" }
  implicit def cDoSerializer: ScriptSerializer[CDo] = pure[CDo] { _ => "Do" }
  implicit def cThenSerializer: ScriptSerializer[CThen] = pure[CThen] { _ =>
    "Then"
  }
  implicit def cElseSerializer: ScriptSerializer[CElse] = pure[CElse] { _ =>
    "Else"
  }
  implicit def cElseIfSerializer: ScriptSerializer[CElseIf] = pure[CElseIf] {
    _ => "ElseIf"
  }
  implicit def cUntilSerializer: ScriptSerializer[CUntil] = pure[CUntil] { _ =>
    "Until"
  }
  implicit def cForSerializer: ScriptSerializer[CFor] = pure[CFor] { _ =>
    "For"
  }
  implicit def cWhileSerializer: ScriptSerializer[CWhile] = pure[CWhile] { _ =>
    "While"
  }
  implicit def pipeWithStdOutSerializer: ScriptSerializer[PipeWithStdOut] =
    pure[PipeWithStdOut] { _ => "|" }
  implicit def pipeWithErrorSerializer: ScriptSerializer[PipeWithError] =
    pure[PipeWithError] { _ => "|&" }
  implicit def orSerializer: ScriptSerializer[Or] = pure[Or] { _ => "||" }
  implicit def andSerializer: ScriptSerializer[And] = pure[And] { _ => "&&" }
  implicit def stdOutSerializer: ScriptSerializer[StdOut] = pure[StdOut] { _ =>
    ">"
  }
  implicit def stdErrSerializer: ScriptSerializer[StdErr] = pure[StdErr] { _ =>
    "2>"
  }
  implicit def appendStdOutSerializer: ScriptSerializer[AppendStdOut] =
    pure[AppendStdOut] { _ => ">>" }
  implicit def stdOutWithStdErrSerializer: ScriptSerializer[StdOutWithStdErr] =
    pure[StdOutWithStdErr] { _ => "&>" }
  implicit def appendStdOutWithSdErrSerializer
      : ScriptSerializer[AppendStdOutWithSdErr] = pure[AppendStdOutWithSdErr] {
    _ => "&>>"
  }
  implicit def stdInSerializer: ScriptSerializer[StdIn] = pure[StdIn] { _ =>
    "<"
  }
  implicit def cIfSerializer: ScriptSerializer[CIf] = pure[CIf] { _ => "If" }
  implicit def negateSerializer: ScriptSerializer[Negate] = pure[Negate] { _ =>
    "!"
  }
  implicit def amperSerializer: ScriptSerializer[Amper] = pure[Amper] { _ =>
    "&"
  }
  implicit def semiSerializer: ScriptSerializer[Semi] = pure[Semi] { _ => ";" }
  implicit def newLineSerializer: ScriptSerializer[NewLine] = pure[NewLine] {
    _ => "\n"
  }
  implicit def cDoneSerializer: ScriptSerializer[CDone] = pure[CDone] { _ =>
    "Done"
  }
  implicit def cFiSerializer: ScriptSerializer[CFi] = pure[CFi] { _ => "Fi" }
  implicit def redirectStdOutWithStdErrSerializer
      : ScriptSerializer[RedirectStdOutWithStdErr] =
    pure[RedirectStdOutWithStdErr] { _ => "2>&1" }
  implicit def closeStdInSerializer: ScriptSerializer[CloseStdIn] =
    pure[CloseStdIn] { _ => "<&-" }
  implicit def closeStdOutSerializer: ScriptSerializer[CloseStdOut] =
    pure[CloseStdOut] { _ => ">&-" }
  implicit def closeDoubleSquareBracketSerializer
      : ScriptSerializer[CloseDoubleSquareBracket] =
    pure[CloseDoubleSquareBracket] { _ => "]]" }
  implicit def closeSubShellEnvSerializer: ScriptSerializer[CloseSubShellEnv] =
    pure[CloseSubShellEnv] { _ => ")" }
  implicit def closeCommandListSerializer: ScriptSerializer[CloseCommandList] =
    pure[CloseCommandList] { _ => "}" }
  implicit def scriptLineSerializer: ScriptSerializer[ScriptLine] =
    pure[ScriptLine] { _ => "\n" }
}
