object SyntaxEnhancer {
  def name(implicit n: sourcecode.Name): String = n.value
  implicit class StringSyntax(s: String) {
    def list = s.split(" ").map(_.trim()).toList
    def capFirst = s.head.toLower + s.tail
  }

}
