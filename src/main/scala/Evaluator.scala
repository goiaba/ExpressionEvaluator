package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.ast._
import scala.util.Try
import scala.util.Success
import scala.util.Failure

import scala.collection.mutable.{Map => MMap}

/**
 * Created by bruno on 3/28/15.
 */

trait Value[V] {
  def get: V
  def set(value: V): Value[V]
}

case class Num(var value: Int) extends Value[Int] {
  def get: Int = value
  def set(value: Int) = { this.value = value; this }
}

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

  type Store = MMap[String, LValue[Value[Int]]]

  private val store: Store = MMap[String, LValue[Value[Int]]]()

  def storeAsString = "Map" + store.mkString("(",", ",")")

  def memory = store

  def evaluate(expr: Expr): Try[Value[Int]] = { Try(evaluate(store)(expr)) }

  private def evaluate(store: Store)(expr: Expr): Value[Int] = expr match {
    case Constant(c)                                => Num(c)
    case UMinus(r)                                  => Num(-evaluate(store)(r).get)
    case Plus(l, r)                                 => Num(evaluate(store)(l).get + evaluate(store)(r).get)
    case Minus(l, r)                                => Num(evaluate(store)(l).get - evaluate(store)(r).get)
    case Times(l, r)                                => Num(evaluate(store)(l).get * evaluate(store)(r).get)
    case Div(l, r)                                  => Num(evaluate(store)(l).get / evaluate(store)(r).get)
    case Mod(l, r)                                  => Num(evaluate(store)(l).get % evaluate(store)(r).get)
    case Identifier(s)                              => {
      val svalue = store.get(s)
      if (svalue.isDefined) svalue.get.get
      else throw new NoSuchFieldException(s)
    }
    case Assignment(l,r)                            => {
      val lvalue = Try(evaluate(store)(l)).getOrElse(Num(0))
      val rvalue = evaluate(store)(r)
      store(l.variable) = Cell(lvalue.set(rvalue.get))
      Cell.NULL.get
    }
    case Conditional(condExpr, ifBlock, elseBlock @ _*)  => {
      val cvalue = evaluate(store)(condExpr)
      if (cvalue.get != 0 /*true*/) {
        evaluate(store)(ifBlock)
      } else {
        elseBlock.foldLeft(Cell.NULL.get.asInstanceOf[Value[Int]])((c: Value[Int], s: Expr) => evaluate(store)(s))
      }
    }
    case Loop(condExpr, block)                      => {
      var cvalue = evaluate(store)(condExpr)
      while (cvalue.get != 0) {
        evaluate(store)(block)
        cvalue = evaluate(store)(condExpr)
      }
      Cell.NULL.get
    }
    case Block(exprs @ _*)                          =>
      exprs.foldLeft(Cell.NULL.get.asInstanceOf[Value[Int]])((c: Value[Int], s: Expr) => evaluate(store)(s))
  }

}
