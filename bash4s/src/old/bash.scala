package old.bash

import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}

object dsl {
  implicit def bashCommandAdapterToSimpleCommand
      : BashCommandAdapter => ScriptBuilder =
    cmd => ScriptBuilder(Vector(cmd.toCmd))
}

package object bash {

  def Var(implicit name: sourcecode.Name) =
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  def `/dev/stdin` = domain.`/dev/stdin`
  def `/dev/stdout` = domain.`/dev/stdout`
  def `/dev/stderr` = domain.`/dev/stderr`

  def `/dev/fd`(fileDescriptor: FileDescriptor) =
    domain.`/dev/fd`(fileDescriptor)
  def `/dev/tcp`(host: Host, port: Port) = domain.`/dev/tcp`(host, port)
  def `/dev/udp`(host: Host, port: Port) = domain.`/dev/udp`(host, port)
  def `/dev/null` = domain.`/dev/null`
  def `/dev/random` = domain.`/dev/random`

  def End = END()

  def Until(op: CommandOp) = LUntil(op)
  def For(op: CommandOp) = LFor(op)
  def While(op: CommandOp) = LWhile(op)
  def Do(op: CommandOp) = CDo(op)
  def Then(op: CommandOp) = CThen(op)
  def Else(op: CommandOp) = CElse(op)
  def `[[`(op: CommandOp) = ScriptBuilder(Vector(OpenSquareBracket(op)))
  def If = ScriptBuilder(Vector(CIf()))
  def Until = ScriptBuilder(Vector(CUntil()))
  def Elif = ScriptBuilder(Vector(CElif()))
//  def Done = ScriptBuilder(Vector(LDone()))
  def True = CTrue()
  def False = CFalse()

  case object - {
    def a(op: CommandOp) = ConditionalExpression("a", op)
  }

  def $(op: CommandOp) =
    ScriptBuilder(Vector(SubCommandStart(), op, SubCommandEnd()))

 // def * = RegexFileSearchApi()

  def time(op: CommandOp) =
    ScriptBuilder(Vector(TimedPipeline(), op))

//  def comm = clitools.CommWrapper()

}
