package bash4s

import domain._

sealed trait ParserContext

final case object LineContext extends ParserContext
final case object IfContext extends ParserContext
final case object ElIfContext extends ParserContext
final case object ElseContext extends ParserContext

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

    /*
    def reproducibleString(s: String) = {

      def wordLoop(rest: String, ctx: ParserContext, accum: String, result: String): String = {

      }

      def newLineLoop(rest: List[String], ctx: ParserContext, accum: String, result: String): String = rest match {
        case Nil => result + accum 
        case x::xs => newLineLoop(rest.tail, ctx, accum + wordLoop(rest.head))
        
      }

      newLineLoop(s.split("\n"), LineContext, "", "")
    }*/

    def startWord: String => String = s => {
     val x = s.split(" ").filter(_.nonEmpty)
     x.head
    }

    def endWord: String => String = s => {
     val x = s.split(" ").reverse.filter(_.nonEmpty).reverse
     x.last
    }

    def lint(txt: String): String = {
      val cleanedForMultiSpace: String = txt.replaceAll(" +", " ")
      val cleanedForMultiNewLines: String = cleanedForMultiSpace.replaceAll("\n+", "\n")
      /*
      cleanedForMultiNewLines.split("\n").toList.map { l =>
        
        val w: String = startWord(l)

        w match {
          case "else" => "else"
          case "fi" => "fi"
          case _ => l
        }

        w + " " + l.split(" ").drop(1).tail.mkString(" ")

      }.mkString("\n")
      */
      // use folding to keep track of state 
      cleanedForMultiNewLines.split("\n").toList.map { l =>

        val w: String = startWord(l)

        w match {
          case "else" => "else"
          case "fi" => "fi"
          case _ => l
        }

        w + " " + l.split(" ").drop(1).tail.mkString(" ")

      }.mkString("\n")
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