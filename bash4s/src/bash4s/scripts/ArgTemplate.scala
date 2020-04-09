package bash4s.scripts

import bash4s.domain._

case class ScriptMeta(name: String, description: String, argOpt: List[ArgOptional]) {
  //def $1(op: CommandOp) = BashCliArgVariable(argOpt.head.long, op) 
  def $1(op: CommandOp) = BashCliArgVariable("1", op) 
  def $2(op: CommandOp) = BashCliArgVariable("2", op) 
  def $3(op: CommandOp) = BashCliArgVariable("3", op) 
  def $4(op: CommandOp) = BashCliArgVariable("4", op) 
  def $5(op: CommandOp) = BashCliArgVariable("5", op) 
  def $6(op: CommandOp) = BashCliArgVariable("6", op) 
  def $7(op: CommandOp) = BashCliArgVariable("7", op) 
  def $8(op: CommandOp) = BashCliArgVariable("8", op) 
  def $9(op: CommandOp) = BashCliArgVariable("9", op) 
  def $10(op: CommandOp) = BashCliArgVariable("10", op) 
  
  def $1(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("1", op, paramName) 
  def $2(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("2", op, paramName) 
  def $3(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("3", op, paramName) 
  def $4(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("4", op, paramName) 
  def $5(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("5", op, paramName) 
  def $6(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("6", op, paramName) 
  def $7(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("7", op, paramName) 
  def $8(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("8", op, paramName) 
  def $9(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("9", op, paramName) 
  def $10(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("10", op, paramName)
  
  def $1(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("1", op.toVector, paramName) 
  def $2(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("2", op.toVector, paramName) 
  def $3(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("3", op.toVector, paramName) 
  def $4(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("4", op.toVector, paramName) 
  def $5(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("5", op.toVector, paramName) 
  def $6(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("6", op.toVector, paramName) 
  def $7(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("7", op.toVector, paramName) 
  def $8(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("8", op.toVector, paramName) 
  def $9(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("9", op.toVector, paramName) 
  def $10(op: Seq[CommandOp], paramName: String	) = BashCliVecArgVariable("10", op.toVector, paramName)
  
  def $1(op: Seq[CommandOp]	) = BashCliVecArgVariable("1", op.toVector, "") 
  def $2(op: Seq[CommandOp]	) = BashCliVecArgVariable("2", op.toVector, "") 
  def $3(op: Seq[CommandOp]	) = BashCliVecArgVariable("3", op.toVector, "") 
  def $4(op: Seq[CommandOp]	) = BashCliVecArgVariable("4", op.toVector, "") 
  def $5(op: Seq[CommandOp]	) = BashCliVecArgVariable("5", op.toVector, "") 
  def $6(op: Seq[CommandOp]	) = BashCliVecArgVariable("6", op.toVector, "") 
  def $7(op: Seq[CommandOp]	) = BashCliVecArgVariable("7", op.toVector, "") 
  def $8(op: Seq[CommandOp]	) = BashCliVecArgVariable("8", op.toVector, "") 
  def $9(op: Seq[CommandOp]	) = BashCliVecArgVariable("9", op.toVector, "") 
  def $10(op: Seq[CommandOp]	) = BashCliVecArgVariable("10", op.toVector, "")

  def $1(op: Boolean, paramName: String) = BashCliFlagArgVariable("1", op, paramName) 
  def $2(op: Boolean, paramName: String) = BashCliFlagArgVariable("2", op, paramName) 
  def $3(op: Boolean, paramName: String) = BashCliFlagArgVariable("3", op, paramName) 
  def $4(op: Boolean, paramName: String) = BashCliFlagArgVariable("4", op, paramName) 
  def $5(op: Boolean, paramName: String) = BashCliFlagArgVariable("5", op, paramName) 
  def $6(op: Boolean, paramName: String) = BashCliFlagArgVariable("6", op, paramName) 
  def $7(op: Boolean, paramName: String) = BashCliFlagArgVariable("7", op, paramName) 
  def $8(op: Boolean, paramName: String) = BashCliFlagArgVariable("8", op, paramName) 
  def $9(op: Boolean, paramName: String) = BashCliFlagArgVariable("9", op, paramName) 
  def $10(op: Boolean, paramName: String) = BashCliFlagArgVariable("10", op, paramName)
  //TODO: Add support for VarArgszz
}

case class ArgOptional(long: String, description: String, short: String = "")

// Some day, maybe implement something like this
// https://github.com/matejak/argbash
object ArgTemplate {

  def argHandler(argOpt: ArgOptional) = s"""
    |-${argOpt.short}|--${argOpt.long})  # Takes an option argument; ensure it has been specified.
    |   if [ "$$2" ]; then
    |     ${argOpt.long}=$$2
    |     shift
    |   else
    |     die 'ERROR: "--${argOpt.long}" requires a non-empty option argument.'
    |   fi
    |   ;;
    |--${argOpt.long}=?*)
    |    ${argOpt.long}=$${1#*=} # Delete everything up to "=" and assign the remainder.
    |    ;;
    |--${argOpt.long}=)    # Handle the case of an empty --${argOpt.long}=
    |    die 'ERROR: "--${argOpt.long}" requires a non-empty option argument.'
    |    ;;
    """.stripMargin

  def initToBlank(argOpt: ArgOptional)   = s"""${argOpt.long}="""
  
  def optionParser(
    args: List[ArgOptional]
  ) = 
    s"""#!/usr/bin/env bash
    |die() {
    |  printf '%s\\n' "$$1" >&2
    |  exit 1
    |}
    |
    |# Initialize all the option variables.
    |# This ensures we are not contaminated by variables from the environment.
    |${args.map(initToBlank).mkString("\n")}
    |verbose=0
    |while :; do
    |    case $$1 in
    |        -h|-\\?|--help)
    |            show_help    # Display a usage synopsis.
    |            exit
    |            ;;
    |        ${args.map(argHandler).mkString("\n")}
    |        -v|--verbose)
    |            verbose=$$((verbose + 1))  # Each -v adds 1 to verbosity.
    |            ;;
    |        --)              # End of all options.
    |            shift
    |            break
    |            ;;
    |        -?*)
    |            printf 'WARN: Unknown option (ignored): %s\\n' "$$1" >&2
    |             ;;
    |        *)               # Default case: No more options, so break out of the loop.
    |            break
    |    esac
    |    shift
    |done
    |
    |# Rest of the program here.
    |# If there are input files (for example) that follow the options, they
    |# will remain in the "$$@" positional parameters.
    |""".stripMargin

}
