
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Docker_credential_gcloudWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("docker_credential_gcloud", args)
      def help = copy(args = self.args :+ "--help")
    }
    