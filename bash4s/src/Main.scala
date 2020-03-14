package bash4s

//import scala.language.postfixOps
import experimental._

object Main {

  def main(args: Array[String]): Unit = {

    val script = du"ok" > du"hello" > du"goodbye"                   o
      du"ok" > du"hello" > du"goodbye" | du"pipeline"               o
      !(du"ok"  | du"pipeline"  | du"next").&                       o
      du"last" || du"last" || du"first" | du"end"                   o
      du"hello".&                                                   o
      du"one" & du"two" && du"three"                                o
      du"goodybe"

    pprint.pprintln(script)
  }

}