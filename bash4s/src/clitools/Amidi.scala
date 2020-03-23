
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class AmidiWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("amidi", args)
      def help = copy(args = self.args :+ "--help")
    }
    