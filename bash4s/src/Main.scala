package bash4s

import scala.language.postfixOps
import bash4s._

object Main {


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
      } Elif `[[` (du"lovely").`]]` Then {
        du"ls in elif" o
        du"this is elif"
      } Else {
        du"this is else"
      } Fi
      du"It workd!"

    val testBashCond = 
      `[[` (du"hello").`]]` && `[[` (du"hello").`]]` && echo"yes"
      
    val MY_VAR = Var

    val myVar = 
      MY_VAR `=` txt"Hello" o
      While `[[` MY_VAR.$ `]]` Do {
        echo"$MY_VAR"
      } Done
      echo"hello world $MY_VAR"

    myVar.print()

    script.print()
    testWhile.print()
    testBashCond.print()
    testIf.print()
    testUntil.print()

  }

}