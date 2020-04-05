
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Compare two files.
    case class CmpWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("cmp", args)
      def help = copy(args = self.args :+ "--help")
    }
    