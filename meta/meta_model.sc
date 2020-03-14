import $file.syntax, syntax._
import SyntaxEnhancer._

import $file.templates
val tmpl = templates

val symbolToName = Map(
  "|" -> "PipeWithStdOut",
  "|&" -> "PipeWithError",
  "||" -> "Or",
  "&&" -> "And",
  ">" -> "StdOut",
  "`2>`" -> "StdErr",
  ">>" -> "AppendStdOut",
  "&>" -> "StdOutWithStdErr",
  "&>>" -> "AppendStdOutWithSdErr",
  "<"  -> "StdIn",
  "`[[`" -> "OpenDoubleSquareBracket",
  "`]]`" -> "CloseDoubleSquareBracket",
  "`(`" -> "OpenSubShellEnv",
  "`)`" -> "CloseSubShellEnv",
  "`{`" -> "OpenCommandList",
  "`}`" -> "CloseCommandList",
  "!" -> "Negate",
  "&" -> "Amper",
  "`;`" -> "Semi",
  "`\\n`" -> "NewLine",
  "`2>&1`" -> "RedirectStdOutWithStdErr",
  "<&-" -> "CloseStdIn",
  ">&-" -> "CloseStdOut",
)

val symbolsInit = "`[[` `(` `{` Do Then Else ElseIf Until For While".list
val symbolsWithArg = "| |& || && > `2>` >> &> &>> < If".list
val symbolsNoArgWithArg = "! & `;` `\\n` Done Fi `2>&1` <&- >&- `]]` `)` `}`".list

val extract: String => String = s => symbolToName.get(s).getOrElse("C" + s) 
def toName(prefix: String): String => String = s => symbolToName.get(s).getOrElse(prefix + s) 

val args = (symbolsWithArg ++ symbolsInit).zip( symbolsWithArg.map(extract)  ++ symbolsInit.map(extract))
val argAndNoArg = symbolsNoArgWithArg.zip(symbolsNoArgWithArg.map(extract))

val allArgs = args ++ argAndNoArg
val allClasses = allArgs.map(_._2)

val commandOpFns = (allArgs.map(tmpl.toOpDef) ++ argAndNoArg.map(tmpl.toOpDefEmpty)).mkString("\n") 
val commandOpAdt = allClasses.map(tmpl.toCmdOp()(_)).mkString("\n")
val emptyBuilder = argAndNoArg.map(tmpl.emptyDefBuilder).mkString("\n")

// From here downwards building a model as close to bash as possible
val cmdRedirectionSymbols = "> `2>` >> &> &>> < `2>&1` <&- >&-".list
def redirectFn: ((String, String)) => String = {
  case (symbol,name) => 
    s"def ${symbol}(op: CommandOp) = copy(cmds = (self.cmds :+ ${name}()) :+ op )"
}

def terminateFn: ((String, String)) => String = {
  case (symbol,name) => 
    s"def ${symbol} = copy(cmds = self.cmds :+ ${name}())"
}

val cmdTerminatorSymbols = "& `;` `\\n`".list
val cmdTerminators = (cmdTerminatorSymbols.zip(cmdTerminatorSymbols.map(extract))).map(terminateFn).mkString("\n")

val commandListSymbols = "|| &&".list
val commandListClasses = tmpl.toAdt("CommandListOp", commandListSymbols.map(extract) ++ cmdTerminatorSymbols.map(extract))

val toCommandArgName = toName("CmdArg")
def redirectClasses = tmpl.toAdt("CommandRedirection", cmdRedirectionSymbols.map(toCommandArgName))
def redirectFunctions = (cmdRedirectionSymbols.zip(cmdRedirectionSymbols.map(toCommandArgName))).map(redirectFn).mkString("\n")

val pipeSymbols = "| |&".list
val pipelineClasses = tmpl.toAdtSuper("CommandListOp", "PipelineOp", pipeSymbols.map(extract))

val template = s"""|package bash4s
   |object experimental {
   |
   |  sealed abstract class CommandOp()
   |  final case class ScriptLine() extends CommandOp
   |  sealed trait CommandArg extends CommandOp
   |
   |  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
   |      extends CommandArg
   |
   |  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
   |    def :+(arg: String) = copy(args = self.args :+ arg)
   |  }
   |  final case class EmptyArg() extends CommandArg
   |
   |  ${redirectClasses}
   |  ${pipelineClasses}
   |  final case class Negate() extends PipelineOp
   |  
   |  final case class SimpleCommand(
   |    name: String, 
   |    arg: CommandArg, 
   |    cmds: Vector[CommandOp] = Vector.empty[CommandOp]
   |   ) extends PipelineOp { self => 
   |
   |    ${redirectFunctions}
   |
   |    ${cmdTerminators}
   |
   |   def & (cmdList: CommandListOp) =
   |     CommandListBuilder(Vector(self, Amper(), cmdList))
   |
   |    def o(op: CommandOp) = 
   |      ScriptBuilder(Vector(self, ScriptLine(), op))
   |
   |    def | (simpleCommand: SimpleCommand) =
   |      PipelineBuilder(Vector(self, PipeWithStdOut(), simpleCommand))
   |
   |    def |& (simpleCommand: SimpleCommand) =
   |      PipelineBuilder(Vector(self, PipeWithStdOut(), simpleCommand))
   |
   |   def || (pipelineOp: PipelineOp) =
   |     CommandListBuilder(Vector(self, Or(), pipelineOp))
   |
   |    def && (pipelineOp: PipelineOp) =
   |     CommandListBuilder(Vector(self, And(), pipelineOp))
   |  }
   |  
   |  final case class PipelineBuilder(cmds: Vector[CommandOp]) extends PipelineOp { self =>
   |
   |   def unary_! = self.copy(Negate() +: cmds) 
   |    
   |   def & = 
   |     CommandListBuilder(Vector(SubShellStart(), self, Amper(), SubShellEnd()))
   |
   |   def `;` = copy(cmds = self.cmds :+ Semi())
   |     CommandListBuilder(Vector(SubShellStart(), self, Semi(), SubShellEnd()))
   |
   |   def `\\n` =
   |     CommandListBuilder(Vector(SubShellStart(), self, NewLine(), SubShellEnd()))
   |
   |   def | (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)
   |
   |    def |& (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)
   |
   |   def || (pipelineOp: PipelineOp) =
   |     CommandListBuilder(Vector(self, Or(), pipelineOp))
   |
   |    def && (pipelineOp: PipelineOp) =
   |     CommandListBuilder(Vector(self, And(), pipelineOp))
   |  }
   |  
   |  ${commandListClasses}
   |
   |  final case class CommandListBuilder(cmds: Vector[CommandListOp]) extends CommandListOp { self =>
   |
   |   def unary_! = self.copy(Negate() +: cmds) 
   |
   |   def || (pipelineOp: PipelineOp) =
   |     copy(cmds = (self.cmds :+ Or()) :+ pipelineOp)
   |
   |    def && (pipelineOp: PipelineOp) =
   |     copy(cmds = (self.cmds :+ And()) :+ pipelineOp)
   | 
   |   def & (cmdList: CommandListOp) =
   |     copy(cmds = (self.cmds :+ Amper()) :+ cmdList)
   |
   |   def | (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)
   |
   |    def |& (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)
   |  }
   |  
   |  final case class SubShellStart() extends CommandListOp
   |  final case class SubShellEnd() extends CommandListOp
   |
   |  final case class ScriptBuilder(acc: Vector[CommandOp]) extends CommandOp { self => 
   |
   |    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
   |      op match {
   |        case ScriptBuilder(scripts) =>
   |          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
   |            acc ++ decomposeOnion(c)
   |          }
   |        case _ => Vector(op)
   |      }
   |    }
   |
   |    def o(op: CommandOp) = self.copy(acc = (acc :+ ScriptLine()) ++ decomposeOnion(op))
   |
   |   }
   |
   |  final case class SheBang(s: String) extends CommandOp
   |
   | implicit class CmdSyntax(s: StringContext) {
   |   def du(args: Any*) =
   |     SimpleCommand("du", CmdArgCtx(args.toVector, s))
   |   }
   |}
""".stripMargin

val templateOld = s"""|package bash4s
   |object experimental {
   |
   |  sealed abstract class CommandOp()
   |sealed trait CommandArg extends CommandOp
   |  final case class CmdArgCtx(args: Vector[Any], strCtx: StringContext)
   |      extends CommandArg
   |  final case class CmdArgs(args: Vector[String]) extends CommandArg { self =>
   |    def :+(arg: String) = copy(args = self.args :+ arg)
   |  }
   |  final case class SimpleCommand(name: String, args: CommandArg)
   |      extends CommandOp
   |  final case class SheBang(s: String) extends CommandOp
   |  ${commandOpAdt}
   |
   |  final case class ScriptBuilder(acc: Vector[CommandOp]) extends CommandOp { self => 
   |
   |    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
   |      op match {
   |        case ScriptBuilder(scripts) =>
   |          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
   |            acc ++ decomposeOnion(c)
   |          }
   |        case _ => Vector(op)
   |      }
   |    }
   |
   |    ${commandOpFns}
   |
   |    def o(op: CommandOp) = self.copy(acc = (acc :+ CNewLine()) ++ decomposeOnion(op))
   |
   |}
   |
   |def bash_env = ScriptBuilder(Vector(SheBang("/usr/bin/env bash")))
   |def o = CNewLine()
   |${emptyBuilder}
    implicit class CmdSyntax(s: StringContext) {

      def comm(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("comm", CmdArgCtx(args.toVector, s))))

      def realpath(args: Any*) =
        ScriptBuilder(
          Vector(SimpleCommand("realpath", CmdArgCtx(args.toVector, s)))
        )

      def test(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("test", CmdArgCtx(args.toVector, s))))

      def runcon(args: Any*) =
        ScriptBuilder(
          Vector(SimpleCommand("runcon", CmdArgCtx(args.toVector, s)))
        )

      def dircolors(args: Any*) =
        ScriptBuilder(
          Vector(SimpleCommand("dircolors", CmdArgCtx(args.toVector, s)))
        )

      def du(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("du", CmdArgCtx(args.toVector, s))))

      def uniq(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("uniq", CmdArgCtx(args.toVector, s))))

      def mknod(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("mknod", CmdArgCtx(args.toVector, s))))

      def rmdir(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("rmdir", CmdArgCtx(args.toVector, s))))

      def shred(args: Any*) =
        ScriptBuilder(Vector(SimpleCommand("shred", CmdArgCtx(args.toVector, s))))

      def sha256sum(args: Any*) =
        ScriptBuilder(
          Vector(SimpleCommand("sha256sum", CmdArgCtx(args.toVector, s)))
        )

      def dirname(args: Any*) =
        ScriptBuilder(
          Vector(SimpleCommand("dirname", CmdArgCtx(args.toVector, s)))
        )
    }
   }
|""".stripMargin

println(template)