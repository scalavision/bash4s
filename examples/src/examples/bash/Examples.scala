package examples.bash

import scala.language.postfixOps
//import domain._
import bash4s._

object Examples {
    val script = du"ok" > du"hello"                                 o
      du"in" < du"out" | du"ok"                                     o
      du"ok" | du"pipeline"                                         o
      !(du"ok"  | du"pipeline"  | du"next").&                       o
      !(du"ok"  `;` du"pipeline"  & du"next").&                     o
      du"last" || du"last" || du"first" | du"end"                   o
      ! du"hello".&                                                 o
      `[[` (! echo"yes").`]]` || `{`( 
        echo"inside context group"  o
        echo"goodnight"
      )`}`
      `[[` (! echo"yes").`]]` || `(`( 
        echo"inside context group"  o
        echo"goodnight"
      )`)`
      (du"one" & du"two" && ! du"three")                            o
      du"goodybe"                                                   o
      du"oki" %( du"sub" | du"hello" )                              o 
      du"now"                                                       o
      du"oki" %( du"sub" | du"hello" )                              o
      `[[` (du"hello" && du"yes").`]]` && 
        `[[` (du"goodbye").`]]` || du"good"                         o 
      While `[[` du"hello" `]]` Do {
        du"ls"
      } Done
      du"no"

    val testWhile = 
      While `[[` du"hello" `]]` Do {
        du"ls"
      } Done < (du"from") o
      echo"continue"

    val testUntil = 
      Until `[[` (du"hello").&& (du"no") `]]` Do {
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
    val MY_INDEX = Var

    val myVar = 
      MY_VAR `=` array"""Hello Goodbye "John the ultimate" """ o
      While `[[` MY_VAR.$ `]]` Do {
        echo"$MY_VAR"
      } Done
      echo""""hello world $MY_VAR"""" o
      echo("hello", "world", s"${MY_VAR.$.txt}")

    val forTest = 
      MY_VAR `=` array"one two three" o
      For(MY_INDEX).In(MY_VAR.$).Do {
        echo"iterating $MY_INDEX"
      } Done

    val subscript = echo %(echo"hello" %(echo"goodbye")) || echo"oki"

    val currentShellContextGroup = `{`( 
      echo"inside context group"  o
      echo"goodnight".`;`
    )`}`
    echo"next day"

    val hereStr1 = cat <<< txt"herestring" > echo"tofile"
    val hereStr2 = cat `<<END`(
      txt"""Some additional text"""
    ) o
    echo"end"

   val testFile = 
     echo"FielTest" o
     { 
       If `[[` -a(file"/path/to/SomeFile").&&( -b(file"/path/to/OtherFile") ) `]]` Then {
        echo"This is a file"
       } Elif `[[` ( -a(file"/path/to/OtherFile1").&&( -g(file"/path/to/OtherFile2")) ).`]]` Then {
        du"ls in elif" o
        du"this is elif"
       } Else {
        du"this is else"
      } Fi
     } o
     du"It workd!"

    myVar.print()
    script.print()
    testBashCond.print()
    testIf.print()
    testUntil.print()
    forTest.print()
    subscript.printRich()

    currentShellContextGroup.printRich()

    hereStr1.printRich()
    hereStr2.printRich()
    
    testWhile.printRich()

    testFile.printRich()

    val workDir = new scripts.WorkDir(dirPath"/hello/goodbye")

     workDir.op.printRich()

//    pprint.pprintln(testFile)

//    pprint.pprintln(testFile)

    //hereStr3.printRich()
    
/*
    val HelloWorld = echo"hello world is working"
    val HelloWorld2 = echo("hello", "world", "simple args")
    val exe = HelloWorld.run()
    println(exe)

    HelloWorld.save(os.pwd / "hello_world")
    HelloWorld2.save(os.pwd / "helloworld2")
    */


}