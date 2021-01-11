package examples

import zio.test._
// import zio.test.Assertion._
object ExampleSpecRunner extends DefaultRunnableSpec {

  val wip = examples.bash.FileSyncSpec.suite1

  override def spec = wip

}
