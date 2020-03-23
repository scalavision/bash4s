
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class FatlabelWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("fatlabel", args)
      def help = copy(args = self.args :+ "--help")
    }
    