package edu.luc.cs.spring2015.comp471

import scala.collection.mutable.{Map => MMap}
import scala.util.{Failure, Success, Try}

/**
 * Created by bruno on 3/28/15.
 */

/**
 * Exception class with the only aim of distinguish between a field that
 *  does not exists and a selector that does not exists. It is not
 *  mandatory. NoSuchFieldException could be used instead.
 * @param msg
 */
class UndefinedSelectorException(msg: String) extends RuntimeException(msg)

object Evaluator {

  type Store = MMap[String, Cell[Value]]

  trait Value
  case class Num(value: Int) extends Value
  case class Ins(value: Store) extends Value

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
  case class Cell[T <: Value](var value: T) extends LValue[T] {
    override def get = value
    override def set(value: T) = { this.value = value; this }
  }

  /**
   * A companion object defining a useful Cell instance.
   */
  object Cell {
    val NULL = Cell(Num(0)).asInstanceOf[Cell[Value]]
  }

  private val store: Store = MMap[String, Cell[Value]]()

  def memoryAsString = store.mkString("Map(",", ",")")

  def memory = store.toMap

  def clearMemory = store.clear()

  def evaluate(expr: Expr): Try[Value] = { Try(evaluate(store)(expr).get) }

  private def evaluate(store: Store)(expr: Expr): Cell[Value] = expr match {
    case Constant(c)                                =>
      Cell(Num(c))
    case UMinus(r)                                  =>
      Cell(Num(-getNumValue(evaluate(store)(r))))
    case Plus(l, r)                                 =>
      Cell(Num(getNumValue(evaluate(store)(l)) + getNumValue(evaluate(store)(r))))
    case Minus(l, r)                                =>
      Cell(Num(getNumValue(evaluate(store)(l)) - getNumValue(evaluate(store)(r))))
    case Times(l, r)                                =>
      Cell(Num(getNumValue(evaluate(store)(l)) * getNumValue(evaluate(store)(r))))
    case Div(l, r)                                  =>
      Cell(Num(getNumValue(evaluate(store)(l)) / getNumValue(evaluate(store)(r))))
    case Mod(l, r)                                  =>
      Cell(Num(getNumValue(evaluate(store)(l)) % getNumValue(evaluate(store)(r))))
    case Select(root, selectors @ _*)               =>
      //Using foldLeft to deep into the Cell's of each selector.
      //The idea here is iterate over the list of selectors and return the Cell
      //to which the last identifier of the list points to. The match inside the
      //fold left is over the acc. It works because a Num must always be the
      //leaf, the last element of the list of selectors. Otherwise, we would be
      //looking for a selector inside a Num and that makes no sense.
      selectors.foldLeft(evaluate(store)(root))((acc: Cell[Value], el: Identifier) =>
        acc.get match {
          case Ins(m) => Try(m(el.variable)).getOrElse(throw new UndefinedSelectorException(el.variable))
          case Num(v) => throw new UndefinedSelectorException(el.variable)
        }
      )
    case Struct(m)                                  =>
     m.foldLeft(Cell(Ins(MMap[String, Cell[Value]]())))((acc, kv) => {
        acc.get.value(kv._1) = evaluate(acc.get.value)(kv._2)
        acc
      }).asInstanceOf[Cell[Value]]
    case Identifier(s)                              => {
      val svalue = store.get(s)
      if (svalue.isDefined) svalue.get
      else throw new NoSuchFieldException(s)
    }
    case Assignment(r,l @ _*)                       => {
      //Evaluates the right side of the assignment. This will return a Cell[Value] and we must
      // set the Value into the left side of the assignment expression
      val rvalue = evaluate(store)(r)
      //Here we delegate the task of evaluating the possible Select (it may also be a simple Identifier,
      // but the Select branch know how to deal with both cases) to the Select branch. The select branch
      // starts calling recursively the evaluate method passing as argument the 'root' identifier. This
      // call may result in an exception.
      Try(evaluate(store)(Select(l.head, l.tail.dropRight(1):_*))) match {
        case Success(cell) =>
          //Simple identifier (root).
          if (l.tail.isEmpty) cell.set(rvalue.get)
           //Selection.
          else cell.get match {
            //In this case must store the assigned value inside of an structure (map)
            case Ins(m) => m(l.last.variable) = rvalue
            //Otherwise we just ignore the value and set the rvalue into the cell
            case Num(v) => cell.set(Ins(MMap[String, Cell[Value]](l.last.variable -> rvalue)))
          }
        case Failure(ex: NoSuchFieldException) =>
          if (l.tail.isEmpty) store(l.head.variable) = Cell(rvalue.get)
          else throw ex
        case Failure(ex) => throw ex
      }
      Cell.NULL
    }
    case Conditional(cnd, ifBlock, elseBlock @ _*)  => {
      val cvalue = evaluate(store)(cnd)
      if (!Cell.NULL.equals(cvalue) /*true*/) {
        evaluate(store)(ifBlock)
      } else {
        elseBlock.foldLeft(Cell.NULL)((c: Cell[Value], s: Expr) => evaluate(store)(s))
      }
    }
    case Loop(condExpr, block)                      => {
      var cvalue = evaluate(store)(condExpr)
      while (!Cell.NULL.equals(cvalue)) {
        evaluate(store)(block)
        cvalue = evaluate(store)(condExpr)
      }
      Cell.NULL
    }
    case Block(exprs @ _*)                          =>
      exprs.foldLeft(Cell.NULL)((c: Cell[Value], s: Expr) => evaluate(store)(s))
  }

  private def getNumValue(v: Cell[Value]): Int = v.get.asInstanceOf[Num].value

}
