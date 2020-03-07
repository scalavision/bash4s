package bash

import domain._

case class For (
  iterator: BashVariable,
  iteratee: RefVariable = RefVariable("", BEmpty()),
  script: ScriptBuilder = ScriptBuilder(Vector.empty[CommandOp])
) { self =>

  def In (iteratee: RefVariable) = 
    self.copy(iteratee = iteratee)

  def Do(script: ScriptBuilder) = 
    self.copy(script = script)

  def Done = 
    ForLoop(self)
}

object For {
  def apply(iterator: BashVariable): For = For(iterator)
}
/*
case class ForB (
  iterator: BashVariable,
  acc: Vector[CommandOp],
  iteratee: RefVariable = RefVariable("", BEmpty()),
  script: ScriptBuilder = ScriptBuilder(Vector.empty[CommandOp])
) { self =>

  def In (iteratee: RefVariable) = 
    self.copy(iteratee = iteratee)

  def Do(script: ScriptBuilder) = 
    self.copy(script = script)

  def Done = ScriptBuilder(
    acc :+ ForLoop(ForB(iterator, Vector.empty[CommandOp], iteratee, script))
  )

}

object ForB {
  def apply(iterator: BashVariable, acc: Vector[CommandOp]): ForB = ForB(iterator, acc)
}*/