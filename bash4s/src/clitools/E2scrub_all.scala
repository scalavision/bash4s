
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class E2scrub_allWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("e2scrub_all", args)
      def help = copy(args = self.args :+ "--help")
    }
    