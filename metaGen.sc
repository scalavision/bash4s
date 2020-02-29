import $file.meta.formatter, formatter._
import $file.meta.command_op, command_op._
import $file.meta.bash_dsl, bash_dsl._

def generateDomain(dest: os.Path): Unit = {

  val domainPath = dest / "domain.scala"
  os.write.over(domainPath, Formatter.style(domain, domainPath))

  val bashPath = dest / "bash.scala"
  os.write.over(bashPath, Formatter.style(bashDsl, bashPath))

}

