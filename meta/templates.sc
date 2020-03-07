
def toCmdOp(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s() extends $ext"""
}

def toCmdOpValue(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s(value: String) extends $ext"""
}

def toAdtValue(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOpValue(ext)(s)).mkString("\n")}
  """
}

def toAdt(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toOpDef(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1}(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}())  ++ decomposeOnion(op))"""
}

def toOpDefWithNewLineTerminator(fnMeta: (String, String)) = {
  s"""def ${fnMeta._1}^(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}() :+ NewLine()) ++ decomposeOnion(op))"""
}

