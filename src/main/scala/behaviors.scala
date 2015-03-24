package edu.luc.cs.laufer.cs473.expressions

import ast._

import scala.collection.Iterator

object behaviors {

  def evaluate(e: Expr): Int = e match {
    case Constant(c)                                => c
    case UMinus(r)                                  => -evaluate(r)
    case Plus(l, r)                                 => evaluate(l) + evaluate(r)
    case Minus(l, r)                                => evaluate(l) - evaluate(r)
    case Times(l, r)                                => evaluate(l) * evaluate(r)
    case Div(l, r)                                  => evaluate(l) / evaluate(r)
    case Mod(l, r)                                  => evaluate(l) % evaluate(r)
    case Identifier(s)                              => -1
    case Assignment(l,r)                            => -1
    case Conditional(condExpr, ifBlock, elseBlock)  => -1
    case Loop(condExpr, block)                      => -1
    case Block(exprs @ _*)                          => -1
  }

  def size(e: Expr): Int = e match {
    case Constant(c)                                => 1
    case UMinus(r)                                  => 1 + size(r)
    case Plus(l, r)                                 => 1 + size(l) + size(r)
    case Minus(l, r)                                => 1 + size(l) + size(r)
    case Times(l, r)                                => 1 + size(l) + size(r)
    case Div(l, r)                                  => 1 + size(l) + size(r)
    case Mod(l, r)                                  => 1 + size(l) + size(r)
    case Identifier(s)                              => -1
    case Assignment(l,r)                            => -1
    case Conditional(condExpr, ifBlock, elseBlock)  => -1
    case Loop(condExpr, block)                      => -1
    case Block(exprs @ _*)                          => -1
  }

  def depth(e: Expr): Int = e match {
    case Constant(c) => 1
    case UMinus(r)   => 1 + depth(r)
    case Plus(l, r)  => 1 + math.max(depth(l), depth(r))
    case Minus(l, r) => 1 + math.max(depth(l), depth(r))
    case Times(l, r) => 1 + math.max(depth(l), depth(r))
    case Div(l, r)   => 1 + math.max(depth(l), depth(r))
    case Mod(l, r)   => 1 + math.max(depth(l), depth(r))
    case Identifier(s) => 1
    case Assignment(l,r) => 1 + math.max(depth(l), depth(r))
    case Conditional(condExpr, ifBlock, elseBlock @ _*) =>
      1 + math.max(depth(ifBlock), util.Try(elseBlock.map((expr: Expr) => depth(expr)).max).getOrElse(0))
    case Loop(condExpr, block) => 1 + depth(block)
    case Block(exprs @ _*) =>
      1 + util.Try(exprs.map((expr: Expr) => depth(expr)).max).getOrElse(0)
  }

  def toFormattedString(prefix: String)(e: Expr): String = e match {
    case Constant(c) => prefix + c.toString
    case UMinus(r)   => buildUnaryExprString(prefix, "UMinus", toFormattedString(prefix + INDENT)(r))
    case Plus(l, r)  => buildExprString(prefix, "Plus", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Minus(l, r) => buildExprString(prefix, "Minus", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Times(l, r) => buildExprString(prefix, "Times", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Div(l, r)   => buildExprString(prefix, "Div", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Mod(l, r)   => buildExprString(prefix, "Mod", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Identifier(s) => prefix + s
    case Assignment(l,r) => buildExprString(prefix, "Assignment", toFormattedString(prefix + INDENT)(l), toFormattedString(prefix + INDENT)(r))
    case Conditional(condExpr, ifBlock, elseBlocks @ _*) => buildConditionalString(prefix, "Conditional", condExpr, ifBlock, elseBlocks:_*)
    case Loop(condExpr, block) => buildExprString(prefix, "Loop", toFormattedString(prefix + INDENT)(condExpr), toFormattedString(prefix + INDENT)(block))
    case Block(exprs @ _*) => buildBlockString(prefix, "Block", exprs:_*)
  }

  def toFormattedString(e: Expr): String = toFormattedString("")(e)

  def toFormattedString(es: Seq[Expr]): String = {
    val result = new StringBuilder("")
    buildStringIfExists("", es.toIterator, result)
    result.toString
  }

  def unparse(e: Expr): String = toUnparsedString("")(e)

  def toUnparsedString(prefix: String)(e: Expr): String = e match {
    case Identifier(s) => prefix + s
    case Constant(c) => prefix + c
    case Assignment(l,r) => buildUnparsedBinaryExpr(prefix, "=", l, r, false)
    case UMinus(r) => buildUnparsedUnaryExpr(prefix, "-", r)
    case Plus(l,r) => buildUnparsedBinaryExpr(prefix, "+", l, r)
    case Minus(l,r) => buildUnparsedBinaryExpr(prefix, "-", l, r)
    case Times(l,r) => buildUnparsedBinaryExpr(prefix, "*", l, r)
    case Div(l,r) => buildUnparsedBinaryExpr(prefix, "/", l, r)
    case Mod(l,r) => buildUnparsedBinaryExpr(prefix, "%", l, r)
    case Conditional(condition, ifBlock, elseBlock @ _*) => buildUnparsedConditional(prefix, condition, ifBlock, elseBlock:_*)
    case Loop(condition, block) => buildUnparsedLoop(prefix, condition, block)
    case Block(exprs @ _*) => buildUnparsedBlock(prefix, exprs:_*)
    case _ => "{" + EOL + "}"
  }

  def buildUnparsedLoop(prefix: String, condition: Expr, block: Expr): String = {
    val result = new StringBuilder
    result.append(prefix)
    result.append("while (")
    result.append(toUnparsedString("")(condition))
    result.append(") ")
    result.append(toUnparsedString(prefix)(block).replaceAll("^\\s+", ""))
    result.toString()
  }

  def buildUnparsedConditional(prefix: String, condition: Expr, ifBlock: Expr, elseBlock: Expr*): String = {
    val result = new StringBuilder
    result.append(prefix)
    result.append("if ")
    result.append("(")
    result.append(toUnparsedString("")(condition))
    result.append(") ")
    result.append(toUnparsedString(prefix)(ifBlock).replaceAll("^\\s+", ""))
    result.append(" else")
    if (elseBlock.size > 0) {
      for (expr <- elseBlock) {
        result.append(toUnparsedString(prefix)(expr))
      }
    } else {
      result.append(" {")
      result.append(EOL)
      result.append(prefix + "}")
    }

    result.toString
  }

  def buildUnparsedUnaryExpr(prefix: String, operator: String, l: Expr): String = {
    val a = new StringBuilder()
    a.append(prefix)
    a.append(operator)
    a.append(toUnparsedString("")(l))
    a.toString
  }

  def buildUnparsedBinaryExpr(prefix: String, operator: String, l: Expr, r: Expr, parens: Boolean = true): String = {
    val a = new StringBuilder()
    a.append(prefix)
    if (parens) a.append("(")
    a.append(toUnparsedString("")(l))
    a.append(" ")
    a.append(operator)
    a.append(" ")
    a.append(toUnparsedString("")(r))
    if (parens) a.append(")")
    a.toString
  }

  def buildUnparsedBlock(prefix: String, exprs: Expr*): String = {
    val result = new StringBuilder
    result.append(prefix)
    result.append("{")
    for(expr <- exprs) {
      result.append(EOL)
      result.append(toUnparsedString(prefix + U_INDENT)(expr))
      result.append(";")
    }
    if (result.length() > 2 &&
      '}'.equals(result.charAt(result.length()-2)))
      result.deleteCharAt(result.length()-1)
    result.append(EOL)
    result.append(prefix)
    result.append("}")
    result.toString
  }

  def buildConditionalString(prefix: String, nodeString: String, condExpr: Expr, ifBlock: Expr, elseBlocks: Expr*) = {
    val result = new StringBuilder(prefix)
    result.append(nodeString)
    result.append("(")
    result.append(EOL)
    result.append(toFormattedString(prefix + INDENT)(condExpr))
    result.append(", ")
    result.append(EOL)
    result.append(toFormattedString(prefix + INDENT)(ifBlock))
    elseBlocks.foreach((block: Expr) => {
      result.append(",")
      result.append(EOL)
      result.append(toFormattedString(prefix + INDENT)(block))
    })
    result.append(")")
    result.toString
  }

  def buildBlockString(prefix: String, nodeString: String, exprs: Expr*) = {
    val result = new StringBuilder(prefix)
    result.append(nodeString)
    result.append("(")
    result.append(EOL)
    val exprsIterator = exprs.toIterator
    while (exprsIterator.hasNext) {
      val expr = exprsIterator.next();
      result.append(toFormattedString(prefix + INDENT)(expr));
      if (exprsIterator.hasNext)
        result.append(",").append(EOL)
    }
    result.append(")")
    result.toString
  }

  def buildStringIfExists(prefix: String, exprs: Iterator[Expr], sb: StringBuilder) = {
    while (exprs.hasNext) {
      val expr = exprs.next();
      sb.append(toFormattedString(prefix)(expr));
      if (exprs.hasNext)
        sb.append(",").append(EOL)
    }
  }

  def buildExprString(prefix: String, nodeString: String, leftString: String, rightString: String) = {
    val result = new StringBuilder(prefix)
    result.append(nodeString)
    result.append("(")
    result.append(EOL)
    result.append(leftString)
    result.append(", ")
    result.append(EOL)
    result.append(rightString)
    result.append(")")
    result.toString
  }

  def buildUnaryExprString(prefix: String, nodeString: String, exprString: String) = {
    val result = new StringBuilder(prefix)
    result.append(nodeString)
    result.append("(")
    result.append(EOL)
    result.append(exprString)
    result.append(")")
    result.toString
  }

  val EOL = scala.util.Properties.lineSeparator
  val INDENT = ".."
  val U_INDENT = "  "
}
