import $file.meta.formatter, formatter._
import $file.meta.command_op, command_op._

def generateDomain(dest: os.Path): Unit = {

  val domainPath = dest / "domain.scala"
  os.write.over(domainPath, Formatter.style(domain, domainPath))

}

