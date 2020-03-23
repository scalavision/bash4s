import $file.syntax, syntax._
import SyntaxEnhancer._

val wd = os.pwd

val list = 
  os.read(wd / "file_conditionals.txt")
    .lines.toList
    .filterNot(_.isEmpty)
   // .take(14)
    .toList

val names = 
  "CIfIsFile CIsBlock CIsCharacter CIsDirectory CIsFile CGroupIdBitSet CIsSymbolLink CStickyBitSet CIsNamedPipe CIsReadAble CIsGreaterThanZero CFileDescriptorIsOpenAndReferTerminal CUserIdBitSet CIsWritable CIsExecutable CIsOwnedByEffectiveGroupId CIsSymbolicLink CIsModifiedSinceLastRead CIsOwnedByEffectiveUserId CIsSocket".list

case class MetaCond(c: Char, comment: String, name: String)

val data = list.sliding(size=2, step=2).toList.zip(names).map {

  case (c, n)  =>
  MetaCond(c(0).drop(1).head, c(1).dropRight(1), n)
}


//pprint.pprintln(data)

def template = s"""
final case class a(f: FileTypeOp) extends ConditionalExpr {
  def unary_- = ConditionalExprAccum(Vector(this))
}
"""

def fn(m: MetaCond) = s"""
  // ${m.comment}
  def ${m.c}(op: CommandOp) = ${m.name}(op)
"""

def domain(name: String) = s"""
final case class ${name}(op: CommandOp, isNegated: Boolean = false) extends ConditionalExpression { self =>
  def unary_- = self
  def unary_! = copy(isNegated = true)
  def &&(op: CommandOp) = ConditionalBuilder(Vector(self, And(), op))
  def ||(op: CommandOp) = ConditionalBuilder(Vector(self, Or(), op))
}
"""

def serializer(m: MetaCond) = s"""
  implicit def cond${m.name}(implicit enc: ScriptSerializer[CommandOp]): ScriptSerializer[${m.name}] = pure[${m.name}] { ce =>
    val negated = if(ce.isNegated) "! " else ""
    s\"\"\"$${negated}-${m.c} $${enc.apply(ce.op)}\"\"\"
  }
"""

val domainDsl = data.map(_.name).map(domain).mkString("\n")
val bashDsl = data.map(fn).mkString("\n")
val serial = data.map(serializer).mkString("\n")

println(serial)
//println(domainDsl)