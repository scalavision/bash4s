package bash

import domain._

sealed trait ConditionalExpr

final case class a(f: FileTypeOp) extends ConditionalExpr {
  def unary_- = ConditionalExprAccum(Vector(this))
}

case class ConditionalExprAccum(
  accum: Vector[ConditionalExpr]
) extends ConditionalExpr {
  def && (ce: ConditionalExpr) = 
    copy(accum = accum :+ ce)
}


