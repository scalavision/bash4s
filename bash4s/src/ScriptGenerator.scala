package bash4s

import magnolia._

import scala.language.experimental.macros
import scala.annotation.implicitNotFound

@implicitNotFound(
  """Cannot find an BiologyGenerator for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologyGenerator for other types that could be nested in ${T}.
  """
)
trait ScriptGenerator[T] {
  def apply(t: T): String
}
object ScriptGenerator {
  
  type Typeclass[T] = ScriptGenerator[T]

  def pure[A](func: A => String): ScriptGenerator[A] =
    new ScriptGenerator[A] {
      def apply(value: A): String = func(value)
    }

  // We only want to extract the parameter names and annotations / documentation
  // Therefor we stop here ..
  implicit def nilGenerator[A]: ScriptGenerator[A] = pure[A] { _ => ""}

  def combine[T](
      caseClass: CaseClass[ScriptGenerator, T]
  ): ScriptGenerator[T] = new ScriptGenerator[T] {
    def apply(t: T) = {
      pprint.pprintln(caseClass)
      println("annotations:")
      pprint.pprintln(caseClass.annotations)
      println("isObject")
      pprint.pprintln(caseClass.isObject)
      println("typeName")
      pprint.pprintln(caseClass.typeName)
      println("rawConstruct")
      pprint.pprintln(caseClass.formatted(""))

      val paramString = caseClass.parameters.map { p =>
        p.label + p.annotations
      }
      paramString.mkString(" ")
    }
    /*
      val paramString = caseClass.parameters.map { p =>
        p.typeclass.apply(p.dereference(t))
      }
      paramString.mkString("")
    }}
    */
  }

  def dispatch[T](
      sealedTrait: SealedTrait[ScriptGenerator, T]
  ): ScriptGenerator[T] = new ScriptGenerator[T] {
    def apply(t: T) = {
      sealedTrait.typeName.full
      /*
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }*/
    }
  }


  implicit def gen[T]: ScriptGenerator[T] = macro Magnolia.gen[T]
  
}