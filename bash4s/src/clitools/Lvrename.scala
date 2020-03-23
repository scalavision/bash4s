
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class LvrenameWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("lvrename", args)
      def help = copy(args = self.args :+ "--help")
    }
    