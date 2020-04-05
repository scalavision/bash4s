
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Change file ownership.
    case class ChownWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("chown", args)
      def help = copy(args = self.args :+ "--help")
    }
    