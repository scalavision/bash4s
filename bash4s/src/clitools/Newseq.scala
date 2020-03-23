
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class NewseqWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("newseq", args)
      def help = copy(args = self.args :+ "--help")
    }
    