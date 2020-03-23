
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class GmenudbusmenuproxyWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("gmenudbusmenuproxy", args)
      def help = copy(args = self.args :+ "--help")
    }
    