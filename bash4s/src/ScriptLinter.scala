package bash4s

import domain._

object ScriptLinter {

    def beautify(op: CommandOp) = {
      val s = op.txt
      val lines = s.split("\n")

      val split1: String = lines
        .map { l =>
          if (l.length() > 80) {
            l.split("&&").mkString("\n  &&")
          } else l
        }
        .mkString("\n")

      val lines2 = split1.split("\n")

      val split2: String = lines2
        .map { l =>
          if (l.length() > 80) {
            l.split('|').mkString("\n  |")
          } else l
        }
        .mkString("\n")

      //TODO: This could be extended with ||, & etc.

      split2.trim().replaceAll(" +", " ")

    }

    def simplify(op: CommandOp) = {

      op.txt.foldLeft("") { (acc, s) =>
          if (acc.isEmpty()) acc + s
          else {
            (acc.head, s) match {
              case ('\\', '\n') =>
                " "
              case _ => acc + s
            }
          }
        }
        .trim()
        .replaceAll(" +", " ")
      }

}