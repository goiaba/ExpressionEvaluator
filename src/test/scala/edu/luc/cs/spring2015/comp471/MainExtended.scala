package edu.luc.cs.spring2015.comp471

import TestFixtures._
import edu.luc.cs.spring2015.comp471.behaviors._
import org.scalatest.FunSuite

object MainExtended extends App {
  println("p = " + complex1)
  println("depth(p) = " + depth(complex1))
  println(toFormattedString(complex1))
  println("q = " + complex2)
  println("depth(q) = " + depth(complex2))
  println(toFormattedString(complex2))
}

class TestExtended extends FunSuite {
  test("depth(p)") { assert(depth(complex1) === 5) }
  test("depth(q)") { assert(depth(complex2) === 6) }
  test("depth(singleAssignment)") { assert(depth(singleAssignment) === 3) }
  test("depth(complexAssignment)") { assert(depth(complexAssignment) === 6) }
  test("depth(assignmentWithIf)") { assert(depth(assignmentWithIf) === 5) }
  test("depth(assignmentWithIfElse)") { assert(depth(assignmentWithIfElse) === 5) }
  test("depth(ifWithMultipleElse)") { assert(depth(ifWithMultipleElse) === 7) }
  test("depth(assignmentWithinBlock)") { assert(depth(assignmentWithinBlock) === 4) }
  test("depth(ifWithDoubleAssignment)") { assert(depth(ifWithDoubleAssignment) === 6) }
  test("depth(whileLoop1)") { assert(depth(whileLoop1) === 6) }
  test("depth(trelloExample)") { assert(depth(trelloExample) === 8) }
  test("depth(tryingToAssignNumAsIns)") { assert(depth(tryingToAssignNumAsIns) === 4) }

}
