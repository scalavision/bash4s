import $file.syntax, syntax._
import SyntaxEnhancer._

import $file.formatter, formatter._

import $file.templates
val tmpl = templates

import $file.command_op 
val cops = command_op

val symbolNames = cops.cmdListFns.dropRight(1) ++ cops.pipeFns ++ cops.redirectionFns

val serializeSymbols: ((String, String)) => String = { case (symbol, name) => 
  val symbolFixed = if(symbol == "o") "\\n" else symbol
  s"""|implicit val ${name.uncapFirst}Serializer: ScriptSerializer[${name}] = 
      | pure[${name}] { _ => "${symbolFixed}" }""".stripMargin
}

def src: String =
  s"""|${symbolNames.map(serializeSymbols).mkString("\n")}
  |""".stripMargin

def generateSerializer(dest: os.Path): Unit = {
  val template = os.read( os.pwd / "meta" / "ScriptSerializer.template").lines.toList.dropRight(1).mkString("\n")

  val path = dest / "ScriptSerializer.scala"
  val scriptSerializerSrc = Formatter.style(template + src + "}", path)
  os.write.over(path, scriptSerializerSrc) 
}
