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
trait Param[T] {
  // We get the name of the parameter, and the value it contains
  def apply(t: T): (String, String)
}
object Param {

  type Typeclass[T] = Param[T]

  def pure[A](func: A => (String,String)): Param[A] =
    new Param[A] {
      def apply(value: A): (String, String) = func(value)
    }
   
  implicit val stringSerializer: Param[String] = pure[String] { s =>
    (s.toString(), s.toString())
  }
  implicit val booleanSerializer: Param[Boolean] = pure[Boolean] {b: Boolean =>  (b.toString(), b.toString())  }
  implicit val intSerializer: Param[Int] = pure[Int] { i => (i.toString(), i.toString())  }
  implicit val charSerializer: Param[Char] = pure[Char] { c =>
    (c.toString(), c.toString())
  }
  
  implicit def vectorSerializer[A](
      implicit enc: Param[A]
  ): Param[Vector[A]] =
    pure[Vector[A]] { v => ("", v.map(enc.apply).mkString(" ")) }

  def combine[T](
      caseClass: CaseClass[Param, T]
  ): Param[T] = new Param[T] {
    def apply(t: T) = {

      val value = t match {
        case op: CommandOp => op.txt
        case _ => throw new Exception("Unsupported type")
      }

      val name = caseClass.typeName.short
      (name, value)

    }
  }
  
  def dispatch[T](
      sealedTrait: SealedTrait[Param, T]
  ): Param[T] = new Param[T] {
    def apply(t: T) = {
      (sealedTrait.toString(), t.toString())
      /*
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }*/
    }
  }


  implicit def gen[T]: Param[T] = macro Magnolia.gen[T]

}