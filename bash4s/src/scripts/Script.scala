package bash4s.scripts

import bash4s.domain._

abstract class Script(implicit n: sourcecode.Name) {
  def name = n.value
  def cmdOp: CommandOp
  def txt = cmdOp.txt
  def run() = cmdOp.run(name)
  def param: ScriptMeta
  def script = cmdOp.txt
  // BashCliArgVariable(gen.$1, path)
  // ArgTemplate.optionParser(param.argOpt) + "\n" + 
  /*
  def scriptify = cmdOp match {
    case BashVariable(name, value, isExpanded) => ???
  }*/
}