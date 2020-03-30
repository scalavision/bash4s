package bash4s.scripts

import bash4s.domain._

abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def cmdOp: CommandOp
  def txt = cmdOp.txt
  def run() = cmdOp.run(name)
  def gen: ScriptMeta
  def script = ArgTemplate.optionParser(gen.argOpt) + "\n" + cmdOp.txt
  def scriptify = cmdOp match {
    case BashVariable(name, value, isExpanded) => ???
  }
}