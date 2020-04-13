val wd = os.pwd

val data = os.read(wd / "op_domain.dat").lines.toList.filter(_.nonEmpty).map(_.trim()).head


sealed trait Ctx
case object Empty extends Ctx
case class OpenParam(name: String) extends Ctx
case class Param(name: String, args: Int) extends Ctx
case class Domain(name: String, args: Int) {

  def parens = args match {
    case -1 => "=> "
    case 0 => "() => "
    case _ => s"(${(1 to args).toList.zipWithIndex.map{ case(_, i) => s"op${i}@_"}.mkString(", ")}) =>"
  }

  def toCase(default: String = "???") = 
    if(name.startsWith("/dev"))
      s"case `${name}`${parens} $default"
    else
      s"case ${name}${parens} $default"

}

case class MetaInfo(name: String, nrOfParams: Int)

def loop(left: List[Char], accum: String, context: Ctx, result: List[Domain]): List[Domain] = left match {
  case Nil => result
  case x::xs => x match {
    case '(' => loop(xs, "", OpenParam(accum), result)
    case ')' => context match {
      case OpenParam(name) => loop(xs, "", Empty, result :+ Domain(name, 0))
      case Param(name, params) => loop(xs, "", Empty, result :+ Domain(name, params) )
      case _ => 
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
    case ',' => context match {
      case OpenParam(name) => loop(xs, "", context, result)
      case Param(name, args) => loop(xs, "", context, result)
      case Empty => 
        if(accum.isEmpty()) 
          loop(xs, "", Empty, result) 
        else
          loop(xs, "", Empty, result :+ Domain(accum, -1))
      case _ => 
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
    case '_' => context match {
      case OpenParam(name) => loop(xs, "", Param(name, 1), result)
      case Param(name, params) => loop(xs, "", Param(name, params + 1), result)
      case Empty => loop(xs, "", Empty, result)
      case _ =>
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
      
    }
    case ' ' => loop(xs, accum, context, result)
    case _ => context match {
      case Empty => loop(xs, accum + x.toString(), Empty, result)
      case _ => 
        pprint.pprintln(result) 
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
  }
}

val domain = pprint.pprintln(data.split(",").take(14))
val caseSetup = loop(data.toList, "", Empty, List.empty[Domain])
val parensOnly = caseSetup.filter(_.args > 0)

val recurs: Int => String = { i =>
      s"expand(${(1 to i).toList.zipWithIndex.map{ case(_, i) => s"op${i}"}.mkString(", ")}) "
}

def expand(name: String): Int => String = { i =>
  s"$name(${(1 to i).toList.zipWithIndex.map{ case(_, i) => s"expandAll(op${i})"}.mkString(", ")}) "
}

val defaultLoop = caseSetup.filter(_.args > 0).map(d => d.toCase(recurs(d.args)))

val expandAll = caseSetup.filter(_.args > 0).map(d => d.toCase(expand(d.name)(d.args)))

val template1 = 
s"""|def apply(script: Script) = script.op match {
    |  ${caseSetup.map(_.toCase()).mkString("\n  ")}
    |}
    |""".stripMargin

val template2 = 
s"""|def expand(op: CommandOp) = op match {
    |  ${parensOnly.map(_.toCase()).mkString("\n  ")}
    |  case a => a
    |}
    |""".stripMargin

val template3 =
s"""|def expand(op: CommandOp): CommandOp = op match {
    |  ${defaultLoop.mkString("\n  ")}
    |  case a => a
    |}
    |""".stripMargin
  
val template4 =
s"""|def expandAll(op: CommandOp): CommandOp = op match {
    |  ${expandAll.mkString("\n  ")}
    |  case a => a
    |}
    |""".stripMargin

os.write.over( wd / "expand_all_template.tmp", template4 )

/*
sealed trait Ctx
case object Empty extends Ctx
case class OpenParam(name: String) extends Ctx
case class Param(name: String, params: List[Param]) extends Ctx

case class Domain(name: String, params: List[Param])

case class MetaInfo(name: String, nrOfParams: Int)

def loop(left: List[Char], accum: String, context: Ctx, result: List[Domain]): List[Domain] = left match {
  case Nil => result
  case x::xs => x match {
    case '(' => loop(xs, "", OpenParam(accum), result)
    case ')' => context match {
      case OpenParam(name) => loop(xs, "", Empty, result :+ Domain(name, List(Param(name, List.empty[Param]))))
      case Param(name, params) => loop(xs, "", Empty, result :+ Domain(name, params :+ Param(name, List.empty[Param])))
      case _ => 
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
    case ',' => context match {
      case OpenParam(name) => loop(xs, "", Param(name, List.empty[Param]), result)
      case p: Param => loop(xs, "", p.copy(params = p.params :+ Param(p.name, p.params)), result)
      case Empty => 
        if(accum.isEmpty()) 
          loop(xs, "", Empty, result) 
        else
          loop(xs, "", Empty, result :+ Domain(accum, List.empty[Param]))
      case _ => 
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
    case '_' => context match {
      case OpenParam(name) => loop(xs, "", Param(name, List.empty[Param]), result)
      case Param(name, params) => loop(xs, "", Param(name, params), result)
      case Empty => loop(xs, "", Empty, result)
      case _ =>
        pprint.pprintln(result)
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
      
    }
    case ' ' => loop(xs, accum, context, result)
    case _ => context match {
      case Empty => loop(xs, accum + x.toString(), Empty, result)
      case _ => 
        pprint.pprintln(result) 
        throw new Exception(s"error occured in '$x': $accum \t $context \t ${left} ${result}")
    }
  }
}*/

