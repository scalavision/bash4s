
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Set or report file size limit.
    case class UlimitWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("ulimit", args)
      def help = copy(args = self.args :+ "--help")
    }
    