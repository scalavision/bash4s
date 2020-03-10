package bash

import domain._

sealed trait ConditionalExpr

case class FileConditional(
  param: Char,
  exp: CommandOp
) extends ConditionalExpr

final case class a(cmdOp: CommandOp) {
  def unary_- = ConditionalExprAccum(Vector(
    FileConditional("a", cmdOp)
  ))
}

case class ConditionalExprAccum(
  accum: Vector[ConditionalExpr]
) extends ConditionalExpr {
  def && (ce: ConditionalExpr) = 
    copy(accum = accum :+ ce)
}
