package bash4s

import domain._

sealed trait ParserContext

final case object LineCtx extends ParserContext
final case object IfCtx extends ParserContext
final case object ElIfCtx extends ParserContext
final case object ElseCtx extends ParserContext

// i.e. use a stack instead of List for the ParserContext
final case class LintData(text: String, ctx: List[ParserContext], indent: Int)
final case class Indenter(txt: String, indent: Int)

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

    def startWord: String => (String, String) = s => {
     val x = s.split(" ").filter(_.nonEmpty)
     (x.head, x.tail.mkString(" "))
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

        val w: (String,String) = startWord(l)

        w match {
          case ("else", w) => "else" + w
          case ("fi", w) => "fi" + w
          case _ => l.dropWhile(_ == ' ')
        }

      }.mkString("\n")
      
      */
      // use folding to keep track of state 
      cleanedForMultiNewLines.split("\n").toList.foldLeft(Indenter("", 0)) { (acc, l) =>

        val w: (String,String) = startWord(l)

        w match {
          case("if", w) => acc.copy(
            txt = acc.txt + "if" + w + "\n",
            indent = acc.indent + 2
          )
          case ("else", w) => acc.copy(
            txt = acc.txt + "else" + w + "\n",
          )
          case ("fi", w) => acc.copy(
            txt = acc.txt + "fi" + w + "\n",
            indent = acc.indent - 2
          )
          case _ => 
            acc.copy(
              txt = acc.txt + List.fill(acc.indent)(' ').mkString + l.dropWhile(_ == ' ') + "\n"
            )
        }

      }.txt
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