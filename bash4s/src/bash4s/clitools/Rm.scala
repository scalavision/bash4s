
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Remove directory entries.
    case class RmWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("rm", args)
      def help = copy(args = self.args :+ "--help")
    }
    