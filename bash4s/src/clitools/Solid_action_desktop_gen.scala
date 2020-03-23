
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Solid_action_desktop_genWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("solid_action_desktop_gen", args)
      def help = copy(args = self.args :+ "--help")
    }
    