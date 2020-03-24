package bash4s

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
  implicit val booleanSerializer: ScriptSerializer[Boolean] = pure[Boolean] {b: Boolean =>  b.toString() }
  implicit val intSerializer: ScriptSerializer[Int] = pure[Int] { _.toString() }
  implicit val charSerializer: ScriptSerializer[Char] = pure[Char] {
    _.toString()
  }

  implicit def cmdArgCtx(
      implicit enc: ScriptSerializer[CommandOp],
      fEnc: ScriptSerializer[FileType]
  ): ScriptSerializer[CmdArgCtx] = pure[CmdArgCtx] {
    case CmdArgCtx(args: Vector[Any], stringContext) =>
      val serializedArgs = args.map {
        case f: FileType => fEnc.apply(f)
        case b: BashVariable => b.expansionSafe
        case h: HereString => enc.apply(h)
        case h: HereDoc => enc.apply(h)
        case c: CommandOp => enc.apply(c)
        case other        => other
      }
      stringContext.s(serializedArgs: _*)
  }

  implicit def simpleCommand(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[SimpleCommand] = pure[SimpleCommand] { sc =>

    val quoted = if(sc.name == "echo") true else false
    
    val args = sc.arg match {
      case CmdArgs(args) => args.mkString(" ")
      case c: CmdArgCtx  => enc.apply(c)
      case EmptyArg()    => ""
      case h: HereString => enc.apply(h)
      case h: HereDoc => enc.apply(h)
    }

    if(args.isEmpty()) s"${sc.name} ${sc.postCommands.map(enc.apply).mkString(" ")}"
    else {
      val argTxt = if(quoted) s""""${args}"""" else args
      s"""${sc.preCommands.map(enc.apply).mkString(" ") + " "}${sc.name} ${argTxt} ${sc.postCommands.map(enc.apply).mkString(" ")}"""
    }
    
  }
  
  implicit def hereDoc(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[HereDoc] = pure[HereDoc] { hs  =>
    s"""${hs.prefix}\n${enc.apply(hs.value)}\nEND"""
  }

  implicit def hereString(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[HereString] = pure[HereString] { hs  =>
    s"""${hs.prefix}"${enc.apply(hs.value)}""""
  }

  implicit def untilLoop(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CUntil] = pure[CUntil] { w =>
    s"""until ${w.testCommands.map(enc.apply).mkString(" ")} ${w.conseqCmds
      .map(enc.apply)
      .mkString(" ")}"""
  }

  implicit def whileLoop(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CWhile] = pure[CWhile] { w =>
    s"""while ${w.testCommands.map(enc.apply).mkString(" ")} ${w.conseqCmds
      .map(enc.apply)
      .mkString(" ")}"""
  }

  implicit def cInSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CIn] =
    pure[CIn] { cdo => s"in ${enc.apply(cdo.op)}" }

  implicit def forLoopSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CFor] = pure[CFor] { w =>
    val indexer = w.args.head match {
      case BashVariable(name, _, _) => 
        s"$name"
      case _ => enc.apply(w.args.head)
    }
    s"""for $indexer ${w.args.tail.map(enc.apply).mkString(" ")}"""
  }

  //TODO: need a test for this !
  def quote(left: String, tmp: String, quoted: Boolean, accum: Vector[String]): Vector[String] = {
    if(left.isEmpty()) accum
    else {
      left.head match {
        case '"' => 
          if(tmp.isEmpty()) quote(left.tail, "", true, accum)
          else quote(left.tail, "", false, accum :+ tmp) 
        case ' ' =>
          if(tmp.isEmpty()) quote(left.tail, "", quoted, accum)
          else if(quoted){
            quote(left.tail, tmp :+ left.head, quoted,  accum)
          }
          else quote(left.tail, "", quoted, accum :+ tmp) 
        case _ =>
          quote(left.tail, tmp :+ left.head, quoted,  accum)
      }
    }
  }

  implicit def bashVariableSerializer(
    implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[BashVariable] = pure[BashVariable] { b =>

    if(b.isExpanded) s"$$${b.name}"
    else {
      b.value match {
        case UnsetVariable() => s"unset $$${b.name}"
        case TextVariable(value) => s"""$$${b.name}="${enc.apply(value)}""""
        case SubShellVariable(value) => s"$$(${enc.apply(value)})"
        case ArrayVariable(value) => 
          val txt = enc.apply(value)
          val splitOnQuote = quote(txt, "", false, Vector.empty[String])
          val quoted = splitOnQuote.map(s => "\"" + s + "\"")
          s"""$$${b.name}=(${quoted.mkString(" ")})"""
      }
    }

  }

  implicit def localizationSerializer(
    implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[LocalizationString] = pure[LocalizationString] { l =>
    s"""$$"${enc.apply(l.value)}""""
  }

  implicit def ansiCQuotedSerializer(
    implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[AnsiCQuoted] = pure[AnsiCQuoted] { l =>
    s"""$$'${enc.apply(l.value)}'"""
  }

  implicit def cDoneSerializer: ScriptSerializer[CDone] =
    pure[CDone] { _ => "done" }

  implicit def cFiSerializer: ScriptSerializer[CFi] =
    pure[CFi] { _ => "fi" }

  implicit def cDoSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CDo] =
    pure[CDo] { cdo => s"do;\n${enc.apply(cdo.op)}\n" }

  implicit def cThenSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CThen] =
    pure[CThen] { cdo => s";then\n${enc.apply(cdo.op)}\n" }

  implicit def cElseSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CElse] =
    pure[CElse] { cdo => s"else\n${enc.apply(cdo.op)}\n" }

  implicit def cElifSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CElif] =
    pure[CElif] { elseif => s"elif ${enc.apply(elseif.op)}" }

  implicit def cIfSerializer(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[CIf] =
    pure[CIf] { iff =>
      s"if ${iff.testCommands.map(enc.apply).mkString(" ")} ${iff.conseqCmds.map(enc.apply).mkString(" ")}"
    }

  implicit def fileTypeSerializer: ScriptSerializer[FileType] = pure[FileType] {
    case FilePath(fp, fn) => s"""${fp.folders.mkString(fp.root.toString())}${fn.baseName.value}.${fn.fileExtension.extension.mkString(".")}"""
    case FolderPath(r, fp) => 
      s"""${fp.map(_.value).mkString(r.toString())}"""
    case FolderName(v) => v
    case SubFolderPath(folders) => s"""${folders.map(_.value).mkString("/")}"""
    case FileDescriptor(value) => value.toString()
    case FileExtension(extension) => extension.mkString(".")
    case BaseName(value) => value
    case FileName(bn,fe) => s"""${bn.value}.${fe.extension.mkString(".")}"""
    case RelPath(folders,fn) => s"""${folders.folders.mkString("/")}${fn.baseName.value}.${fn.fileExtension.extension.mkString(".")}"""
    case `/dev/null` => "/dev/null"
    case `/dev/random` => "/dev/random"
    case `/dev/stderr` => "/dev/stderr"
    case `/dev/stdin` => "/dev/stdin"
    case `/dev/stdout` => "/dev/stdout"
    case `/dev/fd`(fd) => s"/dev/fd/${fd.value}"
    case `/dev/tcp`(h,p) => s"/dev/tcp/${h}/${p}"
    case `/dev/udp`(h,p) => s"/dev/udp/${h}/${p}"
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

  implicit def conditionalBuilderSerializer(implicit enc: ScriptSerializer[CommandOp]): ScriptSerializer[ConditionalBuilder] = pure[ConditionalBuilder] { cb =>
    s"""${cb.cmds.map( b => enc.apply(b)).mkString(" ")}"""
  }
 
  implicit def condCIfIsFile(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIfIsFile] = pure[CIfIsFile] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsBlock(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsBlock] = pure[CIsBlock] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsCharacter(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsCharacter] = pure[CIsCharacter] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsDirectory(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsDirectory] = pure[CIsDirectory] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsFile(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsFile] = pure[CIsFile] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCGroupIdBitSet(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CGroupIdBitSet] = pure[CGroupIdBitSet] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsSymbolLink(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsSymbolLink] = pure[CIsSymbolLink] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCStickyBitSet(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CStickyBitSet] = pure[CStickyBitSet] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsNamedPipe(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsNamedPipe] = pure[CIsNamedPipe] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsReadAble(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsReadAble] = pure[CIsReadAble] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsGreaterThanZero(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsGreaterThanZero] = pure[CIsGreaterThanZero] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCFileDescriptorIsOpenAndReferTerminal(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CFileDescriptorIsOpenAndReferTerminal] = pure[CFileDescriptorIsOpenAndReferTerminal] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCUserIdBitSet(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CUserIdBitSet] = pure[CUserIdBitSet] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsWritable(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsWritable] = pure[CIsWritable] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsExecutable(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsExecutable] = pure[CIsExecutable] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsOwnedByEffectiveGroupId(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsOwnedByEffectiveGroupId] = pure[CIsOwnedByEffectiveGroupId] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsSymbolicLink(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsSymbolicLink] = pure[CIsSymbolicLink] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsModifiedSinceLastRead(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsModifiedSinceLastRead] = pure[CIsModifiedSinceLastRead] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsOwnedByEffectiveUserId(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsOwnedByEffectiveUserId] = pure[CIsOwnedByEffectiveUserId] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }
  

 implicit def condCIsSocket(
  implicit 
    enc: ScriptSerializer[CommandOp],
    fEnc: ScriptSerializer[FileType]): ScriptSerializer[CIsSocket] = pure[CIsSocket] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    val inner = ce.op match {
      case f: FileType => fEnc.apply(f)
      case _ => enc.apply(ce.op)
    }
    s"""${negated}-d $inner"""
  }

  implicit def dollarSerializer: ScriptSerializer[Dollar] = pure[Dollar] { _ =>
    "$"
  }

  implicit def subShellExpSerializer: ScriptSerializer[OpenSubShellExp] =
    pure[OpenSubShellExp] { _ => "$(" }

  implicit def openDoubleSquareBracketSerializer
      : ScriptSerializer[OpenDoubleSquareBracket] =
    pure[OpenDoubleSquareBracket] { _ => "[[" }

  implicit def openSubShellEnvSerializer: ScriptSerializer[OpenSubShellEnv] =
    pure[OpenSubShellEnv] { _ => "(\n" }
  
  implicit def closeSubShellEnvSerializer: ScriptSerializer[CloseSubShellEnv] =
    pure[CloseSubShellEnv] { _ => "\n)\n" }

  implicit def openCommandListSerializer: ScriptSerializer[OpenGroupInContext] =
    pure[OpenGroupInContext] { _ => "{\n" }
  
  implicit def closeCommandListSerializer: ScriptSerializer[CloseGroupInContext] =
    pure[CloseGroupInContext] { _ => "\n}\n" }

  implicit def pipeWithStdOutSerializer: ScriptSerializer[PipeWithStdOut] =
    pure[PipeWithStdOut] { _ => "|" }

  implicit def pipeWithErrorSerializer: ScriptSerializer[PipeWithError] =
    pure[PipeWithError] { _ => "|&" }

  implicit def orSerializer: ScriptSerializer[Or] =
    pure[Or] { _ => "||" }

  implicit def andSerializer: ScriptSerializer[And] =
    pure[And] { _ => "&&" }

  implicit def stdOutSerializer: ScriptSerializer[StdOut] =
    pure[StdOut] { _ => ">" }

  implicit def stdErrSerializer: ScriptSerializer[StdErr] =
    pure[StdErr] { _ => "2>" }

  implicit def appendStdOutSerializer: ScriptSerializer[AppendStdOut] =
    pure[AppendStdOut] { _ => ">>" }

  implicit def stdOutWithStdErrSerializer: ScriptSerializer[StdOutWithStdErr] =
    pure[StdOutWithStdErr] { _ => "&>" }

  implicit def appendStdOutWithSdErrSerializer
      : ScriptSerializer[AppendStdOutWithSdErr] =
    pure[AppendStdOutWithSdErr] { _ => "&>>" }

  implicit def stdInSerializer: ScriptSerializer[StdIn] =
    pure[StdIn] { _ => "<" }

  implicit def negateSerializer: ScriptSerializer[Negate] =
    pure[Negate] { _ => "!" }

  implicit def amperSerializer: ScriptSerializer[Amper] =
    pure[Amper] { _ => "&" }

  implicit def semiSerializer: ScriptSerializer[Semi] =
    pure[Semi] { _ => ";" }

  implicit def newLineSerializer: ScriptSerializer[NewLine] =
    pure[NewLine] { _ => "\n" }

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


  implicit def scriptLineSerializer: ScriptSerializer[ScriptLine] =
    pure[ScriptLine] { _ => "\n" }

}
