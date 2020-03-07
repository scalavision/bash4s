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

      val scriptTest1 = bash_#!                         o
        ls"-halt"                                       o
        myVar `=` "Hello World"                         o
        myVar `=` $( ls"-halt" | ls"two" || ls"three" ) o
        time (ls"-h") | ls"-halt"                       !^
        ls"end.txt" | ls"yepp!"                         &^
        ls"one" & ls"two" & ls"three"                   o 
        ls"one" && ls"two" && ls"three"                 o
        ls %(ls | ls || ls > ls ) | ls"yes"             o 
        ls <( ls"start" | ls"end" ) | ls"nope"          o
        ls %( ls"start" | ls"end" ) | ls"nope"          o
        cat <( ls"start" | ls"end" ) | ls"nope"         o
        cat <( ls"start" | ls"end7" ) & ls"nope"        o
        du | ls"echo"                                   o
        du.help | ls"hello"

      val scriptTest2 = 
        du.help.toCmd  
     
      val result = ScriptSerializer.gen[domain.CommandOp].apply(scriptTest1)

//      pprint.pprintln(scriptTest1)
      pprint.pprintln(scriptTest2)
      pprint.pprintln(result)

      assert(1, equalTo(1))

    }
  )
)
