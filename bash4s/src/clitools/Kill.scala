
    package bash.clitools

    import bash.domain._
    import bash.BashCommandAdapter

    case class KillWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("du", args)
      def help = copy(args = self.args :+ "--help")
    }
    