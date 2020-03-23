
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Systemd_stdio_bridgeWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("systemd_stdio_bridge", args)
      def help = copy(args = self.args :+ "--help")
    }
    