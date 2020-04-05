package bash4s.scripts

import bash4s.domain._
import bash4s.ScriptLinter

abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def op: CommandOp
  def txt = op.txt
  def run() = op.run(name)
  def param: ScriptMeta
  def script = {
   
    val comments = param.description.foldLeft(""){(acc,c) =>
      c match {
        case '\n' => acc + "\n #  "
        case _ => acc + c
      }
    }.reverse.dropWhile(_ != '#').drop(1).reverse + "\n"

  ScriptLinter.lint(
    s"""#!/usr/bin/env bash
    |
    |${comments}
    |${op.txt}
    """.stripMargin
  )

  }
  // BashCliArgVariable(gen.$1, path)
  // ArgTemplate.optionParser(param.argOpt) + "\n" + 
  /*
  def scriptify = cmdOp match {
    case BashVariable(name, value, isExpanded) => ???
  }*/
}