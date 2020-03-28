
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Report free disk space.
    case class DfWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("df", args)
      def help = copy(args = self.args :+ "--help")
    }
    