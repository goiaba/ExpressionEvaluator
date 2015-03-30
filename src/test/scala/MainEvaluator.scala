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
    val stmt = parser.InputLine.run().get
    Evaluator.evaluate(stmt)
  }

  test("evaluate singleAssignment") {
    val result = parserFixture(singleAssignmentString)
    assert(result === Success(Cell(0)))
    assert(Evaluator.memory.get("x").get === Cell(5))
  }

  test("evaluate works on complexAssignment") {
    val result = parserFixture(complexAssignmentString)
    intercept[java.lang.NoSuchFieldException] {
      result.get
    }
  }

  test("evaluate works on assignmentWithIf") {
    val result = parserFixture(assignmentWithIfString)
    assert(result == Success(Cell(0)))
    assert(Evaluator.memory.get("x").get == Cell(2))
  }
  test("evaluate works on assignmentWithIfElse") {
    val result = parserFixture(assignmentWithIfElseString)
    assert(result == Success(Cell(0)))
    assert(Evaluator.memory.get("x").get == Cell(2))
  }

  test("evaluate works on ifWithMultipleElseString") {
    val result = parserFixture(ifWithMultipleElseString)
    assert(result == Success(Cell(0)))
    assert(Evaluator.memory.get("x").get == Cell(1))
  }

  test("evaluate works on assignmentWithinBlock") {
    val result = parserFixture(assignmentWithinBlockString)
    intercept[java.lang.NoSuchFieldException] {
      result.get
    }
  }

  test("parser works on ifWithDoubleAssignment") {
    val result = parserFixture(ifWithDoubleAssignmentString)
    intercept[java.lang.NoSuchFieldException] {
      result.get
    }
  }

}
