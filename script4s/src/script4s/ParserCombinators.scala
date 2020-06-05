package fprb

import scala.language.implicitConversions

/*
Adding ParserError as a type parameter, makes a Parser 
interface that can work for any representation of ParserError

Parser[+_] the interface works for any kind of Parser.
*/

trait Parsers[ParserError, Parser[+_]] { self =>
  
  def char(c: Char): Parser[Char]

//  def string(s: String): Parser[String]

  def or[A](s1: Parser[A], s2: Parser[A]): Parser[A]

  def run[A, ErrorHander <: ParserError](p: Parser[A])(input: String): Either[ParserError, A]

  /*
  def foldLeft[A](a: Parser[A])(f: A => Parser[A]) = {

  }*/
 


  def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]]

  def count[A](p: Parser[List[A]]): Parser[Int]

  def parseAs(s: String): Parser[Int] = {
   count(listOfN(s.length, char('a')))
  }

  def many[A](p: Parser[A]): Parser[List[A]]

  def map[A,B](a: Parser[A])(f: A => B): Parser[B]


  implicit def string(s: String): Parser[String]
  implicit def operators[A](p: Parser[A]) = ParserOps[A](p)
  implicit def asStringParser[A](a: A)(implicit f: A => Parser[String]) = ParserOps[String](f(a))

  def countAs = map(many(char('a')))(_.size)

  case class ParserOps[A](p: Parser[A]) {
    
    def |[B>:A](p2: Parser[B]): Parser[B] = self.or(p,p2)
    def or[B>:A](p2: => Parser[B]): Parser[B] = self.or(p,p2)
    def many[B>:A]: Parser[List[B]] = self.many(p)
    def map[B1>:A, B](f: B1 => B): Parser[B] = self.map(p)(f)
//    val numA: Parser[Int] = char('a') many map(p) { (a: List[Char]) => a.size }

    val numA: Parser[Int] = char('a').many.map((cs: List[Char]) => cs.size)
  }

}



trait Laws[+_] extends Parsers[Nothing, Laws] {

  // run(char(c))(c.toString) == Right(c)
  def law1 = run(listOfN(3, "ab" | "cad"))("ababcad") == Right("ababcad")

}