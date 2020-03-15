
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class LinkWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("link", args)
      def help = copy(args = self.args :+ "--help")
    }
    