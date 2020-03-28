
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    //Print checksum and block or byte count of a file.
    case class SumWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("sum", args)
      def help = copy(args = self.args :+ "--help")
    }
    