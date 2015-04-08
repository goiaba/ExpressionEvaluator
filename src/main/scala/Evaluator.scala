package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.ast._
import scala.util.Try
import scala.collection.mutable.{Map => MMap}

/**
 * Created by bruno on 3/28/15.
 */
trait Value
case class Num(var value: Int) extends Value
case class Ins(var value: MMap[String, LValue[Value]]) extends Value

/**
 * Something that can be used on the right-hand side of an assignment.
 */
trait RValue[T] {
  def get: T
}

/**
 * Something that can be used on the left-hand side of an assignment.
 */
trait LValue[T] extends RValue[T] {
  def set(value: T): LValue[T]
}

/**
 * A cell for storing a value.
 */
case class Cell[T](var value: T) extends LValue[T] {
  override def get = value
  override def set(value: T) = { this.value = value; this }
}

/**
 * A companion object defining a useful Cell instance.
 */
object Cell {
  val NULL = Cell(Num(0))
}

object Evaluator {

  type Store = MMap[String, LValue[Value]]

  private val store: Store = MMap[String, LValue[Value]]()

  def storeAsString = "Map" + store.mkString("(",", ",")")

  def memory = store.toMap

  def asNum(v: Value): Int = v match {
    case Num(value) => value
    case _ => throw new RuntimeException("Wrong type")
  }

  def evaluate(expr: Expr): Try[Value] = { Try(evaluate(store)(expr)) }

  def evaluate(store: Store)(expr: Expr): Value = expr match {
    case Constant(c)                                => Num(c)
    case UMinus(r)                                  => Num(-asNum(evaluate(store)(r)))
    case Plus(l, r)                                 => Num(asNum(evaluate(store)(l)) + asNum(evaluate(store)(r)))
    case Minus(l, r)                                => Num(asNum(evaluate(store)(l)) - asNum(evaluate(store)(r)))
    case Times(l, r)                                => Num(asNum(evaluate(store)(l)) * asNum(evaluate(store)(r)))
    case Div(l, r)                                  => Num(asNum(evaluate(store)(l)) / asNum(evaluate(store)(r)))
    case Mod(l, r)                                  => Num(asNum(evaluate(store)(l)) % asNum(evaluate(store)(r)))
    case Select(root, selectors @ _*)               => {
      selectors.foldLeft(evaluate(store)(root))((acc: Value, el: Identifier) =>
        acc match {
          case Ins(m) => m.get(el.variable).get.get
          case Num(v) => acc
        }
      )
    }
    case Struct(m)                                  => {
      Cell.NULL.get
    }
    case Identifier(s)                              => {
      val svalue = store.get(s)
      if (svalue.isDefined) svalue.get.get
      else throw new NoSuchFieldException(s)
    }
    case Assignment(r,l @ _*)                       => {
      val lvalue = Try(Cell(evaluate(store)(Select(l.head, l.tail:_*)))).getOrElse(Cell(Num(0)))
      val rvalue = evaluate(store)(r)

      val sel = l.dropRight(1).foldLeft(evaluate(store)(l.head))((acc: Value, el: Identifier) =>
        acc match {
          case Ins(m) => m.get(el.variable).get.get
          case Num(v) => throw new NoSuchFieldException(el.variable)
        }
      )
      sel match {
        case Ins(m) => m(l.last.variable) = lvalue.set(rvalue)
        case Num(v) => throw new NoSuchFieldException(l.last.variable)
      }
      Cell.NULL.get
    }
    case Conditional(condExpr, ifBlock, elseBlock @ _*)  => {
      val cvalue = evaluate(store)(condExpr)
      if (!Cell.NULL.equals(cvalue) /*true*/) {
        evaluate(store)(ifBlock)
      } else {
        elseBlock.foldLeft(Cell.NULL.asInstanceOf[Value])((c: Value, s: Expr) => evaluate(store)(s))
      }
    }
    case Loop(condExpr, block)                      => {
      var cvalue = evaluate(store)(condExpr)
      while (!Cell.NULL.equals(cvalue)) {
        evaluate(store)(block)
        cvalue = evaluate(store)(condExpr)
      }
      Cell.NULL.get
    }
    case Block(exprs @ _*)                          =>
      exprs.foldLeft(Cell.NULL.asInstanceOf[Value])((c: Value, s: Expr) => evaluate(store)(s))
  }

}
