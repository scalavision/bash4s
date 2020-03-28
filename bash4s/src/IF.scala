package bash4s

import bash4s._
import domain._

object IF {
  def isDir(path: FolderPath): CommandOp = `[[` (-d(path)).`]]`
}