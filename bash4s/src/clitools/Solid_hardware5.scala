
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Solid_hardware5Wrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("solid_hardware5", args)
      def help = copy(args = self.args :+ "--help")
    }
    