package bash4s.scripts

import scala.annotation.StaticAnnotation

object Annotations {

  /**
   * Annotation used to describe the parameter
   */
  case class doc(description: String, short: String = "") extends StaticAnnotation

}