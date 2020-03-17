package bash4s

import domain._

object o {
  def `{`(op: CommandOp) = 
    CommandListBuilder(Vector(OpenGroupInContext(), op))
}

