package bash4s.scripts

import bash4s.domain._

trait Script {
  def src: CommandOp
}