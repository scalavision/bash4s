package bash4s

import magnolia._

import scala.language.experimental.macros
import scala.annotation.implicitNotFound
import scripts._
import scripts.Annotations.arg
import scripts.Annotations.doc

@implicitNotFound(
  """Cannot find an BiologyGenerator for type ${T}.
     The script serialization is derived automatically for basic Scala types, case classes and sealed traits, but
     you need to manually provide an implicit BiologyGenerator for other types that could be nested in ${T}.
  """
)
trait ScriptGenerator[T] {
  def apply(t: T): ScriptMeta
}
object ScriptGenerator {
  
  type Typeclass[T] = ScriptGenerator[T]

  def pure[A](func: A => ScriptMeta): ScriptGenerator[A] =
    new ScriptGenerator[A] {
      def apply(value: A): ScriptMeta = func(value)
    }

  // We only want to extract the parameter names and annotations / documentation
  // Therefor we stop here ..
  implicit def nilGenerator[A]: ScriptGenerator[A] = pure[A] { _ => ScriptMeta("", "", List.empty[ArgOptional])}

  def combine[T](
      caseClass: CaseClass[ScriptGenerator, T]
  ): ScriptGenerator[T] = new ScriptGenerator[T] {
    def apply(t: T) = {

      val description = caseClass.annotations.collect { 
        case d: doc => d.description 
      }       
      val paramString = caseClass.parameters.map { p =>
        ArgOptional(p.label, p.annotations.collect {
          case d: arg => d.description
        }.headOption.fold(""){s => s}, 
        p.annotations.collect {
          case d: arg => d.short 
        }.headOption.fold(""){s => s})
      }.toList

      ScriptMeta(caseClass.typeName.short, description.headOption.fold(""){s => s}, paramString)
    }
  }

  def dispatch[T](
      sealedTrait: SealedTrait[ScriptGenerator, T]
  ): ScriptGenerator[T] = new ScriptGenerator[T] {

    def apply(t: T) = {
      sealedTrait.dispatch(t) { subtype =>
        subtype.typeclass.apply(subtype.cast(t))
      }
    }
    /*
    def apply(t: T) = {
      ScriptMeta(sealedTrait.typeName.short, "", List.empty[ArgOpt])
    }*/
  }


  implicit def gen[T]: ScriptGenerator[T] = macro Magnolia.gen[T]
  
}