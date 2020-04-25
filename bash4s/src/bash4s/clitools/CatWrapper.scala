
    package bash4s.clitools

    import bash4s.domain._
    import bash4s.BashCommandAdapter

    case class CatWrapper (
      args: CmdArgs = CmdArgs(Vector.empty[String])
    ) extends BashCommandAdapter { self =>
      def toCmd = SimpleCommand("cat", args)
      def help = copy(args = self.args :+ "--help")

      def <<< (txt: TextVariable) = 
        SimpleCommand("cat", HereString("<<<", txt.value))

      /* TODO: more advanced here doc requires a bit more massage
      def `<<END`(op: CommandOp)(txt: TextVariable) =
        SimpleCommand(
          name = "cat", 
          arg = HereDoc("<<END", txt.value),
          postCommands= Vector(op)
        )*/

      def `<<END`(txt: TextVariable) =
        SimpleCommand("cat", HereDoc("<<END", txt.value))

      def `<<-END`(txt: TextVariable) =
        SimpleCommand("cat", HereDoc("<<-END", txt.value))

    }
    