package edu.luc.cs.laufer.cs473.expressions

import edu.luc.cs.laufer.cs473.expressions.TestFixtures._
import org.scalatest.FunSuite
import behaviors._

/**
 * Created by bruno on 3/23/15.
 */
object MainUnparser extends App {

}

class TestUnparser extends FunSuite {
  test("unparser works trelloExample") { assert(unparse(trelloExample) === trelloExampleUnparsedString) }
  test("unparser works singleAssignment") { assert(unparse(singleAssignment) === singleAssignmentUnparsedString) }
  test("unparser works complexAssignment") { assert(unparse(complexAssignment) === complexAssignmentUnparsedString) }
  test("unparser works assignmentWithIfElse") { assert(unparse(assignmentWithIfElse) === assignmentWithIfElseUnparsedString) }
  test("unparser works ifWithMultipleElse") { assert(unparse(ifWithMultipleElse) === ifWithMultipleElseUnparsedString) }
  test("unparser works assignmentWithinBlock") { assert(unparse(assignmentWithinBlock) === assignmentWithinBlockUnparsedString) }
  test("unparser works ifWithDoubleAssignment") { assert(unparse(ifWithDoubleAssignment) === ifWithDoubleAssignmentUnparsedString) }
  test("unparser works whileLoop1") { assert(unparse(whileLoop1) === whileLoop1UnparsedString) }
  test("unparser works complex1") { assert(unparse(complex1) === complex1UnparsedString) }
  test("unparser works complex2") { assert(unparse(complex2) === complex2UnparsedString) }
}
