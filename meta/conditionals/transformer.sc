
val wd = os.pwd

val list = 
  os.read(wd / "file_conditionals.txt")
    .lines.toList
    .filterNot(_.isEmpty)
    .take(14).toList

case class MetaCond(c: Char, comment: String)

val data = list.sliding(size=2, step=2).toList.map { c =>
  MetaCond(c(0).drop(1).head, c(1).dropRight(1))
}

pprint.pprintln(data)

def template = s"""
final case class a(f: FileTypeOp) extends ConditionalExpr {
  def unary_- = ConditionalExprAccum(Vector(this))
}
"""
