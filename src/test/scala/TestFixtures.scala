package edu.luc.cs.laufer.cs473.expressions

object TestFixtures {

  import ast._

  val singleAssignmentString = "x = 5;"
  val singleAssignment =
    Block(
      Assignment(
        Identifier("x"),
        Constant(5)
      )
    )
  val singleAssignmentUnparsedString = "{\n  x = 5;\n}"

  val complexAssignmentString = "x = ((1 + y2) - (3 * y4)) / 5;"
  val complexAssignment =
    Block(
      Assignment(
        Identifier("x"),
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
        )
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
            Identifier("x"),
            Constant(2)
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
            Identifier("x"),
            Constant(2)
          )
        ),
        Block(
          Assignment(
            Identifier("x"),
            Constant(3)
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
            Identifier("x"),
            Constant(1)
          )
        ),
        Conditional(
          Constant(2),
          Block(
            Assignment(
              Identifier("x"),
              Constant(2)
            )
          ),
          Conditional(
            Constant(3),
            Block(
              Assignment(
                Identifier("x"),
                Constant(3)
              )
            ),
            Block(
              Assignment(
                Identifier("x"),
                Constant(4)
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
                Identifier("x"),
                Constant(3)
              )
            )
          ),
          Loop(
            Identifier("x"),
            Block(
              Assignment(
                Identifier("x"),
                Minus(
                  Identifier("x"),
                  Constant(1)
                )
              ),
              Assignment(
                Identifier("y"),
                Constant(5)
              ),
              Block(
                Assignment(
                  Identifier("xy"),
                  Constant(88)
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
              Identifier("y"),
              Plus(
                Plus(
                  Identifier("x"),
                  Identifier("y")
                ),
                Times(
                  Identifier("z"),
                  Constant(3)
                )
              )
            )
          ),
          Block(
            Assignment(
              Identifier("xwq"),
              UMinus(
                Constant(2123)
              )
            ),
            Assignment(
              Identifier("x"),
              Constant(4)
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
                Identifier("x"),
                Constant(3)
              ),
              Assignment(
                Identifier("y"),
                Constant(5)
              ),
              Block(
                Assignment(
                  Identifier("xy"),
                  Constant(88)
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
        Identifier("r"),
        Plus(
          Identifier("r"),
          Identifier("x")
        )
      ),
      Assignment(
        Identifier("y"),
        Plus(
          Identifier("y"),
          Constant(1)
        )
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
            Identifier("r"),
            Plus(
              Identifier("r"),
              Identifier("x")
            )
          ),
          Assignment(
            Identifier("y"),
            Plus(
              Identifier("y"),
              Constant(1)
            )
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
            Identifier("r"),
            Plus(
              Identifier("r"),
              Identifier("x")
            )
          ),
          Assignment(
            Identifier("y"),
            Minus(
              Identifier("y"),
              Constant(1)
            )
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
        Identifier("x"),
        Constant(3)
      ),
      Assignment(
        Identifier("y"),
        Constant(3)
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
            Identifier("x"),
            Constant(4)
          )
        ),
        Block(
          Assignment(
            Identifier("y"),
            Constant(5)
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
        Identifier("x"),
        Constant(10)
      ),
      Assignment(
        Identifier("y"),
        Constant(100)
      ),
      Loop(
        Identifier("x"),
        Block(
          Assignment(
            Identifier("y"),
            Plus(
              Identifier("y"),
              Identifier("x")
            )
          ),
          Assignment(
            Identifier("x"),
            Minus(
              Identifier("x"),
              Constant(1)
            )
          )
        )
      )
    )
  val simpleWhileUnparsedString = "{\n  x = 10;\n  y = 100;\n  while (x) {\n    y = y + x;\n    x = x - 1;\n  }\n}"
}
