package edu.luc.cs.spring2015.comp471

import TestFixtures._
import edu.luc.cs.spring2015.comp471.behaviors._
import org.scalatest.FunSuite

object Main extends App {
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

class Test extends FunSuite {
//  test("evaluate(p)") { assert(evaluate(complex1) === -1) }
//  test("size(p)") { assert(size(complex1) === 9) }
  test("depth(p)") { assert(depth(complex1) === 5) }
}
