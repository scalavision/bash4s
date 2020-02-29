package bash

//import scala.language.postfixOps
import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash._

object ScriptSpec extends DefaultRunnableSpec(
  suite("Bash Dsl")(
    test("Test a simpel script") {

      val myVar = Var

      val test = bash_#!                                o
        ls"-halt"                                       o
        myVar `=` "Hello World"                         o
        myVar `=` $( ls"-halt" | ls"two" || ls"three" ) o
        time (ls"-h") | ls"-halt"  !
        ls"end.txt" | ls"yepp!"                         o
        ls"one" & ls"two" & ls"three"                   o
        ls"one" && ls"two" && ls"three"                 o
        ls $(ls | ls || ls > ls)                         o 
        ls <( ls"start" | ls"end" )

      pprint.pprintln(test)
      assert(1, equalTo(1))

    }
  )
)
