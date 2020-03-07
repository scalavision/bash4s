package bash

//import scala.language.postfixOps
import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash._
import dsl._

object ScriptSpec extends DefaultRunnableSpec(
  suite("Bash Dsl")(
    test("Test a simpel script") {

//      implicit def bashCommandAdapterToSimpleCommand: BashCommandAdapter => SimpleCommand = _.toCmd

      val myVar = Var
      val myFile = Var
      val myIterator = Var

      val scriptTest1 = bash_#!                         o
        ls"-halt"                                       o
        myVar `=` "Hello World"                         o
        myVar `=` $( ls"-halt" | ls"two" || ls"three" ) o
        myFile `=` "./out.txt"                          o
        time (ls"-h") | ls"-halt"                       !^
        ls"end.txt" | ls"'yepp!'"                       &^
        ls"one" & ls"two" & ls"three"                   o 
        ls"one" && ls"two" && ls"three"                 o
        ls %(ls | ls || ls > ls ) | ls"yes"             o 
        ls <( ls"start" | ls"end" ) | ls"nope"          o
        ls %( ls"start" | ls"end" ) | ls"nope"          o
        cat <( ls"start" | ls"end" ) | ls"nope"         o
        cat <( ls"start" | ls"end7" ) & ls"nope"        o
        du | ls"echo"                                   o
        du.help | ls"hello"                             o
        ls > myFile.$ >&(2,1)                           o
        ls > `/dev/stdin`                               o 
        For(myIterator).In(myVar.$).Do {
          du | ls"echo"
        }.Done                                          o
        ls | grep"hello"

      val scriptTest2 = 
        ScriptInspector.bashRefs(scriptTest1)
     
      val result1 = ScriptSerializer.gen[domain.CommandOp].apply(scriptTest1)
      val result2 = ScriptSerializer.gen[domain.CommandOp].apply(scriptTest2)
      
//      pprint.pprintln(scriptTest1)
      pprint.pprintln(scriptTest2)
      pprint.pprintln(result1)
      pprint.pprintln(result2)

      assert(1, equalTo(1))

    }
  )
)
