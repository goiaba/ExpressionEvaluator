package edu.luc.cs.laufer.cs473.expressions.ast

import scala.collection.Map

/** An initial algebra of arithmetic expressions. */
sealed trait Expr
case class Constant(value: Int) extends Expr
case class UMinus(expr: Expr) extends Expr
case class Plus(left: Expr, right: Expr) extends Expr
case class Minus(left: Expr, right: Expr) extends Expr
case class Times(left: Expr, right: Expr) extends Expr
case class Div(left: Expr, right: Expr) extends Expr
case class Mod(left: Expr, right: Expr) extends Expr

case class Identifier(variable: String) extends Expr
case class Assignment(right: Expr, left: Identifier*) extends Expr
case class Struct(struct: Map[String, Expr]) extends Expr
case class Select(root: Expr, selectors: Identifier*) extends Expr
case class Block(exprs: Expr*) extends Expr
case class Conditional(condExpr: Expr, ifBlock: Expr, elseBlock: Expr*) extends Expr
case class Loop(condExpr: Expr, block: Expr) extends Expr