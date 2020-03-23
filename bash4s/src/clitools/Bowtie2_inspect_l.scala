
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Bowtie2_inspect_lWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("bowtie2_inspect_l", args)
      def help = copy(args = self.args :+ "--help")
    }
    