
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class B2sumWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("b2sum", args)
      def help = copy(args = self.args :+ "--help")
    }
    