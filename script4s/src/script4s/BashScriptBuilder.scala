package fprb

import scala.language.implicitConversions

sealed trait CommandType
final case class SimpleCommand() extends CommandType

sealed trait Pipeline[A] extends CommandType
sealed trait PipeStdOut extends Pipeline[PipeStdOut]

trait ScriptBuilder[ScriptLine, Script[+_]] { self =>

  def pipe[A] (cmd1: Script[A], cmd2: Script[A]): Script[A]
  def pipeWStdErr[A] (cmd1: Script[A], cmd2: Script[A]): Script[A]
  def and[A] (cmd1: Script[A], cmd2: Script[A]): Script[A]
  def or[A] (cmd1: Script[A], cmd2: Script[A]): Script[A]
  def semi[A] (cmd1: Script[A], cmd2: Script[A]): Script[A]
  def newLine[A](cmd1: Script[A], cmd2: Script[A]): Script[A]

  def listOfLines[A](script: Script[A]): Script[Vector[A]]

  def bash[A](s: Script[A])(line: ScriptLine): Script[A]

  def map[A,B](a: Script[A])(f: A => B): Script[B]



  implicit def asStringCommand[A](a: A)(
    implicit f: A => Script[String]
  ): ScriptBuilderOps[String] = ScriptBuilderOps(f(a))

  case class ScriptBuilderOps[A](s: Script[A]) {

    def |[B>: A](s2: Script[B]): Script[B] = self.pipe(s, s2)
    def |&[B>: A](s2: Script[B]): Script[B] = self.pipeWStdErr(s, s2)
    def ||[B>: A](s2: Script[B]): Script[B] = self.or(s, s2)
    def &&[B>: A](s2: Script[B]): Script[B] = self.and(s, s2)
    def `;`[B>: A](s2: Script[B]): Script[B] = self.semi(s, s2)
    def `\n`[B>:A](s2: Script[B]): Script[B] = self.newLine(s, s2)
    
  }

}

/*
trait SingleCommand[A] {
  def cmd() = ???
}
trait Pipeline[Command, Pipe, PipeTerminator] {}
trait CommandList[Command, CommandListCombinator, CommandListTerminator] {}
*/
