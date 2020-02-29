import $ivy.`org.scalameta::scalafmt-dynamic:2.4.2`
import $ivy.`org.scalameta::scalafmt-core:2.4.2`


import org.scalafmt.interfaces.Scalafmt

object Formatter {


  /* Not in use anymore, loaded from file instead ..
  val formatter = ScalafmtConfig(
  maxColumn = 90,
  continuationIndent = ContinuationIndent(2,2),
  danglingParentheses = true,
  optIn = OptIn(
    configStyleArguments = false
  ),
  align = Align (
      openParenCallSite = false,
      openParenDefnSite= false,
      //      mixedOwners = false,
      tokens = AlignToken.default,
      arrowEnumeratorGenerator = false,
      ifWhileOpenParen = true
  )
  )*/

  // The class loader creates a memory leak when used with sbt, its a known
  // bug in the jvm that does not garbage collect the allocation done
  // by getClassLoader, we need to try different workarounds here ...
  def style(sourceCode: String, file: os.Path, off: Boolean = false): String = {
    if(off)
        sourceCode
    else{
      var clazzLoader = this.getClass.getClassLoader
      val scalafmt = Scalafmt.create(clazzLoader)
      val configFile = os.pwd / ".scalafmt.conf"
      val config = configFile.wrapped
      val formattedSource = scalafmt.format(config, file.wrapped, sourceCode)
      clazzLoader.clearAssertionStatus()
      clazzLoader = null
      scalafmt.clear()
      formattedSource
    }
  }

  def uncapFirst: String => String = s =>  
    s"${s.head.toLower}${s.tail}"

}