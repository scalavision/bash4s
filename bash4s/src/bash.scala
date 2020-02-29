package bash

import domain._

package object bash {

 def Var(implicit name: sourcecode.Name) = 
  BashVariable(name.value, BEmpty())

  def #!() = ScriptBuilder(Vector())

  def end = END()

  implicit class CmdSyntax(s: StringContext)  {
     def ls(args: Any*) = 
       ScriptBuilder(Vector(SimpleCommand("ls", CmdArgCtx(args.toVector, s))))
  }

}