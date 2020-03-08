import $file.syntax, syntax._
import SyntaxEnhancer._

import $file.formatter, formatter._

import $file.templates
val tmpl = templates

import $file.command_op 
val cops = command_op

val fileTypeOpNames = List(
  ( "`/dev/stdin`", "devStdIn" ),
  ( "`/dev/stdout`", "devStdOut" ),
  ( "`/dev/stderr`", "devStdErr" ),
  ( "`/dev/null`", "devNull" ),
  ( "`/dev/random`", "devRandom" )
  
)

val fileTypeOpTemplate = s"""
  implicit val devFdSerializer: ScriptSerializer[`/dev/fd`] =
    pure[`/dev/fd`] { df => s"/dev/fd/$${df.fileDescriptor}" }
  implicit val devTcpSerializer: ScriptSerializer[`/dev/tcp`] =
    pure[`/dev/tcp`] { dt => s"/dev/tcp/$${dt.host}/$${dt.port}" }
  implicit val devUdpSerializer: ScriptSerializer[`/dev/udp`] =
    pure[`/dev/udp`] { dt => s"/dev/udp/$${dt.host}/$${dt.port}" }
"""

val symbolNames = (cops.cmdListFns.dropRight(1) ++ cops.pipeFns ++ cops.redirectionFns ++ List(
  ("$(", "SubCommandStart"), (")", "SubCommandEnd"),
  ("<(", "ProcCommandStart"), (")", "ProcCommandEnd"),
  ("true", "CTrue"),
  ("false", "CFalse")
)).map {
  case (symbol, name) =>
    (symbol.filter(_ != '`'), name)
} ++ cops.loopCtrlFns.map {
  case (symbol, name) =>
    val fixedSymbol = if(symbol == "Do") ";do\\n" else symbol.uncapFirst
    (fixedSymbol, name)
}

val serializeFileTypeOp: ((String, String)) => String = { case (symbol, name) => 
  s"""|implicit val ${name.uncapFirst}Serializer: ScriptSerializer[${symbol}.type] = 
      | pure[${symbol}.type] { _ => "${symbol.filter(_ != '`')}" }""".stripMargin
}

val serializeSymbols: ((String, String)) => String = { case (symbol, name) => 
  val symbolFixed = if(symbol == "o") "\\n" else symbol
  s"""|implicit val ${name.uncapFirst}Serializer: ScriptSerializer[${name}] = 
      | pure[${name}] { _ => "${symbolFixed}" }""".stripMargin
}

def src: String =
  s"""|${symbolNames.map(serializeSymbols).mkString("\n") + "\n" + fileTypeOpTemplate + "\n" + fileTypeOpNames.map(serializeFileTypeOp).mkString("\n")}
  |""".stripMargin

def generateSerializer(dest: os.Path): Unit = {
  val template = os.read( os.pwd / "meta" / "ScriptSerializer.template").lines.toList.dropRight(1).mkString("\n")

  val path = dest / "ScriptSerializer.scala"
  val scriptSerializerSrc = Formatter.style(template + "\n" + src + "\n}", path)
  os.write.over(path, scriptSerializerSrc) 
}
