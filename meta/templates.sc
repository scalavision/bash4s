
def toCmdOp(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s() extends $ext"""
}

def toAdt(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toOpDef(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1}[B <: A](op: CommandOp) = self.copy((acc :+ ${fnMeta._2}())  ++ decomposeOnion(op))"""
}