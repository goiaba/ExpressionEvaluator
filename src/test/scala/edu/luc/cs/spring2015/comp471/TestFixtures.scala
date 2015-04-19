package edu.luc.cs.spring2015.comp471

import edu.luc.cs.spring2015.comp471.Evaluator.{Value, Num, Ins, Cell}
import scala.collection.mutable.{Map => MMap}

object TestFixtures {

  val singleAssignmentString = "x = 5;"
  val singleAssignment =
    Block(
      Assignment(
        Constant(5),
        Identifier("x")
      )
    )
  val singleAssignmentUnparsedString = "{\n  x = 5;\n}"

  val complexAssignmentString = "x = ((1 + y2) - (3 * y4)) / 5;"
  val complexAssignment =
    Block(
      Assignment(
        Div(
          Minus(
            Plus(
              Constant(1),
              Identifier("y2")
            ),
            Times(
              Constant(3),
              Identifier("y4")
            )
          ),
          Constant(5)
        ),
        Identifier("x")
      )
    )
  val complexAssignmentASTString =
    "Block(\n..Assignment(\n....x, \n....Div(\n......Minus(\n........Plus(\n..........1, \n..........y2), " +
      "\n........Times(\n..........3, \n..........y4)), \n......5)))"
  val complexAssignmentUnparsedString = "{\n  x = (((1 + y2) - (3 * y4)) / 5);\n}"

  val assignmentWithIfString = "if (1) { x = 2;  }"
  val assignmentWithIf =
    Block(
      Conditional(
        Constant(1),
        Block(
          Assignment(
            Constant(2),
            Identifier("x")
          )
        )
      )
    )
  val assignmentWithIfASTString =
    "Block(\n..Conditional(\n....1, \n....Block(\n......Assignment(\n........x, \n........2))))"
  val assignmentWithIfUnparsedString = "{\n  if (1) {\n  x = 2;\n  }\n}"

  val assignmentWithIfElseString = "if (1) { x = 2;  } else { x = 3; }"
  val assignmentWithIfElse =
    Block(
      Conditional(
        Constant(1),
        Block(
          Assignment(
            Constant(2),
            Identifier("x")
          )
        ),
        Block(
          Assignment(
            Constant(3),
            Identifier("x")
          )
        )
      )
    )
  val assignmentWithIfElseASTString =
    "Block(\n..Conditional(\n....1, \n....Block(\n......Assignment(\n........x, " +
    "\n........2)),\n....Block(\n......Assignment(\n........x, \n........3))))"
  val assignmentWithIfElseUnparsedString = "{\n  if (1) {\n    x = 2;\n  } else {\n    x = 3;\n  }\n}"

  val ifWithMultipleElseString = "if (1) { x = 1;  } else if (2) { x = 2; } else if(3){x=3;} else { x = 4; }"
  val ifWithMultipleElse =
    Block(
      Conditional(
        Constant(1),
        Block(
          Assignment(
            Constant(1),
            Identifier("x")
          )
        ),
        Conditional(
          Constant(2),
          Block(
            Assignment(
              Constant(2),
              Identifier("x")
            )
          ),
          Conditional(
            Constant(3),
            Block(
              Assignment(
                Constant(3),
                Identifier("x")
              )
            ),
            Block(
              Assignment(
                Constant(4),
                Identifier("x")
              )
            )
          )
        )
      )
    )
  val ifWithMultipleElseASTString =
    "Block(\n..Conditional(\n....1, \n....Block(\n......Assignment(\n........x, \n........1)),\n....Conditional(" +
      "\n......2, \n......Block(\n........Assignment(\n..........x, \n..........2)),\n......Conditional(\n........3, " +
      "\n........Block(\n..........Assignment(\n............x, \n............3)),\n........Block(\n..........Assignment(" +
      "\n............x, \n............4))))))"
  val ifWithMultipleElseUnparsedString =
    "{\n  if (1) {\n    x = 1;\n  } else if (2) {\n    x = 2;\n  } else if (3) {\n    x = 3;\n  } else {\n    x = 4;\n  }\n}"

  val trelloExampleModifiedString = "if(-3+4+5*6){if(3) {x=3;} while(x){x=x-1;y=5;{xy=88;}}} else if(-2) { y=x+y+z*3;} else {xwq=-2123;x=4;}"
  val trelloExampleModified =
    Block(
      Conditional(
        Plus(
          Plus(
            UMinus(
              Constant(3)
            ),
            Constant(4)
          ),
          Times(
            Constant(5),
            Constant(6)
          )
        ),
        Block(
          Conditional(
            Constant(3),
            Block(
              Assignment(
                Constant(3),
                Identifier("x")
              )
            )
          ),
          Loop(
            Identifier("x"),
            Block(
              Assignment(
                Minus(
                  Identifier("x"),
                  Constant(1)
                ),
                Identifier("x")
              ),
                Assignment(
                  Constant(5),
                  Identifier("y")
              ),
              Block(
                Assignment(
                  Constant(88),
                  Identifier("xy")
                )
              )
            )
          )
        ),
        Conditional(
          UMinus(
            Constant(2)
          ),
          Block(
            Assignment(
              Plus(
                  Plus(
                      Identifier("x"),
                      Identifier("y")
                  ),
                  Times(
                      Identifier("z"),
                      Constant(3)
                  )
              ),
              Identifier("y")
            )
          ),
          Block(
            Assignment(
              UMinus(
                  Constant(2123)
              ),
              Identifier("xwq")
            ),
            Assignment(
              Constant(4),
              Identifier("x")
            )
          )
        )
      )
    )
  val trelloExampleModifiedASTString =
    "Block(\n..Conditional(\n....Plus(\n......Plus(\n........UMinus(\n..........3), \n........4), " +
    "\n......Times(\n........5, \n........6)), \n....Block(\n......Conditional(\n........3, \n........Block(" +
    "\n..........Assignment(\n............x, \n............3))),\n......Loop(\n........x, \n........Block(" +
    "\n..........Assignment(\n............x, \n............Minus(\n..............x, \n..............1))," +
    "\n..........Assignment(\n............y, \n............5),\n..........Block(\n............Assignment(" +
    "\n..............xy, \n..............88))))),\n....Conditional(\n......UMinus(\n........2), \n......Block(" +
    "\n........Assignment(\n..........y, \n..........Plus(\n............Plus(\n..............x, \n..............y), " +
    "\n............Times(\n..............z, \n..............3)))),\n......Block(\n........Assignment(\n..........xwq, " +
    "\n..........UMinus(\n............2123)),\n........Assignment(\n..........x, \n..........4)))))"
  val trelloExampleModifiedUnparsedString = "{\n  if (((-3 + 4) + (5 * 6))) {\n    if (3) {\n      x = 3;\n    }\n    while (x) {\n      x = (x - 1);\n      y = 5;\n      {\n        xy = 88;\n      }\n    }\n  } else if (-2) {\n    y = ((x + y) + (z * 3));\n  } else {\n    xwq = -2123;\n    x = 4;\n  }\n}"

  val trelloExampleString = "if(-3+4+5*6){while(0){x=3;y=5;{xy=88;}}}"
  val trelloExample =
    Block(
      Conditional(
        Plus(
          Plus(
            UMinus(
              Constant(3)
            ),
            Constant(4)
          ),
          Times(
            Constant(5),
            Constant(6)
          )
        ),
        Block(
          Loop(
            Constant(0),
            Block(
              Assignment(
                Constant(3),
                Identifier("x")
              ),
              Assignment(
                Constant(5),
                Identifier("y")
              ),
              Block(
                Assignment(
                  Constant(88),
                  Identifier("xy")
                )
              )
            )
          )
        )
      )
    )
  val trelloExampleUnparsedString =
    "{\n  if (((-3 + 4) + (5 * 6))) {\n    while (0) {\n      x = 3;\n      y = 5;\n      {\n        xy = 88;\n      }\n    }\n  }\n}"

  val assignmentWithinBlockString = "{ r = r + x; y = y + 1 ; }"
  val assignmentWithinBlock =
    Block(
      Assignment(
        Plus(
            Identifier("r"),
            Identifier("x")
        ),
        Identifier("r")
      ),
      Assignment(
        Plus(
          Identifier("y"),
          Constant(1)
        ),
        Identifier("y")
      )
    )
  val assignmentWithinBlockUnparsedString = "{\n  r = (r + x);\n  y = (y + 1);\n}"

  val ifWithDoubleAssignmentString = "if (4) { r = r + x; y = y + 1; }"
  val ifWithDoubleAssignment =
    Block(
      Conditional(
        Constant(4),
        Block(
          Assignment(
            Plus(
              Identifier("r"),
              Identifier("x")
            ),
            Identifier("r")
          ),
          Assignment(
            Plus(
              Identifier("y"),
              Constant(1)
            ),
            Identifier("y")
          )
        )
      )
    )
  val ifWithDoubleAssignmentUnparsedString = "{\n  if (4) {\n    r = (r + x);\n    y = (y + 1);\n  }\n}"

  val whileLoop1String1 = "while (y) {\n      r = r + x;\n      y = y - 1;\n    }"
  val whileLoop1String2 = "while (y) {\n      r = r + x\n    ; y = y - 1 ;\n    }"
  val whileLoop1 =
    Block(
      Loop(
        Identifier("y"),
        Block(
          Assignment(
            Plus(
              Identifier("r"),
              Identifier("x")
            ),
            Identifier("r")
          ),
          Assignment(
            Minus(
              Identifier("y"),
              Constant(1)
            ),
            Identifier("y")
          )
        )
      )
    )
  val whileLoop1UnparsedString = "{\n  while (y) {\n    r = (r + x);\n    y = (y - 1);\n  }\n}"

  val complex1String = "((1 + 2) - (3 * 4)) / 5;"
  val complex1String2 = "  ((1 + 2) - (3 * 4)) / 5;  "
  val complex1 =
    Block(
      Div(
        Minus(
          Plus(
            Constant(1),
            Constant(2)
          ),
          Times(
            Constant(3),
            Constant(4)
          )
        ),
        Constant(5)
      )
    )
  val complex1UnparsedString = "{\n  (((1 + 2) - (3 * 4)) / 5);\n}"

  val complex2String = "((1 + 2) - (-3 * 4)) % 5;"
  val complex2 =
    Block(
      Mod(
        Minus(
          Plus(
            Constant(1),
            Constant(2)
          ),
          Times(
            UMinus(
              Constant(3)
            ),
            Constant(4)
          )
        ),
        Constant(5)
      )
    )
  val complex2UnparsedString = "{\n  (((1 + 2) - (-3 * 4)) % 5);\n}"

  val elseBranchString = "x=3; y=3; if (((x--y)*x/y)%y) { x = 4; } else { y = 5; }"
  val elseBranch =
    Block(
      Assignment(
        Constant(3),
        Identifier("x")
      ),
      Assignment(
        Constant(3),
        Identifier("y")
      ),
      Conditional(
        Mod(
          Div(
            Times(
              Minus(
                Identifier("x"),
                UMinus(
                  Identifier("y")
                )
              ),
              Identifier("x")
            ),
            Identifier("y")
          ),
          Identifier("y")
        ),
        Block(
          Assignment(
            Constant(4),
            Identifier("x")
          )
        ),
        Block(
          Assignment(
            Constant(5),
            Identifier("y")
          )
        )
      )
    )
  val elseBranchASTString =
    "Block(\n..Assignment(\n....x, \n....3),\n..Assignment(\n....y, \n....3),\n..Conditional(\n....Mod(\n......Div(" +
      "\n........Times(\n..........Minus(\n............x, \n............UMinus(\n..............y)), \n..........x), " +
      "\n........y), \n......y), \n....Block(\n......Assignment(\n........x, \n........4)),\n....Block(" +
      "\n......Assignment(\n........y, \n........5))))"
  val elseBranchUnparsedString = "{\n  x = 3;\n  y = 3;\n  if (((((x - -y) * x) / y) % y)) {\n    x = 4;\n  } else {\n    y = 5;\n  }\n}"

  val simpleWhileString = "x=10; y=100; while (x) { y=y+x; x=x-1; }"
  val simpleWhile =
    Block(
      Assignment(
        Constant(10),
        Identifier("x")
      ),
      Assignment(
        Constant(100),
        Identifier("y")
      ),
      Loop(
        Identifier("x"),
        Block(
          Assignment(
            Plus(
              Identifier("y"),
              Identifier("x")
            ),
            Identifier("y")
          ),
          Assignment(
            Minus(
              Identifier("x"),
              Constant(1)
            ),
            Identifier("x")
          )
        )
      )
    )
  val simpleWhileUnparsedString = "{\n  x = 10;\n  y = 100;\n  while (x) {\n    y = y + x;\n    x = x - 1;\n  }\n}"

  val emptyStructAssignmentString = "x = {};"
  val emptyStructAssignment =
    Block(
      Assignment(
        Struct(Map[String, Expr]()),
        Identifier("x")
      )
    )

  val emptyStructAssignmentASTString = "Block(\n..Assignment(\n....x, \n....Struct(\n)))"
  val emptyStructAssignmentUnparsed = "{\n  x = {};\n}"

  val simpleStructString = "x={a:3};"
  val simpleStruct =
    Block(
      Assignment(
        Struct(
          Map[String, Expr]("a" -> Constant(3))
        ),
        Identifier("x")
      )
    )
  val simpleStructASTString = "Block(\n..Assignment(\n....x, \n....Struct(\n......Assignment(\n........a, \n........3))))"
  val simpleStructUnparsedString = "x = { a : 3 };"

  val complexStructString = "x=        { a :      { b :5},c:13};"
  val complexStruct =
    Block(
      Assignment(
        Struct(
          Map[String, Expr](
            "a" -> Struct(
              Map[String, Expr]("b" -> Constant(5))
            ),
            "c" -> Constant(13)
          )
        ),
        Identifier("x")
      )
    )
  val complexStructASTString = "Block(\n..Assignment(\n....x, \n....Struct(\n......Assignment(\n........a, \n" +
    "........Struct(\n..........Assignment(\n............b, \n............5))),\n......Assignment(\n........c, \n" +
    "........13))))"
  val complexStructUnparsedString = "{\n  x = { a : { b : 5 }, c : 13 }\n}"

  val complexStructWithAssignment1String = "x = {a : {b: {c:3}}, d:13};"
  def complexStructWithAssignment1Memory = {
    val d = Cell(Num(13))
    val c = Cell(Num(3))
    val b = Cell(Ins(MMap[String, Cell[_]]("c" -> c).asInstanceOf[Evaluator.Store]))
    val a = Cell(Ins(MMap[String, Cell[_]]("b" -> b).asInstanceOf[Evaluator.Store]))
    val x = Cell(Ins(MMap[String, Cell[_]]("a" -> a, "d" -> d).asInstanceOf[Evaluator.Store]))
    x
  }
  val complexStructWithAssignment1 =
    Block(
      Assignment(
        Struct(
          Map[String, Expr](
            "a" -> Struct(
              Map[String, Expr](
                "b" -> Struct(
                  Map[String, Expr](
                    "c" -> Constant(3)
                  )
                )
              )
            ),
            "d" -> Constant(13)
          )
        ),
        Identifier("x")
      )
    )

  val complexStructWithAssignment2String = "x = {a : {b: {c:3}}, d:13}; z = x;"
  val complexStructWithAssignment2Memory = complexStructWithAssignment1Memory
  val complexStructWithAssignment2 =
    Block(
      Assignment(
        Identifier("x"),
        Identifier("z")
      )
    )

  val complexStructWithAssignment3String = "x = {a : {b: {c:3}}, d:13}; z = x; z.a.b.c = 4;"
  val complexStructWithAssignment3Memory = {
    val xCell = complexStructWithAssignment1Memory
    val aCell = xCell.get.value.get("a").get
    val bCell = aCell.get.asInstanceOf[Ins].value.get("b").get
    val cCell = bCell.get.asInstanceOf[Ins].value.get("c").get
    cCell.set(Num(4))
    xCell
  }
  val complexStructWithAssignment3 =
    Block(
      Assignment(
        Constant(4),
        Identifier("x"),
        Identifier("a"),
        Identifier("b"),
        Identifier("c")
      )
    )

  val undefinedSelectorString = "x={}; x.z;"

  val undefinedFieldTryingToAccessSelectorString = "x.z;"

  val undefinedFieldTryingToAssignValueToSelectorString = "x.z = 3;"

  val tryingToAccessSelectorNumAsInsString = "x={z:3}; x.z.y;"
  val tryingToAccessSelectorNumAsIns =
    Block(
      Assignment(
        Struct(
          Map[String, Expr](
            "z" -> Constant(3)
          )
        ),
        Identifier("x")
      ),
      Select(
        Identifier("x"),
        Identifier("z"),
        Identifier("y")
      )
    )
  val tryingToAccessSelectorNumAsInsASTString = "Block(\n..Assignment(\n....x, \n....Struct(\n......Assignment(\n" +
    "........z, \n........3))),\n..Select(x.z.y))"

  val tryingToAssignToUndefinedSelectorString = "x={z:{w:3}}; x.z.w.k.y = 4;"

  val tryingToAssignNumAsInsString = "x={z:3}; x.z.y = 10; x.z.y;"
  val tryingToAssignNumAsIns =
    Block(
      Assignment(
        Struct(
          Map[String, Expr](
            "z" -> Constant(3)
          )
        ),
        Identifier("x")
      ),
      Assignment(
        Constant(10),
        Identifier("x"),
        Identifier("z"),
        Identifier("y")
      ),
      Select(
        Identifier("x"),
        Identifier("z"),
        Identifier("y")
      )
    )
  val tryingToAssignNumAsInsASTString = "Block(\n..Assignment(\n....x, \n....Struct(\n......Assignment(\n........z, \n" +
    "........3))),\n..Assignment(\n....Select(x.z.y), \n....10),\n..Select(x.z.y))"

  val tryingToAssignValueToUndefinedSelectorString = "x={z:3}; x.w = 3;"
  val emptyStructUnparsedAssignment = "{\n  x = {};\n}"

  val simpleStructAssignmentString = "a = {b:1, c:2, d:{e:1, f:2}};"

  val simpleStructAssignment =
    Block(
      Assignment(
        Struct(Map[String, Expr]()),
        Identifier("a")
      )
    )
  val simpleStructAssignmentUnparsedString = "{\n  a = {b : 1, d : {e : 1, f : 2}, c : 2};\n}"

}
