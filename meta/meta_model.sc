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
  "$(" -> "OpenSubShellExp",
  "`)`" -> "CloseSubShellEnv",
  "`{`" -> "OpenCommandList",
  "`}`" -> "CloseCommandList",
  "!" -> "Negate",
  "&" -> "Amper",
  "$" -> "Dollar",
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
val commandListClasses = tmpl.toAdt(
  "CommandListOp", 
  commandListSymbols.map(extract) ++ cmdTerminatorSymbols.map(extract) ++ 
  List("`(`", "`)`", "$(" ).map(extract))

val toCommandArgName = toName("CmdArg")
def redirectClasses = tmpl.toAdt("CommandRedirection", cmdRedirectionSymbols.map(toCommandArgName))
def redirectFunctions = (cmdRedirectionSymbols.zip(cmdRedirectionSymbols.map(toCommandArgName))).map(redirectFn).mkString("\n")

val pipeSymbols = "| |&".list
val pipelineClasses = tmpl.toAdtSuper("CommandListOp", "PipelineOp", pipeSymbols.map(extract))

val leftOvers = "`{` For Done Fi `}` $".list
val leftOverClasses = (leftOvers.map(extract).map(tmpl.toCmdOp()(_))).mkString("\n") //tmpl.toAdt("CommandOp", )

val conditional_and_loop_template =
"""final case class CloseDoubleSquareBracket() extends CommandOp
  final case class OpenDoubleSquareBracket() extends CommandOp
  
  def `[[`(op: CommandOp) =
    CommandListBuilder(Vector(OpenDoubleSquareBracket(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  final case class CDo(op: CommandOp) extends CommandOp

  final case class CWhile(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(doCommand: CDo) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ doCommand
      )

    def Done =
      ScriptBuilder(Vector(self, CDone()))

    def Done(op: CommandOp) =
      ScriptBuilder(Vector(self, CDone(), op))
  }

  def Do(op: CommandOp) = CDo(op)

  case class CUntil(
    testCommands: Vector[CommandOp],
    conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(doCommand: CDo) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ doCommand
      )

    def Done =
      ScriptBuilder(Vector(self, CDone()))
    
    def Done(op: CommandOp) =
      ScriptBuilder(Vector(self, CDone(), op))
  }

  object Until {
    def `[[`(op: CommandOp) = CUntil(Vector(OpenDoubleSquareBracket(), op))
  }
  
  object While {
    def `[[`(op: CommandOp) = CWhile(Vector(OpenDoubleSquareBracket(), op))
  }

  final case class CThen(op: CommandOp) extends CommandOp
  final case class CElse(op: CommandOp) extends CommandOp
  final case class CElseIf(op: CommandOp) extends CommandOp

  def Then(op: CommandOp) = CThen(op)
  
  final case class CIf(
      testCommands: Vector[CommandOp],
      conseqCmds: Vector[CommandOp] = Vector.empty[CommandOp]
  ) extends CommandOp { self =>

    def `]]`(thenCommand: CThen) =
      copy(conseqCmds =
        self.conseqCmds :+ CloseDoubleSquareBracket() :+ thenCommand
      )

    def ElseIf(op: CommandListOp) = {
      copy(conseqCmds =
        self.conseqCmds :+ CElseIf(op)
      )
    }

    def Then(op: CommandOp) =
      copy(conseqCmds =
        self.conseqCmds :+ CThen(op)
      )

    def Else(op: CommandOp) =
      copy(conseqCmds = self.conseqCmds :+ CElse(op))

    // These are used for standalone CIf, or if script is started
    // with a CIf
    def Fi =
      ScriptBuilder(Vector(self, CFi()))

    def Fi(op: CommandOp) =
      ScriptBuilder(Vector(self, CFi(), op))

  }

  object If {
    def `[[`(op: CommandOp) = CIf(Vector(OpenDoubleSquareBracket(), op))
  }

  final case class ScriptBuilder(acc: Vector[CommandOp]) extends CommandOp {
    self =>

    def decomposeOnion(op: CommandOp): Vector[CommandOp] = {
      op match {
        case ScriptBuilder(scripts) =>
          scripts.foldLeft(Vector.empty[CommandOp]) { (acc, c) =>
            acc ++ decomposeOnion(c)
          }
        case _ => Vector(op)
      }
    }

    // These are used when the Conditional or Loop are used within a script
    def Done(op: CommandOp) =
      self.copy(acc = acc :+ CDone() :+ ScriptLine() :+ op)

    def Done =
      self.copy(acc = acc :+ CDone() :+ ScriptLine())

    def Fi(op: CommandOp) =
      self.copy(acc = acc :+ CFi() :+ ScriptLine() :+ op)

    def Fi =
      self.copy(acc = acc :+ CFi() :+ ScriptLine())

    def o(op: CommandOp) =
      self.copy(acc = (acc :+ ScriptLine()) ++ decomposeOnion(op))
  }"""
   
val template = s"""|package bash4s
   |object dsl {
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
   |  ${leftOverClasses}
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
   |   def unary_! = self.copy(cmds = Negate() +: self.cmds)
   |
   |   def $$(cmdList: CommandListOp) =
   |    copy(cmds = cmds :+ OpenSubShellExp() :+ cmdList :+ CloseSubShellEnv()) 
   |
   |   def & (cmdList: CommandListOp) =
   |     CommandListBuilder(Vector(self, Amper(), cmdList))
   |
   |   def `;` (cmdList: CommandListOp) =
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
   |   def unary_! = self.copy(cmds = Negate() +: self.cmds) 
   |    
   |   def & = 
   |     CommandListBuilder(Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), Amper() ))
   |
   |   def `;` = copy(cmds = self.cmds :+ Semi())
   |     CommandListBuilder(Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), Semi() ))
   |
   |   def `\\n` =
   |     CommandListBuilder(Vector(OpenSubShellEnv(), self, CloseSubShellEnv(), NewLine() ))
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
   |  final case class CommandListBuilder(cmds: Vector[CommandOp]) extends CommandListOp { self =>
   |
   |   def unary_! = self.copy(Negate() +: cmds) 
   |
   |   def || (cmdListOp: CommandListOp) =
   |     copy(cmds = (self.cmds :+ Or()) :+ cmdListOp)
   |
   |    def && (cmdListOp: CommandListOp) =
   |     copy(cmds = (self.cmds :+ And()) :+ cmdListOp)
   |
   |   def & = 
   |    self.copy(cmds = (OpenSubShellEnv() +: self.cmds) ++ Vector(CloseSubShellEnv(), Amper()))
   | 
   |   def & (cmdList: CommandListOp) =
   |     copy(cmds = (self.cmds :+ Amper()) :+ cmdList)
   |
   |   def `;` (cmdList: CommandListOp) =
   |     copy(cmds = (self.cmds :+ Semi()) :+ cmdList)
   |
   |   def | (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithStdOut()) :+ simpleCommand)
   |
   |    def |& (simpleCommand: SimpleCommand) =
   |     copy(cmds = (self.cmds :+ PipeWithError()) :+ simpleCommand)
   |
   |    def `]]` = copy(cmds = self.cmds :+ CloseDoubleSquareBracket())
   |
   |}
   |
   |  ${conditional_and_loop_template}  
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