package bash4s.scripts

import bash4s.domain._

case class ScriptMeta(name: String, description: String, argOpt: List[ArgOpt]) {
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
  def $10(op: Option[CommandOp], paramName: String="") = BashCliOptArgVariable("10", op, paramName) ?

  //TODO: Add support for VarArgszz
}

case class ArgOpt(long: String, description: String, short: String = "")

// Some day, maybe implement something like this
// https://github.com/matejak/argbash
object ArgTemplate {

  def argHandler(argOpt: ArgOpt) = s"""
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

  def initToBlank(argOpt: ArgOpt)   = s"""${argOpt.long}="""
  
  def optionParser(
    args: List[ArgOpt]
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
