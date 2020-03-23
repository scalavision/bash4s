
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Git_lfs_test_server_apiWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("git_lfs_test_server_api", args)
      def help = copy(args = self.args :+ "--help")
    }
    