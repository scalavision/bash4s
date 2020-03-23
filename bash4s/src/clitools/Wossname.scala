
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class WossnameWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("wossname", args)
      def help = copy(args = self.args :+ "--help")
    }
    