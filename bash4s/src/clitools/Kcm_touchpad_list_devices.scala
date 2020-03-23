
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Kcm_touchpad_list_devicesWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("kcm_touchpad_list_devices", args)
      def help = copy(args = self.args :+ "--help")
    }
    