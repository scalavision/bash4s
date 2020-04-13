package bash4s.scripts

import bash4s.domain._

object ScriptFormatter {

  def addBreak: CommandOp => List[CommandOp] = {
    case Amper() => List(Amper(), BreakLine())
    case PipeWithStdOut() => List(PipeWithStdOut(), BreakLine())
    case PipeWithError() => List(PipeWithError(), BreakLine())
    case Or() => List(Or(), BreakLine())
    case And() => List(And(), BreakLine())
    case b => List(b)
  }

  def break(builder: ScriptBuilder): CommandOp => ScriptBuilder = {
    case Amper() => builder.copy(acc = builder.acc :+ Amper() :+ BreakLine())
    case And()=> builder.copy(acc = builder.acc :+ And() :+ BreakLine())
    case PipeWithStdOut()=> builder.copy(acc = builder.acc :+ PipeWithStdOut() :+ BreakLine())
    case PipeWithError() => builder.copy(acc = builder.acc :+ PipeWithError() :+ BreakLine())
    case Or() => builder.copy(acc = builder.acc :+ Or() :+ BreakLine())
    case PipelineBuilder(cmds) => cmds match {
      case Vector() => builder.copy(acc = builder.acc :+ PipelineBuilder(cmds) :+ NewLine())
      case _ +: _ => builder.copy(acc = builder.acc :+ PipelineBuilder( cmds.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
        acc ++ addBreak(c) 
      }
      ))
    }
    case CommandListBuilder(cmds) => cmds match {
      case Vector() => builder.copy(acc = builder.acc :+ PipelineBuilder(cmds) :+ NewLine())
      case _ +: _ => builder.copy(acc = builder.acc :+ PipelineBuilder( cmds.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
        acc ++ addBreak(c) 
      }
      ))
    }
    case head => builder.copy(acc = builder.acc :+ head)
  }

  def loop(op: CommandOp, builder: ScriptBuilder): CommandOp = op match {
    case ScriptBuilder(acc) => acc match {
      case Vector() => builder.copy(acc = builder.acc :+ NewLine())
      case x +: xs => loop(ScriptBuilder(xs), break(builder)(x))
    }
    case CommandListBuilder(cmds) => cmds match {
      case Vector() =>  builder
      case x +: xs => loop(CommandListBuilder(xs), break(builder)(x))
    }
    case PipelineBuilder(cmds) => cmds match {
      case Vector() => builder
      case x +: xs => loop(PipelineBuilder(xs), break(builder)(x))
    }

    case Amper() => builder.copy(acc = builder.acc :+ Amper() :+ BreakLine())
    case And() => builder.copy(acc = builder.acc :+ And() :+ BreakLine())
    case PipeWithStdOut() => builder.copy(acc = builder.acc :+ PipeWithStdOut() :+ BreakLine())
    case PipeWithError() => builder.copy(acc = builder.acc :+ PipeWithError() :+ BreakLine())
    case Or() => builder.copy(acc = builder.acc :+ Or() :+ BreakLine())
    case _ => builder.copy(acc = builder.acc :+ op)
  }

  def apply(op: CommandOp) = 
    loop(op, ScriptBuilder(Vector.empty))
  
}
