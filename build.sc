import mill._, scalalib._
import $ivy.`com.lihaoyi::mill-contrib-bloop:0.8.0`
// import $file.metaGen

trait common extends ScalaModule {

  val stdOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-explaintypes",
    "-Yrangepos",
    "-feature",
    "-Xfuture",
    "-language:higherKinds",
    "-language:existentials",
    "-unchecked",
    "-Xlint:_,-type-parameter-shadow",
    "-Xsource:2.13",
    "-Xfatal-warnings",
    "-Ymacro-annotations",
    "-opt-warnings",                                                                                         
    "-Ywarn-extra-implicit",                                                                                 
    "-Ywarn-unused:_,imports",                                                                               
    "-Ywarn-unused:imports",                                                                                 
    "-opt:l:inline",                                                                                         
    "-opt-inline-from:<source>",
    "-feature",
    "-Ywarn-value-discard",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ypatmat-exhaust-depth", "off"
  )
  
  def scalaVersion = "2.13.3"
  
  def compileIvyDeps = Agg(
    ivy"com.github.ghik:silencer-plugin_2.13.2:1.7.0",
    ivy"org.typelevel:kind-projector_2.13.2:0.11.0",
    //ivy"org.typelevel::kind-projector:0.11.0",
    ivy"com.github.tomasmikula::pascal:0.3.5"
  )

  def scalacPluginIvyDeps = Agg(
    ivy"com.github.ghik:silencer-plugin_2.13.2:1.7.0",
    ivy"org.typelevel:kind-projector_2.13.1:0.11.0",
    ivy"com.github.tomasmikula::pascal:0.3.5"
  )

  val Http4sVersion = "0.21.1"
  val CirceVersion = "0.13.0"
  val DoobieVersion = "0.8.8"
  val ZioVersion = "1.0.0-RC21-1"
  val PureConfigVersion = "0.12.3"
  val H2Version = "1.4.199"
  val FlywayVersion = "6.0.0-beta2"

  def ivyDeps = Agg(
    ivy"org.scala-lang.modules::scala-collection-compat:2.1.4",
    ivy"com.lihaoyi::sourcecode:0.2.1",
    ivy"com.lihaoyi::pprint:0.5.8",
    ivy"com.lihaoyi::os-lib:0.6.3",
    ivy"dev.zio::zio:$ZioVersion",
    ivy"dev.zio::zio-streams:$ZioVersion",
    ivy"org.tpolecat::atto-core:0.7.2",
    ivy"com.propensive::magnolia:0.14.4",
    ivy"org.scalameta::scalafmt-dynamic:2.4.2",
    ivy"org.scalameta::scalafmt-core:2.4.2",
    ivy"eu.timepit::refined:0.9.12",
    ivy"org.scala-lang:scala-reflect:${scalaVersion}",
    ivy"org.scala-lang:scala-compiler:${scalaVersion}",
  )

  def compile = T {
    // clearing the screen
//    print("\u001b[2J")
    super.compile()
  }

  def scalacOptions = stdOptions

  object test extends Tests {
    def ivyDeps = Agg(
      ivy"dev.zio::zio-test:1.0.0-RC18-2",
      ivy"dev.zio::zio-test-sbt:1.0.0-RC18-2"
    )
    def testFrameworks = 
      Seq("zio.test.sbt.ZTestFramework")
  }

}

object meta extends common {

//  def metaPath = os.pwd / "meta"
//  def metaFiles = T.sources(metaPath)

/*
  def allMetaFiles = T {
    def isHiddenFile(path: os.Path) = path.last.startsWith(".")
    for {
      root <- metaFiles()
      path <- os.walk(root.path)
      if os.isFile(path) && path.last.endsWith(".meta") && !isHiddenFile(path)
    } yield PathRef(path)
  } 

  */
  
  lazy val genPath = {    
    val wd = os.pwd    
    wd / "bash4s" / "src"
  }    
  // We just write the sources directly in for now
  override def generatedSources = T {    
//    val metaFiles = allMetaFiles().map(_.path)    
//    val dir = T.ctx().dest    
    //metaGen.generateDomain(genPath)    
    Seq.empty[PathRef] //(PathRef(dir))    
  }
}

object bash4s extends common {
  def moduleDeps = Seq(meta)
}


object workflow extends common {
  def moduleDeps = Seq(bash4s)
}

object biology extends common {
  def moduleDeps = Seq(bash4s, workflow)
 }

object examples extends common {
  def moduleDeps = Seq(bash4s, workflow, biology)
}
