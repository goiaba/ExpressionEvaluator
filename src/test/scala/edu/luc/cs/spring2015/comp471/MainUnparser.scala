package edu.luc.cs.spring2015.comp471

import TestFixtures._
import org.scalatest.FunSuite

/**
 * Created by bruno on 3/23/15.
 */
object MainUnparser extends App {

}

class TestUnparser extends FunSuite {
  test("unparser works trelloExampleModified") { assert(Unparser.unparse(trelloExampleModified) === trelloExampleModifiedUnparsedString) }
  test("unparser works trelloExample") { assert(Unparser.unparse(trelloExample) === trelloExampleUnparsedString) }
  test("unparser works singleAssignment") { assert(Unparser.unparse(singleAssignment) === singleAssignmentUnparsedString) }
  test("unparser works complexAssignment") { assert(Unparser.unparse(complexAssignment) === complexAssignmentUnparsedString) }
  test("unparser works assignmentWithIfElse") { assert(Unparser.unparse(assignmentWithIfElse) === assignmentWithIfElseUnparsedString) }
  test("unparser works ifWithMultipleElse") { assert(Unparser.unparse(ifWithMultipleElse) === ifWithMultipleElseUnparsedString) }
  test("unparser works assignmentWithinBlock") { assert(Unparser.unparse(assignmentWithinBlock) === assignmentWithinBlockUnparsedString) }
  test("unparser works ifWithDoubleAssignment") { assert(Unparser.unparse(ifWithDoubleAssignment) === ifWithDoubleAssignmentUnparsedString) }
  test("unparser works whileLoop1") { assert(Unparser.unparse(whileLoop1) === whileLoop1UnparsedString) }
  test("unparser works complex1") { assert(Unparser.unparse(complex1) === complex1UnparsedString) }
  test("unparser works complex2") { assert(Unparser.unparse(complex2) === complex2UnparsedString) }
}
