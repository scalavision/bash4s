package bash

import magnolia._
import domain._

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

  implicit val closeFileDescriptorSerializer
      : ScriptSerializer[CloseFileDescriptor] =
    pure[CloseFileDescriptor] { cfd => s"${cfd.fileDescriptor}>&-" }

  implicit val mergeFileDescriptorsToSingleStreamSerializer
      : ScriptSerializer[MergeFileDescriptorsToSingleStream] =
    pure[MergeFileDescriptorsToSingleStream] { mfds =>
      s"${mfds.descriptor1}>&${mfds.descriptor2}"
    }

  implicit val refVariable: ScriptSerializer[RefVariable] = pure[RefVariable] {
    s => s"""$$${s.name}"""
  }

  implicit def bashVariable(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[BashVariable] = pure[BashVariable] {
    case BashVariable(name, variableValue) =>
      s"""$name=${variableValue match {
        case BString(value)     => s""""${value}""""
        case BSubCommand(value) => s"${value.map(enc.apply).mkString(" ")}"
        case BEmpty()           => "''"
      }}"""
  }

  implicit def simpleCommand(
      implicit enc: ScriptSerializer[CmdArgCtx]
  ): ScriptSerializer[SimpleCommand] = pure[SimpleCommand] { sc =>
    s"""${sc.name} ${sc.args match {
      case CmdArgs(args) => args.mkString(" ")
      case c: CmdArgCtx  => enc.apply(c)
    }}"""
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

  implicit val semiSerializer: ScriptSerializer[Semi] =
    pure[Semi] { _ => ";" }
  implicit val newLineSerializer: ScriptSerializer[NewLine] =
    pure[NewLine] { _ => "\n" }
  implicit val amperSerializer: ScriptSerializer[Amper] =
    pure[Amper] { _ => "&" }
  implicit val andSerializer: ScriptSerializer[And] =
    pure[And] { _ => "&&" }
  implicit val orSerializer: ScriptSerializer[Or] =
    pure[Or] { _ => "||" }
  implicit val pipeStdOutSerializer: ScriptSerializer[PipeStdOut] =
    pure[PipeStdOut] { _ => "|" }
  implicit val pipeStdOutWithErrSerializer
      : ScriptSerializer[PipeStdOutWithErr] =
    pure[PipeStdOutWithErr] { _ => "|&" }
  implicit val timedPipelineSerializer: ScriptSerializer[TimedPipeline] =
    pure[TimedPipeline] { _ => "time" }
  implicit val negatePipelineExitStatusSerializer
      : ScriptSerializer[NegatePipelineExitStatus] =
    pure[NegatePipelineExitStatus] { _ => "!" }
  implicit val stdOutSerializer: ScriptSerializer[StdOut] =
    pure[StdOut] { _ => ">" }
  implicit val stdErrSerializer: ScriptSerializer[StdErr] =
    pure[StdErr] { _ => "2>" }
  implicit val appendStdOutSerializer: ScriptSerializer[AppendStdOut] =
    pure[AppendStdOut] { _ => ">>" }
  implicit val stdOutWithStdErrSerializer: ScriptSerializer[StdOutWithStdErr] =
    pure[StdOutWithStdErr] { _ => "&>" }
  implicit val appendStdOutWithStdErrSerializer
      : ScriptSerializer[AppendStdOutWithStdErr] =
    pure[AppendStdOutWithStdErr] { _ => "&>>" }
  implicit val redirectStdOutWithStdErrSerializer
      : ScriptSerializer[RedirectStdOutWithStdErr] =
    pure[RedirectStdOutWithStdErr] { _ => "2>&1" }
  implicit val closeStdOutSerializer: ScriptSerializer[CloseStdOut] =
    pure[CloseStdOut] { _ => "<&-" }
  implicit val closeStdInSerializer: ScriptSerializer[CloseStdIn] =
    pure[CloseStdIn] { _ => ">&-" }
  implicit val subCommandStartSerializer: ScriptSerializer[SubCommandStart] =
    pure[SubCommandStart] { _ => "$(" }
  implicit val subCommandEndSerializer: ScriptSerializer[SubCommandEnd] =
    pure[SubCommandEnd] { _ => ")" }
  implicit val procCommandStartSerializer: ScriptSerializer[ProcCommandStart] =
    pure[ProcCommandStart] { _ => "<(" }
  implicit val procCommandEndSerializer: ScriptSerializer[ProcCommandEnd] =
    pure[ProcCommandEnd] { _ => ")" }

}
