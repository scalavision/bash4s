package bash4s

import magnolia._
import domain._

import scala.language.experimental.macros
import scala.annotation.implicitNotFound
import _root_.bash4s.domain.CommandOp

@implicitNotFound(
  """Cannot find an BiologyGenerator for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologyGenerator for other types that could be nested in ${T}.
  """
)
trait ScriptGenerator[T] extends ScriptSerializer[T] {
  def apply(t: T): String
}
object ScriptGenerator {
  
  type Typeclass[T] = ScriptGenerator[T]

  def pure[A](func: A => String): ScriptGenerator[A] =
    new ScriptGenerator[A] {
      def apply(value: A): String = func(value)
    }

  implicit val stringGenerator: ScriptGenerator[String] = pure[String] {
    _.toString()
  }
  implicit val booleanGenerator: ScriptGenerator[Boolean] = pure[Boolean] {b: Boolean =>  b.toString() }
  implicit val intGenerator: ScriptGenerator[Int] = pure[Int] { _.toString() }
  implicit val charGenerator: ScriptGenerator[Char] = pure[Char] {
    _.toString()
  }

   implicit def vectorGenerator[A](
      implicit enc: ScriptGenerator[A]
  ): ScriptGenerator[Vector[A]] =
    pure[Vector[A]] { _.map(enc.apply).mkString(" ") }
    
  implicit def seqGenerator[A](
      implicit enc: ScriptGenerator[A]
  ): ScriptGenerator[Seq[A]] =
    pure[Seq[A]] { _.map(enc.apply).mkString(" ") }

  implicit def vectorGeneratorAny: ScriptGenerator[Vector[Any]] =
    pure[Vector[Any]] {
      _.map {
        case c: CommandOp => c.txt
        case a: Any       => a.toString()
      }.mkString(" ")
    }
  
  implicit val folderPathGenerator: ScriptGenerator[FolderPath] = pure[FolderPath] { _.toString() }
  implicit val relFolderPathGenerator: ScriptGenerator[RelPath] = pure[RelPath] { _.toString() }
  implicit val subSubFolderPathGenerator: ScriptGenerator[SubFolderPath] = pure[SubFolderPath] { _.toString() }
  
  implicit def fileTypeGenerator: ScriptGenerator[FileType] = pure[FileType] {
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


  implicit def commandOpGen: ScriptGenerator[CommandOp] = pure[CommandOp] {
    _.txt
  }

  def combine[T](
      caseClass: CaseClass[ScriptGenerator, T]
  ): ScriptGenerator[T] = new ScriptGenerator[T] {
    def apply(t: T) = {
      val paramString = caseClass.parameters.map { p =>
        p.typeclass.apply(p.dereference(t))
      }
      paramString.mkString("")
    }}
    /*
      val paramString = caseClass.parameters.map { p =>
        p.label
      }
      paramString.mkString(" ")
    }
  }*/

  def dispatch[T](
      sealedTrait: SealedTrait[ScriptSerializer, T]
  ): ScriptSerializer[T] = new ScriptSerializer[T] {
    def apply(t: T) = {
      sealedTrait.typeName.full
      /*
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }*/
    }
  }


  implicit def gen[T]: ScriptSerializer[T] = macro Magnolia.gen[T]
  
  
}