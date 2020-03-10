package bash

//import scala.language.postfixOps
import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._

import bash._
//import dsl._
import domain._

object ConditionalSpec extends DefaultRunnableSpec(
  suite("Bash Dsl for Conditionals")(
    test("Test a simpel script") {
 
      val myF = 
        FilePath('/', 
        FolderPath(Vector("folder","path")), 
        FileName(BaseName("hello"), FileExtension(Vector("txt"))))

      val result = -a(myF) && -a(myF)
      pprint.pprintln(result) 

      assert(1, equalTo(1))
    }
  )
)