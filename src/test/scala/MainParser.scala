package edu.luc.cs.laufer.cs473.expressions

import org.scalatest.FunSuite

import edu.luc.cs.laufer.cs473.expressions.TestFixtures._

object MainParser extends App {
  val parsedExpr = new Parser(complex1string).InputLine.run()
  println(parsedExpr.get)
  println(complex1)
  println(parsedExpr.get == complex1)
//  println(behaviors.evaluate(parsedExpr.get))
}

class TestParser extends FunSuite {
  test("parser works 1") { assert(new Parser(complex1string).InputLine.run().get === complex1) }
  test("parser works 2") { assert(new Parser(complex1string2).InputLine.run().get === complex1) }
  test("parser works 3") { assert(new Parser(complex2string).InputLine.run().get === complex2) }

  test("parser works on singleAssignment") { assert(new Parser(singleAssignmentString).InputLine.run().get === singleAssignment) }
  test("parser works on complexAssignment") { assert(new Parser(complexAssignmentString).InputLine.run().get === complexAssignment) }
  test("parser works on assignmentWithIf") { assert(new Parser(assignmentWithIfString).InputLine.run().get === assignmentWithIf) }
  test("parser works on assignmentWithIfElse") { assert(new Parser(assignmentWithIfElseString).InputLine.run().get === assignmentWithIfElse) }
  test("parser works on ifWithMultipleElseString") { assert(new Parser(ifWithMultipleElseString).InputLine.run().get === ifWithMultipleElse) }
  test("parser works on assignmentWithinBlock") { assert(new Parser(assignmentWithinBlockString).InputLine.run().get === assignmentWithinBlock) }
  test("parser works on ifWithDoubleAssignment") { assert(new Parser(ifWithDoubleAssignmentString).InputLine.run().get === ifWithDoubleAssignment) }
  test("parser works on whileLoop1") { assert(new Parser(whileLoop1String1).InputLine.run().get === whileLoop1) }
  test("parser works on whileLoop2") { assert(new Parser(whileLoop1String2).InputLine.run().get === whileLoop1) }
  test("parser works on trelloExample") { assert(new Parser(trelloExampleString).InputLine.run().get === trelloExample) }
}

