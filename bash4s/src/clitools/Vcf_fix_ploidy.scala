
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class Vcf_fix_ploidyWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("vcf_fix_ploidy", args)
      def help = copy(args = self.args :+ "--help")
    }
    