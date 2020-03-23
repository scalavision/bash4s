
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class System_config_printer_appletWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("system_config_printer_applet", args)
      def help = copy(args = self.args :+ "--help")
    }
    