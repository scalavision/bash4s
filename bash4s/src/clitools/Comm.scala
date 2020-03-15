
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class CommWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("comm", args)
      def help = copy(args = self.args :+ "--help")
    }
    