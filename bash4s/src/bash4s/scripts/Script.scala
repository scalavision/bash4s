package bash4s.scripts

import bash4s.domain._
import bash4s.ScriptLinter

abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def op: CommandOp
  def txt = op.txt
  def run() = op.run(name)
  def param: ScriptMeta
  def init(op: CommandOp) = Some(op)
  def setup: Option[CommandOp] = Option.empty[CommandOp]
  def script = {
   
    val comments = param.description.foldLeft(""){(acc,c) =>
      c match {
        case '\n' => acc + "\n #  "
        case _ => acc + c
      }
    }.reverse.dropWhile(_ != '#').drop(1).reverse + "\n"

    val argComments = param.argOpt.zipWithIndex.foldLeft(""){ (acc, ia) =>
      val (a, index) = ia
      acc + s"\n # $$${index + 1} (${a.long}): ${a.description}" 
    }

    ScriptLinter.lint(
      s"""#!/usr/bin/env bash
      |${comments}
      |${argComments}
      |${setup.fold(""){_.txt}}
      |${op.txt}
      """.stripMargin
    )
  }
}