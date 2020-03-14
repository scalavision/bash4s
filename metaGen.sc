import $file.meta.formatter, formatter._
import $file.meta.command_op, command_op._
import $file.meta.bash_dsl, bash_dsl._
import $file.meta.serializer
import $file.meta.meta_model

def generateDomain(dest: os.Path): Unit = {

  /*
  val domainPath = dest / "domain.scala"
  os.write.over(domainPath, Formatter.style(domain, domainPath))

  val bashPath = dest / "bash.scala"
  os.write.over(bashPath, Formatter.style(bashDsl, bashPath))
 
  serializer.generateSerializer(dest)
  val bashcliPath = dest / "clitools"
  createCommandToolClasses(bashcliPath)
*/

  val expPath = dest / "dsl.scala"
  os.write.over(expPath, Formatter.style(meta_model.template, expPath))
  serializer.generateSerializer(dest)
  serializer.generateSerializer(dest)
  
}

