package bash4s

import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}
