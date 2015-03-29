package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.Parser
import edu.luc.cs.laufer.cs473.expressions.TestFixtures._
import edu.luc.cs.laufer.cs473.expressions.behaviors._
import org.parboiled2.ParseError
import org.scalatest.FunSuite

import scala.util.{Success, Failure}

/**
 * Created by sauloaguiar on 3/29/15.
 */
object MainEvaluator extends App {

}

class TestEvaluator extends FunSuite {

  def parserFixture(string: String) = {
    val parser = new Parser(string)
    parser.InputLine.run()
  }

  test("evaluate singleAssignment") {
    parserFixture(singleAssignmentString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Success(Cell(0)))
        assert(Evaluator.memory.get("x").get == Cell(5))
    }
  }

  test("evaluate works on complexAssignment") {
    parserFixture(complexAssignmentString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Failure(new java.lang.NoSuchFieldException("y2")))
    }
  }

  test("evaluate works on assignmentWithIf") {
    parserFixture(assignmentWithIfString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Success(Cell(0)))
        assert(Evaluator.memory.get("x").get == Cell(2))
    }
  }
  test("evaluate works on assignmentWithIfElse") {
    parserFixture(assignmentWithIfElseString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Success(Cell(0)))
        assert(Evaluator.memory.get("x").get == Cell(2))
    }
  }
  test("evaluate works on ifWithMultipleElseString") {
    parserFixture(ifWithMultipleElseString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Success(Cell(0)))
        assert(Evaluator.memory.get("x").get == Cell(1))
    }
  }

  test("evaluate works on assignmentWithinBlock") {
    parserFixture(assignmentWithinBlockString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Failure(new NoSuchFieldException("r")))
    }
  }

  test("parser works on ifWithDoubleAssignment") {
    parserFixture(ifWithDoubleAssignmentString) match {
      case Success(statements) =>
        val result = Evaluator.evaluate(statements)
        assert(result == Failure(new NoSuchFieldException("r")))
    }
  }

}
