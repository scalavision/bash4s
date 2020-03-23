
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class FgconsoleWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("fgconsole", args)
      def help = copy(args = self.args :+ "--help")
    }
    