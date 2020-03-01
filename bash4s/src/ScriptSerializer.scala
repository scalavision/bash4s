package bash

import magnolia._
import scala.language.experimental.macros
import scala.annotation.implicitNotFound

@implicitNotFound(
  """Cannot find an BiologySerializer for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologySerializer for other types that could be nested in ${T}.
  """)
trait ScriptSerializer[T] {
  def apply(t: T): String
}
object ScriptSerializer {

  type Typeclass[T] = ScriptSerializer[T]

  def pure[A](func: A => String): ScriptSerializer[A] = new ScriptSerializer[A] {
    def apply(value: A ): String = func(value)
  }

  implicit val stringSerializer: ScriptSerializer[String] = pure[String] { _.toString() }
  implicit val intSerializer: ScriptSerializer[Int] = pure[Int] { _.toString() }
  implicit val charSerializer: ScriptSerializer[Char] = pure[Char] { _.toString() }

  implicit def vectorSerializer[A](implicit enc: ScriptSerializer[A]): ScriptSerializer[Vector[A]] = 
    pure[Vector[A]] { _.map(enc.apply).mkString("\n") }

  def combine[T](caseClass: CaseClass[ScriptSerializer, T]): ScriptSerializer[T] = new ScriptSerializer[T]{
    def apply(t: T) = {
      val paramString = caseClass.parameters.map { p =>
        p.typeclass.apply(p.dereference(t))
      }
      paramString.mkString("")
    }
  }

  def dispatch[T](sealedTrait: SealedTrait[ScriptSerializer, T]): ScriptSerializer[T] = new ScriptSerializer[T]{
    def apply(t: T) = {
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }
    }
  }

  implicit def gen[T]: ScriptSerializer[T] = macro Magnolia.gen[T]

}