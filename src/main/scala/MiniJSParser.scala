package edu.luc.cs.laufer.cs473.expressions

import org.parboiled2._
import ast._
import scala.collection.mutable.{Map => MMap}

class MiniJSParser(val input: ParserInput) extends org.parboiled2.Parser {

  def InputLine = rule {
    WhiteSpace ~ Block ~ EOI |
      WhiteSpace ~ zeroOrMore(Statement) ~ EOI ~> ((e: Seq[Expr]) => ast.Block(e:_*)) }

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

  /** struct ::= "{" "}" | "{" field { "," field }* "}" */
  def Struct = rule {
    ws('{') ~ zeroOrMore(Field).separatedBy(ws(',')) ~ ws('}') ~> ((fields: Seq[Assignment]) => {
      val map = MMap[String, Expr]()
      fields.foreach((field: Assignment) => {
        map(field.left(0).variable) = field.right
      })
      ast.Struct(map.toMap)
    })
  }
  
  /** simpleFactor ::= ident | number | "+" factor | "-" factor | "(" expr ")" */
  def SimpleFactor: Rule1[Expr] = rule { Ident | Number | UnaryPlus | UnaryMinus | Parens | Struct }

  /** factor ::= simplefactor { "." ident }* */
  def Factor = rule {
    SimpleFactor ~ oneOrMore(ws('.') ~ Ident) ~> ((e: Expr, es: Seq[Identifier]) => Select(e, es:_*)) |
      SimpleFactor
  }

  /** statement ::= expr ";" | assignment | conditional | loop | block */
  def Statement = rule { Expression ~ ws(';') | Assign | Conditional | Loop | Block }

  /** field ::= ident ":" expr */
  def Field = rule { Ident ~ ws(':') ~ Expression ~> ((x: Identifier, y: Expr) => Assignment(y, x)) }

  /** assignment ::= ident { "." ident }* "=" expression ";" */
  def Assign = rule {
    oneOrMore(Ident).separatedBy(ws('.')) ~ ws('=') ~ Expression ~ ws(';') ~>
      ((le: Seq[Identifier], re: Expr) => Assignment(re, le:_*))
  }

  /** block ::= { statement* } */
  def Block: Rule1[Expr] = rule { ws('{') ~ zeroOrMore(Statement) ~ ws('}') ~> ((e: Seq[Expr]) => ast.Block(e:_*)) }

  /** conditional ::= "if" "(" expr ")" block [ "else" (block | conditional) ] */
  def Conditional: Rule1[Expr] = rule {
    ws("if") ~ Parens ~ Block ~ zeroOrMore(ws("else") ~ (Block | Conditional)) ~>
      ((a: Expr, b: Expr, c: Seq[Expr]) => ast.Conditional(a, b, c:_*))
  }

  /** loop ::= "while" "(" expr ")" block */
  def Loop = rule { ws("while") ~ Parens ~ Block ~> (ast.Loop(_: Expr, _)) }

  // rules to deal with variable names [a-zA-Z] [a-zA-Z0-9]*
  def Variable = rule { oneOrMore(CharPredicate.Alpha) ~ zeroOrMore(CharPredicate.AlphaNum) }

  def Ident = rule { capture(Variable) ~ WhiteSpace ~> ((s:String) => Identifier(s)) }

  // explicitly handle trailing whitespace
  def Number = rule { capture(Digits) ~ WhiteSpace ~> ((s: String) => Constant(s.toInt)) }

  def UnaryPlus = rule { ws('+') ~ Factor }

  def UnaryMinus = rule { ws('-') ~ Factor ~> (UMinus(_: Expr)) }

  def Parens = rule { ws('(') ~ Expression ~ ws(')') }

  def Digits = rule { oneOrMore(CharPredicate.Digit) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def ws(c: Char) = rule { c ~ WhiteSpace }

  def ws(s: String) = rule { s ~ WhiteSpace }
}
