package edu.luc.cs.spring2015.comp471

import TestFixtures._
import edu.luc.cs.spring2015.comp471.behaviors._
import org.scalatest.FunSuite

object MainParser extends App {
  val parsedExpr = new MiniJSParser(complex1String).InputLine.run()
  println(parsedExpr.get)
  println(complex1)
  println(parsedExpr.get == complex1)
//  println(behaviors.evaluate(parsedExpr.get))
}

class TestParser extends FunSuite {
  test("parser works 1") { assert(new MiniJSParser(complex1String).InputLine.run().get === complex1) }
  test("parser works 2") { assert(new MiniJSParser(complex1String2).InputLine.run().get === complex1) }
  test("parser works 3") { assert(new MiniJSParser(complex2String).InputLine.run().get === complex2) }

  test("parser works on singleAssignment") { assert(new MiniJSParser(singleAssignmentString).InputLine.run().get === singleAssignment) }
  test("parser works on complexAssignment") {
    val parsed = new MiniJSParser(complexAssignmentString).InputLine.run().get
    assert(parsed === complexAssignment)
    assert(toFormattedString(parsed) === complexAssignmentASTString)
  }
  test("parser works on assignmentWithIf") {
    val parsed = new MiniJSParser(assignmentWithIfString).InputLine.run().get
    assert(parsed === assignmentWithIf)
    assert(toFormattedString(parsed) === assignmentWithIfASTString)
  }
  test("parser works on assignmentWithIfElse") {
    val parsed = new MiniJSParser(assignmentWithIfElseString).InputLine.run().get
    assert(parsed === assignmentWithIfElse)
    assert(toFormattedString(parsed) === assignmentWithIfElseASTString)
  }
  test("parser works on ifWithMultipleElseString") {
    val parsed = new MiniJSParser(ifWithMultipleElseString).InputLine.run().get
    assert(parsed === ifWithMultipleElse)
    assert(toFormattedString(parsed) === ifWithMultipleElseASTString)
  }
  test("parser works on trelloExampleModified") {
    val parsed = new MiniJSParser(trelloExampleModifiedString).InputLine.run().get
    assert(parsed === trelloExampleModified)
    assert(toFormattedString(parsed) === trelloExampleModifiedASTString)
  }
  test("parser works on assignmentWithinBlock") {
    assert(new MiniJSParser(assignmentWithinBlockString).InputLine.run().get === assignmentWithinBlock)
  }
  test("parser works on ifWithDoubleAssignment") {
    assert(new MiniJSParser(ifWithDoubleAssignmentString).InputLine.run().get === ifWithDoubleAssignment)
  }
  test("parser works on whileLoop1") {
    assert(new MiniJSParser(whileLoop1String1).InputLine.run().get === whileLoop1)
  }
  test("parser works on whileLoop2") {
    assert(new MiniJSParser(whileLoop1String2).InputLine.run().get === whileLoop1)
  }
  test("parser works on trelloExample") {
    assert(new MiniJSParser(trelloExampleString).InputLine.run().get === trelloExample)
  }
  test("parser works on elseBranch") {
    val parsed = new MiniJSParser(elseBranchString).InputLine.run().get
    assert(parsed === elseBranch)
    assert(toFormattedString(parsed) === elseBranchASTString)
  }

  test("parser works on emptyStructAssignment") {
    val parsed = new MiniJSParser(emptyStructAssignmentString).InputLine.run().get
    assert(parsed === emptyStructAssignment)
    assert(toFormattedString(parsed) === emptyStructAssignmentASTString)
  }

  test("parser works on simpleStructAssignment") {
    val parsed = new MiniJSParser(simpleStructString).InputLine.run().get
    assert(parsed === simpleStruct)
    assert(toFormattedString(parsed) === simpleStructASTString)
  }

  test("parser works on complexStruct") {
    val parsed = new MiniJSParser(complexStructString).InputLine.run().get
    assert(parsed === complexStruct)
    assert(toFormattedString(parsed) === complexStructASTString)
  }

  test("parser works on tryingToAccessSelectorNumAsIns") {
    val parsed = new MiniJSParser(tryingToAccessSelectorNumAsInsString).InputLine.run().get
    assert(parsed === tryingToAccessSelectorNumAsIns)
    assert(toFormattedString(parsed) === tryingToAccessSelectorNumAsInsASTString)
  }

  test("parser works on tryingToAssignNumAsIns") {
    val parsed = new MiniJSParser(tryingToAssignNumAsInsString).InputLine.run().get
    assert(parsed === tryingToAssignNumAsIns)
    assert(toFormattedString(parsed) === tryingToAssignNumAsInsASTString)
  }
}

