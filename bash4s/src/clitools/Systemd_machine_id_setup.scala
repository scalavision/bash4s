
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Systemd_machine_id_setupWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("systemd_machine_id_setup", args)
      def help = copy(args = self.args :+ "--help")
    }
    