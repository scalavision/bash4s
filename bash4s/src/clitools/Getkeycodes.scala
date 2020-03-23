
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class GetkeycodesWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("getkeycodes", args)
      def help = copy(args = self.args :+ "--help")
    }
    