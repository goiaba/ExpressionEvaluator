package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.ast._
import scala.util.Try
import scala.util.Success
import scala.util.Failure

import scala.collection.mutable

/**
 * Created by bruno on 3/28/15.
 */

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
  val NULL = Cell(0)
}

object Evaluator {

  type Store = mutable.Map[String, LValue[Int]]

  private val store: Store = mutable.Map[String, LValue[Int]]()

  def storeAsString = "Map" + store.mkString("(",", ",")")

  def memory = store

  def evaluate(expr: Expr): Try[LValue[Int]] = { Try(evaluate(store)(expr)) }

  def evaluate(store: Store)(expr: Expr): LValue[Int] = expr match {
    case Constant(c)                                => Cell(c)
    case UMinus(r)                                  => Cell(-evaluate(store)(r).get)
    case Plus(l, r)                                 => Cell(evaluate(store)(l).get + evaluate(store)(r).get)
    case Minus(l, r)                                => Cell(evaluate(store)(l).get - evaluate(store)(r).get)
    case Times(l, r)                                => Cell(evaluate(store)(l).get * evaluate(store)(r).get)
    case Div(l, r)                                  => Cell(evaluate(store)(l).get / evaluate(store)(r).get)
    case Mod(l, r)                                  => Cell(evaluate(store)(l).get % evaluate(store)(r).get)
    case Identifier(s)                              => {
      val svalue = store.get(s)
      if (svalue.isDefined) Cell(svalue.get.get)
      else throw new NoSuchFieldException(s)
    }
    case Assignment(l,r)                            => l match {
      case Identifier(s) => {
        val lvalue = Try(evaluate(store)(l)).getOrElse(Cell(0))
        val rvalue = evaluate(store)(r)
        store(s) = lvalue.set(rvalue.get)
        store(s)
        Cell.NULL
      }
    }
    case Conditional(condExpr, ifBlock, elseBlock @ _*)  => {
      val cvalue = evaluate(store)(condExpr)
      if (cvalue.get != 0 /*true*/) {
        evaluate(store)(ifBlock)
      } else {
        elseBlock.foldLeft(Cell.NULL.asInstanceOf[LValue[Int]])((c: LValue[Int], s: Expr) => evaluate(store)(s))
      }
    }
    case Loop(condExpr, block)                      => {
      var cvalue = evaluate(store)(condExpr)
      while (cvalue.get != 0) {
        evaluate(store)(block)
        cvalue = evaluate(store)(condExpr)
      }
      Cell.NULL
    }
    case Block(exprs @ _*)                          =>
      exprs.foldLeft(Cell.NULL.asInstanceOf[LValue[Int]])((c: LValue[Int], s: Expr) => evaluate(store)(s))
  }

}
