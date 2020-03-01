object SyntaxEnhancer {
  def name(implicit n: sourcecode.Name): String = n.value
  implicit class StringSyntax(s: String) {
    def list = s.split(" ").map(_.trim()).toList
    def uncapFirst = s.head.toLower + s.tail
    def capFirst = s.head.toUpper + s.tail
  }

}
