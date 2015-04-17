package edu.luc.cs.spring2015.comp471

import edu.luc.cs.spring2015.comp471.Evaluator.{Ins, Cell, Num}
import edu.luc.cs.spring2015.comp471.TestFixtures._
import org.scalatest.FunSuite
import scala.util.Success

/**
* Created by sauloaguiar on 3/29/15.
*/
object MainEvaluator extends App {

}

class TestEvaluator extends FunSuite {

  def parserFixture(string: String) = {
    val parser = new MiniJSParser(string)
    val stmt = parser.InputLine.run().get
    Evaluator.clearMemory
    Evaluator.evaluate(stmt)
  }

  test("evaluate singleAssignment") {
    val result = parserFixture(singleAssignmentString)
    assert(result === Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(5)))
    assert(Evaluator.memoryAsString === "Map(x -> Cell(Num(5)))")
  }

  test("evaluate works on complexAssignment") {
    val result = parserFixture(complexAssignmentString)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "y2")
  }

  test("evaluate works on assignmentWithIf") {
    val result = parserFixture(assignmentWithIfString)
    assert(result === Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(2)))
  }
  test("evaluate works on assignmentWithIfElse") {
    val result = parserFixture(assignmentWithIfElseString)
    assert(result == Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(2)))
  }

  test("evaluate works on ifWithMultipleElseString") {
    val result = parserFixture(ifWithMultipleElseString)
    assert(result == Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(1)))
  }

  test("evaluate works on assignmentWithinBlock") {
    val result = parserFixture(assignmentWithinBlockString)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "r")
  }

  test("parser works on ifWithDoubleAssignment") {
    val result = parserFixture(ifWithDoubleAssignmentString)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "r")
  }

  test("evaluate works on simpleWhile") {
    val result = parserFixture(simpleWhileString)
    assert(result == Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(0)))
    assert(Evaluator.memory.get("y").get === Cell(Num(155)))
  }

  test("evaluate works on elseBranch") {
    val result = parserFixture(elseBranchString)
    assert(result == Success(Num(0)))
    assert(Evaluator.memory.get("x").get === Cell(Num(3)))
    assert(Evaluator.memory.get("y").get === Cell(Num(5)))
  }

  test("evaluate works on complexStructWithAssignment") {
    parserFixture(complexStructWithAssignment1String)
    assert(Evaluator.memory.get("x").get === complexStructWithAssignment1Memory)
    parserFixture(complexStructWithAssignment2String)
    assert(Evaluator.memory.get("x").get === Evaluator.memory.get("z").get)
    parserFixture(complexStructWithAssignment3String)
    assert(Evaluator.memory.get("x").get === complexStructWithAssignment3Memory)
    assert(Evaluator.memory.get("x").get === Evaluator.memory.get("z").get)
  }

  test("evaluate works on undefinedSelector") {
    val result = parserFixture(undefinedSelectorString)
    val thrown = intercept[UndefinedSelectorException] {
      result.get
    }
    assert(thrown.getMessage === "z")
  }

  test("evaluate works on undefinedFieldTryingToAssignValueToSelector") {
    val result = parserFixture(undefinedFieldTryingToAssignValueToSelectorString)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "x")
  }

  test("evaluate works on undefinedFieldTryingToAccessSelector") {
    val result = parserFixture(undefinedFieldTryingToAccessSelectorString)
    val thrown = intercept[java.lang.NoSuchFieldException] {
      result.get
    }
    assert(thrown.getMessage === "x")
  }

  test("evaluate works on tryingToAccessSelectorNumAsIns") {
    val result = parserFixture(tryingToAccessSelectorNumAsInsString)
    val thrown = intercept[UndefinedSelectorException] {
      result.get
    }
    assert(thrown.getMessage === "y")
  }

  test("evaluate works on tryingToAssignNumAsIns") {
    val result = parserFixture(tryingToAssignNumAsInsString)
    assert(result === Success(Num(10)))
  }

  test("evaluate works on tryingToAssignToUndefinedSelector") {
    val result = parserFixture(tryingToAssignToUndefinedSelectorString)
    val thrown = intercept[UndefinedSelectorException] {
      result.get
    }
    assert(thrown.getMessage === "k")
  }

}
