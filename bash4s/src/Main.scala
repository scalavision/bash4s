package bash4s

//import scala.language.postfixOps
import dsl._

object Main {

  val serializer = ScriptSerializer.gen[CommandOp]

  def main(args: Array[String]): Unit = {

    val script = du"ok" > du"hello"                                 o
      du"in" < du"out" | du"ok"                                     o
      du"ok" | du"pipeline"                                         o
      !(du"ok"  | du"pipeline"  | du"next").&                       o
      !(du"ok"  `;` du"pipeline"  & du"next").&                     o
      du"last" || du"last" || du"first" | du"end"                   o
      du"hello".&                                                   o
      du"one" & du"two" && du"three"                                o
      du"goodybe"                                                   o
      du"oki".$( du"sub" | du"hello" )                              o 
      du"now" o
      du"oki".$( du"sub" | du"hello" )  o
      `[[` (du"hello" && du"yes").`]]` && `[[` (du"goodbye").`]]` && du"good"

      //o
      //du"done"

    pprint.pprintln(serializer.apply(script))
  }

}