
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class H2xsWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("h2xs", args)
      def help = copy(args = self.args :+ "--help")
    }
    