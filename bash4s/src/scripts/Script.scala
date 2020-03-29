package bash4s.scripts

import bash4s.domain._

abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def cmdOp: CommandOp
  def run() = cmdOp.run(name)

}