
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class BzdiffWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("bzdiff", args)
      def help = copy(args = self.args :+ "--help")
    }
    