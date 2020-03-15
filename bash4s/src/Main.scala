package bash4s

import scala.language.postfixOps
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
      ! du"hello".&                                                 o
      du"one" & du"two" && ! du"three"                              o
      du"goodybe"                                                   o
      du"oki".$( du"sub" | du"hello" )                              o 
      du"now"                                                       o
      du"oki".$( du"sub" | du"hello" )                              o
      `[[` (du"hello" && du"yes").`]]` && 
        `[[` (du"goodbye").`]]` || du"good"                         o 
      While `[[` du"hello" `]]` Do {
        du"ls"
      } Done
      du"no"

    val testWhile = 
      While `[[` du"hello" `]]` Do {
        du"ls"
      } Done //< (du"from")
    
    val testUntil = 
      Until `[[` du"hello" `]]` Do {
        du"ls"
      } Done //< (du"from")
    
    val testIf = 
      If `[[` du"hello" `]]` Then {
        du"ls in if"
      } ElseIf `[[` (du"lovely").`]]` Then {
        du"ls in elif" o
        du"this is elif"
      } Else {
        du"this is else"
      } Fi
      du"It workd!"

    val testBashCond = 
      `[[` (du"hello").`]]` && `[[` (du"hello") `]]`

    pprint.pprintln(serializer.apply(script))
    pprint.pprintln(serializer.apply(testWhile))
    pprint.pprintln(serializer.apply(testBashCond))
    pprint.pprintln(serializer.apply(testIf))
    pprint.pprintln(serializer.apply(testUntil))

  }

}