package edu.luc.cs.laufer.cs473.expressions

import org.parboiled2._
import ast._

class ExprParser(val input: ParserInput) extends Parser {

  def InputLine = rule { WhiteSpace ~ Statement ~ EOI }

  /** expr ::= term { { "+" | "-" } term }* */
  def Expression = rule {
    Term ~ zeroOrMore(
      ws('+') ~ Term ~> (Plus(_: Expr, _))
    | ws('-') ~ Term ~> (Minus(_: Expr, _))
    )
  }

  /** term ::= factor { { "*" | "/" | "%" } factor }* */
  def Term = rule {
    Factor ~ zeroOrMore(
      ws('*') ~ Factor ~> (Times(_: Expr, _))
    | ws('/') ~ Factor ~> (Div(_: Expr, _))
    | ws('%') ~ Factor ~> (Mod(_: Expr, _))
    )
  }

  /** factor ::= ident | number | "+" factor | "-" factor | "(" expr ")" */
  def Factor: Rule1[Expr] = rule { Ident | Number | UnaryPlus | UnaryMinus | Parens }

  /** statement   ::= expression ";" | assignment | conditional | loop | block **/
  def Statement: Rule1[Expr] = rule { Expression ~ ws(';') | Assign | Block }

  /** assignment  ::= ident "=" expression ";" **/
  def Assign = rule {
    Ident ~ ws('=') ~ Expression ~ ws(';') ~> ((x,y) => Assignment(x: Expr, y))
  }

  /** conditional ::= "if" "(" expression ")" block [ "else" (block | conditional) ] */
//  def Conditional = rule {
//    ws(atomic("if")) ~ Parens ~ Block ~ optional(ws(atomic("else")) ~ (Block | Conditional))) ~>
//
//  }


  // rules to deal with variable names [a-zA-Z] [a-zA-Z0-9]*
  def Variable = rule { oneOrMore(CharPredicate.Alpha) ~ zeroOrMore(CharPredicate.AlphaNum) }

  def Ident = rule { capture(Variable) ~ WhiteSpace ~> ((s:String) => Identifier(s)) }

  // explicitly handle trailing whitespace
  def Number = rule { capture(Digits) ~ WhiteSpace ~> ((s: String) => Constant(s.toInt)) }

  def UnaryPlus = rule { ws('+') ~ Factor }

  def UnaryMinus = rule { ws('-') ~ Factor ~> (UMinus(_: Expr)) }

  def Parens = rule { ws('(') ~ Expression ~ ws(')') }

  /** block       ::= { statement* } */
  def Block: Rule1[Expr] = rule { ws('{') ~ zeroOrMore(Statement) ~ ws('}') ~> ((e: Seq[Expr]) => ast.Block(e:_*)) }

  def Digits = rule { oneOrMore(CharPredicate.Digit) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def ws(c: Char) = rule { c ~ WhiteSpace }
}
