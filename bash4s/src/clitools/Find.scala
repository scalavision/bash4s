
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Find files.
    case class FindWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("find", args)
      def help = copy(args = self.args :+ "--help")
    }
    