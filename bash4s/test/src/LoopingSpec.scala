package bash

import zio.test.{assert, suite, test, DefaultRunnableSpec}
import zio.test.Assertion.equalTo
import zio.test.Assertion._
import scala.language.postfixOps

//import bash._
//import dsl._
//import domain._

object d1 {

  sealed trait Conds
  final case class CUntil() extends Conds
  final case class CDo() extends Conds
  final case class CCmd() extends Conds
  final case class CDone() extends Conds
  final case class CWhile() extends Conds
  final case class CThen() extends Conds
  final case class CFor() extends Conds
  final case class CTest() extends Conds
  final case class CTrue() extends Conds
  final case class CFalse() extends Conds
  final case class CIf() extends Conds
  final case class CFi() extends Conds
  final case class `C[[`() extends Conds
  final case class `C]]`() extends Conds

  final case class CondsBuilder(accu: Vector[Conds]) extends Conds {
    def Until(c: Conds) = copy(accu = accu :+ c)
    def Do(c: Conds)  = copy(accu = accu :+ c)
    def Done(c: Conds) = copy(accu = accu :+ c)
    def Done = copy(accu = accu :+ CDone())
    def While(c: Conds) =copy(accu = accu :+ c)
    def Then(c: Conds) = copy(accu = accu :+ c)
    def For(c: Conds) = copy(accu = accu :+ c)
    def If(c: Conds) = copy(accu = accu :+ c)
    def Fi(c: Conds) = copy(accu = accu :+ c)
    def `[[`(c: Conds) = copy(accu = accu :+ c)
    def `]]`(c: Conds) = copy(accu = accu :+ c)
  }

  def init = CondsBuilder(Vector.empty[Conds])

}

object d2 {
  import d1._
  def Test = CTest()
  def True = CTrue()
  def TFalse = CFalse()
  def Cmd = CCmd()
}

object LoopingSpec extends DefaultRunnableSpec(
  suite("Bash Dsl for Conditionals")(
    test("Test a simpel script") {
      import d1._
      import d2._

      init Until Test Do {
        Cmd 
      } Done
      /*
      For (Cmd) Do {
        Cmd
      } Done
      */

      assert(1, equalTo(1))
    }
  )
)