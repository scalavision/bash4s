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

    // If scripts are merged, their environment also needs to be merged,
    // hence we need to reindex the positional parameters, i.e. $1, $2 .. $8 etc.
    val argParam: Vector[BashVariable] = {
      val params: Vector[BashVariable] = setup match {
        case None => Vector.empty[BashVariable]
        case Some(value) => value match {
          case ScriptBuilder(acc) => acc collect {
            case b: BashVariable => b
          }
          case _ => throw new Exception("You can only use BashVariable as a command line argument")
        }
      }
      params.zipWithIndex.map {
        case (b, index) => b.value match {
          case bcli: BashCliOptArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString))
          case bcli: BashCliArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
          case bcli: BashCliVecArgVariable => b.copy(value = bcli.copy(name = (index + 1).toString()))
          case _ => b
        }
      }
    }

    ScriptLinter.lint(
      s"""#!/usr/bin/env bash
      |${comments}
      |${argComments}
      |${argParam.map(_.txt).mkString("\n")}
      |${op.txt}
      """.stripMargin
    )
  }
}