
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Dbus_update_activation_environmentWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("dbus_update_activation_environment", args)
      def help = copy(args = self.args :+ "--help")
    }
    