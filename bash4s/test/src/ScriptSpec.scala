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

      val test = #!()              o
        ls"-halt"                  o
        myVar `=` "Hello World"    o
        myVar =&( ls"-halt" )      o
        time (ls"-h") | ls"-halt"  !
        ls"end.txt" | ls"yepp!"

      pprint.pprintln(test)

      assert(1, equalTo(1))
    }
  )
)
/*

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) => 
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => op
      }
    }
*/