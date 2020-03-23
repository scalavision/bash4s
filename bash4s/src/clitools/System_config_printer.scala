
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class System_config_printerWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("system_config_printer", args)
      def help = copy(args = self.args :+ "--help")
    }
    