
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Xdg_user_dirs_updateWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("xdg_user_dirs_update", args)
      def help = copy(args = self.args :+ "--help")
    }
    