package bash4s

import bash4s.dsl._
import domain._

object IF {
  def isDir(path: FolderPath): CommandListBuilder = `[[` (-d(path)).`]]`
}