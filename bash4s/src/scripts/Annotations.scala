package bash4s.scripts

import scala.annotation.StaticAnnotation

object Annotations {

  /**
   * Annotation used to describe the parameter
   */
  case class doc(description: String) extends StaticAnnotation
  case class arg(description: String, short: String = "") extends StaticAnnotation

}