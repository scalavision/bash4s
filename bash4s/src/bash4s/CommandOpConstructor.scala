package bash4s

import scala.language.experimental.macros
import scala.annotation.implicitNotFound
import scala.language.implicitConversions

import magnolia._
import domain._

@implicitNotFound(
  """Cannot find an BiologySerializer for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologySerializer for other types that could be nested in ${T}.
  """
)
trait CommandOpConstructor[T] {
  def apply(t: T): CommandOp
}

object CommandOpConstructor {

  type Typeclass[T] = CommandOpConstructor[T]

  def pure[T](func: T => CommandOp): CommandOpConstructor[T] = new CommandOpConstructor[T] {
    def apply(t: T): CommandOp = func(t)
  }
 
  implicit val stringConstructor: CommandOpConstructor[String] = pure[String] { s => txt"$s" }
  implicit val intConstructor: CommandOpConstructor[Int] = pure[Int] { i => IntVariable(i) }
  implicit val charConstructor: CommandOpConstructor[Char] = pure[Char] { s => txt"$s" }
  
  implicit def vecConstructor[A](enc: CommandOpConstructor[A]): CommandOpConstructor[Vector[A]] = pure[Vector[A]] { vec => 
    ScriptBuilder(vec.map(enc.apply))
  }
  
  implicit def vecStringConstructor[String](enc: CommandOpConstructor[String]): CommandOpConstructor[Vector[String]] = pure[Vector[String]] { vec => 
    ScriptBuilder(vec.map(enc.apply))
  }

  implicit def optConstructor[A](enc: CommandOpConstructor[A]): CommandOpConstructor[Option[A]] = pure[Option[A]] { 
    _.fold[CommandOp](DebugValue("")) { e => enc.apply(e) }
  }
  
  def combine[T](
      caseClass: CaseClass[CommandOpConstructor, T]
  ): CommandOpConstructor[T] = new CommandOpConstructor[T] {
    def apply(t: T) = {
      ScriptBuilder(
        caseClass.parameters.map { p =>
          p.typeclass.apply(p.dereference(t))
        }.toVector
      )
    }
  }

  def dispatch[T](
      sealedTrait: SealedTrait[CommandOpConstructor, T]
  ): CommandOpConstructor[T] = new CommandOpConstructor[T] {
    def apply(t: T) = {
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }
    }
  }


  
  implicit def gen[T]: CommandOpConstructor[T] = macro Magnolia.gen[T]
  
}