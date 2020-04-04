package bio

import bash4s.domain._
import domain._

package object bio {

  implicit class BioFileSyntax(p: FilePath) {
    def bam = BiologyFilePath[Bam](FolderPath(p.root, p.folderPath), p.fileName)
  }

}