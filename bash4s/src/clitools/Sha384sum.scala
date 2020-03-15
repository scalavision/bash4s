
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Sha384sumWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("sha384sum", args)
      def help = copy(args = self.args :+ "--help")
    }
    