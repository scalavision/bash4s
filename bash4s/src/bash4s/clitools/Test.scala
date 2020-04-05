
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Evaluate expression.
    case class TestWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("test", args)
      def help = copy(args = self.args :+ "--help")
    }
    