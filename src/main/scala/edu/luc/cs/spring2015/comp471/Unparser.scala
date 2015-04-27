package edu.luc.cs.spring2015.comp471

import scala.collection.mutable.Set

trait Attributes

class SynthesizedAttributes extends Attributes
object Parens extends SynthesizedAttributes
object Semicolon extends SynthesizedAttributes

class InheritedAttributes extends Attributes
object Indent extends InheritedAttributes
object StructAssign extends InheritedAttributes

/**
 * Created by bruno on 3/25/15.
 */
object Unparser {

  val ia = Set[Attributes](Semicolon, Parens, Indent)
  val sa = Set[Attributes](Semicolon, Parens, Indent)

  private def resetFlags = {
    ia.add(Semicolon); ia.add(Parens); ia.add(Indent)
    sa.add(Semicolon); sa.add(Parens); sa.add(Indent)
  }

  def unparse(e: Expr): String = {
    resetFlags
    toUnparsedString("")(e)
  }

  private def buildUnparsedBinaryExpr(prefix: String, l: Expr, op: String, r: Expr) =
    prefix + "(" + toUnparsedString("")(l) + op + toUnparsedString("")(r) + ")"

  private def toUnparsedString(prefix: String)(e: Expr): String = e match {
    case Identifier(s)    => prefix + s
    case Constant(c)      => prefix + c.toString
    case Assignment(r, l @_*) => {
      val sep = if (ia.contains(StructAssign)) " : " else " = "
      toUnparsedString(prefix)(Select(l.head, l.tail:_*)) + sep + toUnparsedString("")(r)
    }
    case UMinus(r)        => prefix + "-" + toUnparsedString("")(r)
    case Plus(l,r)        => buildUnparsedBinaryExpr(prefix, l, " + ", r)
    case Minus(l,r)       => buildUnparsedBinaryExpr(prefix, l, " - ", r)
    case Times(l,r)       => buildUnparsedBinaryExpr(prefix, l, " * ", r)
    case Div(l,r)         => buildUnparsedBinaryExpr(prefix, l, " / ", r)
    case Mod(l,r)         => buildUnparsedBinaryExpr(prefix, l, " % ", r)
    case Conditional(condition, ifBlock, elseBlock @ _*) => {
      val sb = new StringBuilder()
      if (ia.contains(Indent)) sb.append(prefix)
      sb.append("if (").append(toUnparsedString("")(condition)).append(") ")
      ia.remove(Indent)
      sb.append(toUnparsedString(prefix)(ifBlock))
      ia.add(Indent)
      elseBlock.foreach {
        (block: Expr) => {
          sb.append(" else ")
          ia.remove(Indent); sa.add(Semicolon)
          sb.append(toUnparsedString(prefix)(block))
          ia.add(Indent)
        }
      }
      sb.toString
    }
    case Loop(condition, block) => {
      val sb = new StringBuilder()
      sb.append(prefix)
      sb.append("while (").append(toUnparsedString("")(condition)).append(") ")
      ia.remove(Indent)
      sb.append(toUnparsedString(prefix)(block))
      ia.add(Indent)
      sb.toString
    }
    case Block(expressions @ _*) => {
      val sb = new StringBuilder()
      if (ia.contains(Indent)) sb.append(prefix)
      else ia.add(Indent)
      sb.append("{").append(EOL)
      expressions.foreach { (expr: Expr) =>
        {
          sb.append(toUnparsedString(prefix + U_INDENT)(expr))
          if (sa.contains(Semicolon)) sb.append(";")
          else sa.add(Semicolon)
          sb.append(EOL)
        }
      }
      sb.append(prefix + "}")
      sa.remove(Semicolon)
      sb.toString
    }
    case Select(root, selectors @ _*) =>
      prefix + toUnparsedString("")(root) + selectors.foldLeft("")((out: String, el: Identifier) => out + "." + toUnparsedString("")(el))
    case Struct(m) => {
      val b = new StringBuilder()

      b.append(prefix)
      b.append("{")
      val s = m.map {
        case (k, v) => {
          ia.add(StructAssign)
          toUnparsedString("")(Assignment(v, Identifier(k)))
        }
      }.mkString(", ")
      b.append(s)
      b.append("}")
      ia.remove(StructAssign)
      b.toString
    }
  }

  val EOL = scala.util.Properties.lineSeparator
  val U_INDENT = "  "

}
