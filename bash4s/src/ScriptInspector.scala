package bash

import domain._

object ScriptInspector {

  val serializer = ScriptSerializer.gen[CommandOp]

  case class ScriptContent(
    ops: Vector[CommandOp] = Vector.empty[CommandOp],
    env: Map[String, BashVariable]= Map.empty[String, BashVariable],
  )

  def traverse(ops: Vector[CommandOp]): Vector[CommandOp] = 
    ops.foldLeft(ScriptContent()){(sc, op) => 
      val newOp = op match {
        case RefVariable(name, _) => 
          sc.env.get(name).fold { 
              throw new Exception(s"Trying to reference variable $name that has not been given a value")
          }{ bVar =>
              bVar.value match {
                case BString(v) => 
                  DebugString(v)
                case BSubCommand(v) => 
                  DebugString(v.map(serializer.apply).mkString(" "))
                case BEmpty() => 
                  throw new Exception(s"Trying to reference variable $name that has not been given a value")
              }
          }
        case _ => op 
      }

      sc.copy(
        ops = sc.ops :+ newOp,
        env = op match {
          case b: BashVariable =>
            sc.env + (b.name -> b)
          case _ => sc.env
        }
      )
    }.ops

  def bashRefs(builder: ScriptBuilder): ScriptBuilder =
    ScriptBuilder(traverse(builder.acc))
    
  /*
    builder.copy(acc = builder.acc.map {
      case RefVariable(_, bValue) =>
        bValue match {
          case BString(v) => DebugString(v)
          case BSubCommand(v) => 
            DebugString(v.map(serializer.apply).mkString(" "))
          case BEmpty() => DebugString("")
        }
      case op => op
    })
    */

}