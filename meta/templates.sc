
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

def toAdtOpClass(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s(op: CommandOp) extends $ext"""
}

def toAdtOpValue(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toAdtOpClass(ext)(s)).mkString("\n")}
  """
}

def toAdt(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toAdtSuper(superTrait: String, ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends $superTrait
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toOpDef(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1}(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}())  ++ decomposeOnion(op))"""
}

def toOpDefWithNewLineTerminator(fnMeta: (String, String)) = {
  s"""def ${fnMeta._1}^(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}() :+ NewLine()) ++ decomposeOnion(op))"""
}

def toOpDefEmpty(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1} = self.copy(acc = acc :+ ${fnMeta._2}())"""
}

def emptyDefBuilder(fnMeta: (String, String)) =
  s"""def ${fnMeta._1} = ${fnMeta._2}()"""