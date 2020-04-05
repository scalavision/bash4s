
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Remove directories.
    case class RmdirWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("rmdir", args)
      def help = copy(args = self.args :+ "--help")
    }
    