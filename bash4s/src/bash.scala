package bash
import domain._

package object bash {

  def Var(implicit name: sourcecode.Name) =
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  def End = END()
  def True = TRUE()
  def False = FALSE()

  def $(op: CommandOp) =
    ScriptBuilder(Vector(SubCommandStart(), op, SubCommandEnd()))

  def time(op: CommandOp) =
    ScriptBuilder(Vector(TimedPipeline(), op))

  def ls =
    ScriptBuilder(Vector(SimpleCommand("ls", CmdArgs(Vector.empty[String]))))

  implicit class CmdSyntax(s: StringContext) {
    def ls(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ls", CmdArgCtx(args.toVector, s))))
  }

}
