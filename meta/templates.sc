
def toCCCommandOp(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s() extends $ext"""
}

def toAdt(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCCCommandOp(ext)(s)).mkString("\n")}
  """
}