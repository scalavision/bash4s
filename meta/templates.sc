import $file.syntax
import syntax.SyntaxEnhancer._

case class Command(name: String, description: String)

def toCmdOp(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s() extends $ext"""
}

def toCmdOpValue(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s(value: String) extends $ext"""
}

def toAdtValue(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOpValue(ext)(s)).mkString("\n")}
  """
}

def toAdtOpClass(ext: String = "CommandOp"): String => String = s => {
  s"""final case class $s(op: CommandOp) extends $ext"""
}

def toAdtOpValue(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toAdtOpClass(ext)(s)).mkString("\n")}
  """
}

def toAdt(ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends CommandOp
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toAdtSuper(superTrait: String, ext: String, subClasses: List[String]) = {
  s"""
  sealed trait $ext extends $superTrait
  ${subClasses.map(s => toCmdOp(ext)(s)).mkString("\n")}
  """
}

def toOpDef(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1}(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}())  ++ decomposeOnion(op))"""
}

def toOpDefWithNewLineTerminator(fnMeta: (String, String)) = {
  s"""def ${fnMeta._1}^(op: CommandOp) = self.copy(acc = (acc :+ ${fnMeta._2}() :+ NewLine()) ++ decomposeOnion(op))"""
}

def toOpDefEmpty(fnMeta: (String, String)) = {
    s"""def ${fnMeta._1} = self.copy(acc = acc :+ ${fnMeta._2}())"""
}

def emptyDefBuilder(fnMeta: (String, String)) =
  s"""def ${fnMeta._1} = ${fnMeta._2}()"""

def cliTool(tool: Command) =
  s"""|${if(tool.description.nonEmpty) s"//${tool.description}" else ""}
      |def ${tool.name}(args: Any*) =
      |  SimpleCommand("${tool.name}", CmdArgCtx(args.toVector, s))""".stripMargin
  
def cli(tools: List[Command]) = 
  s"""|implicit class CmdSyntax(s: StringContext) {
      |  
      |  def txt(args: Any*) =
      |    TextVariable(CmdArgCtx(args.toVector, s))
      |    
      |  def array(args: Any*) =
      |    ArrayVariable(CmdArgCtx(args.toVector, s))
      |
      |  def m(args: Any*) =
      |    ArithmeticExpression(CmdArgCtx(args.toVector, s))
      |
      |  def file(args: Any*): FilePath =
      |    FileConversions.convertToFilePath(s.s(args: _*))
      |
      |  def relFile(args: Any*): RelPath =
      |    FileConversions.convertToRelFilePath(s.s(args: _*))
      |
      |  def fileName(args: Any*): FileName =
      |    FileConversions.convertToFileName(s.s(args: _*))
      |
      |  def dirPath(args: Any*): FolderPath =
      |    FileConversions.convertToFolderPath(s.s(args: _*))
      |  
      |  ${tools.map(cliTool).mkString("\n  ")}
      |}""".stripMargin

def cliAlias(tool: Command) = 
    s"""
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    ${if(tool.description.nonEmpty) s"//${tool.description}" else ""}
    case class ${tool.name.capFirst}Wrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("${tool.name}", args)
      def help = copy(args = self.args :+ "--help")
    }
    """
    
