
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Keditfiletype5Wrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("keditfiletype5", args)
      def help = copy(args = self.args :+ "--help")
    }
    