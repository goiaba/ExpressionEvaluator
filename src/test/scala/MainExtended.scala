package edu.luc.cs.laufer.cs473.expressions

import org.scalatest.FunSuite

import behaviors._
import TestFixtures._

object MainExtended extends App {
  println("p = " + complex1)
//  println("evaluate(p) = " + evaluate(complex1))
//  println("size(p) = " + size(complex1))
  println("depth(p) = " + depth(complex1))
  println(toFormattedString(complex1))
  println("q = " + complex2)
//  println("evaluate(q) = " + evaluate(complex2))
//  println("size(q) = " + size(complex2))
  println("depth(q) = " + depth(complex2))
  println(toFormattedString(complex2))
}

class TestExtended extends FunSuite {
//  test("evaluate(p)") { assert(evaluate(complex1) === -1) }
//  test("size(p)") { assert(size(complex1) === 9) }
  test("depth(p)") { assert(depth(complex1) === 5) }
//  test("evaluate(q)") { assert(evaluate(complex2) === 0) }
//  test("size(q)") { assert(size(complex2) === 10) }
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
}
