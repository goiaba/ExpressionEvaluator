package edu.luc.cs.spring2015.comp471

import scala.collection.Map

object behaviors {

  def depth(e: Expr): Int = e match {
    case Constant(c)                        => 1
    case UMinus(r)                          => 1 + depth(r)
    case Plus(l, r)                         => 1 + math.max(depth(l), depth(r))
    case Minus(l, r)                        => 1 + math.max(depth(l), depth(r))
    case Times(l, r)                        => 1 + math.max(depth(l), depth(r))
    case Div(l, r)                          => 1 + math.max(depth(l), depth(r))
    case Mod(l, r)                          => 1 + math.max(depth(l), depth(r))
    case Identifier(s)                      => 1
    case Assignment(l,r)                    => 1 + math.max(depth(l), depth(r))
    case Conditional(cnd, ifB, elseBs @ _*) => 1 + math.max(depth(ifB), util.Try(elseBs.map((expr: Expr) => depth(expr)).max).getOrElse(0))
    case Loop(condExpr, block)              => 1 + depth(block)
    case Block(exprs @ _*)                  => 1 + util.Try(exprs.map((expr: Expr) => depth(expr)).max).getOrElse(0)
    case Select(root, selectors @ _*)       => 1
    case Struct(m)                          => 1 + util.Try(m.map { case (k, v) => depth(v) }.max).getOrElse(0)
  }

  def toFormattedString(prefix: String)(e: Expr): String = e match {
    case Constant(c) =>
      prefix + c.toString
    case Identifier(s)                      => prefix + s
    case UMinus(r)                          => buildUnaryExprString(prefix, "UMinus", toFormattedString(prefix + INDENT)(r))
    case Plus(l, r)                         => buildExprString(prefix, "Plus", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Minus(l, r)                        => buildExprString(prefix, "Minus", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Times(l, r)                        => buildExprString(prefix, "Times", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Div(l, r)                          => buildExprString(prefix, "Div", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Mod(l, r)                          => buildExprString(prefix, "Mod", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Assignment(r,l @ _*)               => l match {
      case head +: Nil  => buildExprString(prefix, "Assignment", toFormattedString(prefix + INDENT)(head), toFormattedString(prefix + INDENT)(r))
      case head +: tail => buildExprString(prefix, "Assignment", toFormattedString(prefix + INDENT)(Select(head, tail:_*)), toFormattedString(prefix + INDENT)(r))
    }
    case Loop(condExpr, block)              => buildExprString(prefix, "Loop", toFormattedString(prefix + INDENT)(condExpr), toFormattedString(prefix + INDENT)(block))
    case Block(exprs @ _*)                  => buildBlockString(prefix, "Block", exprs:_*)
    case Conditional(cnd, ifB, elseBs @ _*) => buildConditionalString(prefix, "Conditional", cnd, ifB, elseBs:_*)
    case Select(root, selectors @ _*)       => buildSelectorString(prefix, "Select", root, selectors:_*)
    case Struct(m)                          => buildStructString(prefix, "Struct", m)
  }

  def toFormattedString(e: Expr): String = toFormattedString("")(e)

  def buildStructString(prefix: String, nodeString: String, fields: Map[String, Expr]) = {
    val result = new StringBuilder(prefix).append(nodeString).append("(").append(EOL)
    val fieldsString = fields.map {
      case (k, v) => toFormattedString(prefix + INDENT)(Assignment(v, Identifier(k)))
    }.mkString("," + EOL)
    result.append(fieldsString)
    result.append(")")
    result.toString
  }

  def buildSelectorString(prefix: String, nodeString: String, root: Expr, selectors: Identifier*) = {
    val result = new StringBuilder()
    result.append(toFormattedString(prefix)(root))
    result.append(".")
    result.append(selectors.map((el: Identifier) => toFormattedString("")(el)).mkString("."))
    result.toString
  }

  def buildConditionalString(prefix: String, nodeString: String, cnd: Expr, ifB: Expr, elseBs: Expr*) = {
    val result = new StringBuilder(prefix).append(nodeString).append("(").append(EOL)
    result.append(toFormattedString(prefix + INDENT)(cnd))
    result.append(", ")
    result.append(EOL)
    result.append(toFormattedString(prefix + INDENT)(ifB))
    elseBs.foreach((block: Expr) => {
      result.append(",")
      result.append(EOL)
      result.append(toFormattedString(prefix + INDENT)(block))
    })
    result.append(")")
    result.toString
  }

  def buildBlockString(prefix: String, nodeString: String, exprs: Expr*) = {
    val result = new StringBuilder(prefix).append(nodeString).append("(").append(EOL)
    result.append(exprs.map(expr => toFormattedString(prefix + INDENT)(expr)).mkString("," + EOL))
    result.append(")")
    result.toString
  }

  def buildExprString(prefix: String, nodeString: String, leftString: String, rightString: String) = {
    val result = new StringBuilder(prefix).append(nodeString).append("(").append(EOL)
    result.append(leftString)
    result.append(", ")
    result.append(EOL)
    result.append(rightString)
    result.append(")")
    result.toString
  }

  def buildUnaryExprString(prefix: String, nodeString: String, exprString: String) = {
    val result = new StringBuilder(prefix).append(nodeString).append("(").append(EOL)
    result.append(exprString)
    result.append(")")
    result.toString
  }

  val EOL = scala.util.Properties.lineSeparator
  val INDENT = ".."
  val U_INDENT = "  "
}
