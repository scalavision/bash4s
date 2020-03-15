package old.bash

import domain._

case class RegexFileSearchApi() {

  def txt = 
    RegexFileSearch("*.txt")
  
  def bam = 
    RegexFileSearch("*.bam")
}

case class ConditionalExpressions() {
  def a(op: CommandOp) = ConditionalExpression("a", op)
}