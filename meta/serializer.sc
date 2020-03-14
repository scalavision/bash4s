import $file.syntax, syntax._
import SyntaxEnhancer._
import $file.formatter, formatter._
import $file.templates
val tmpl = templates
import $file.command_op 
val cops = command_op

import $file.meta_model
val mm = meta_model

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

def opContentTemplate(text: String, className: String) = 
  s"""
  implicit def ${className.capFirst}(
      implicit enc: ScriptSerializer[CommandOp]
  ): ScriptSerializer[$className] = pure[$className] { f =>
    s"$text $${loopArgSerializer(f.op, enc)}"
  }
  """
{

}

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
} ++ cops.conditionalFns.filterNot(s => s._1 == "True" || s._1 == "False").map {
  case (symbol, name)   =>
    (symbol.filter(_ != '`').uncapFirst, name)
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

def src2: String =
  s"""|${symbolNames.map(serializeSymbols).mkString("\n")}
      |${fileTypeOpTemplate} 
      |${fileTypeOpNames.map(serializeFileTypeOp).mkString("\n")}
      |${cops.conditionalExprFns.map { case (symbol, name) =>
            val fixedSymbol = if(symbol == "Then") ";then\\n" else if(symbol == "Do") ";do\\n" else symbol.uncapFirst
           opContentTemplate(fixedSymbol.filter(_ != '`').uncapFirst, name)
      }.mkString("\n")}
  |""".stripMargin


val tps = 
  mm.symbolsInit ++ mm.symbolsWithArg ++ mm.symbolsNoArgWithArg

val seria: ((String, String)) => String = {
  case (symbol,name) => 
    val fixedSymbol = symbol.filterNot(_ == '`')
    s"""implicit def ${name.uncapFirst}Serializer: ScriptSerializer[$name] = pure[$name] { _ => "$fixedSymbol" }"""
}

val others = List(
  ("\\n", "ScriptLine")
)
val cmb = (tps.zip(tps.map(mm.extract)) ++ others).map(seria).mkString("\n")

def src: String = 
  s"""|
      |${cmb}""".stripMargin

def generateSerializer(dest: os.Path): Unit = {
  val template = os.read( os.pwd / "meta" / "ScriptSerializer.template").lines.toList.dropRight(1).mkString("\n")
  val path = dest / "ScriptSerializer.scala"
  val scriptSerializerSrc = Formatter.style(template + "\n" + src + "\n}", path)
  os.write.over(path, scriptSerializerSrc) 
}
