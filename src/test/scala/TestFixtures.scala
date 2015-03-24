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
  val ifWithMultipleElseUnparsedString = "" +
    ""

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
    "{\n  if (((-3 + 4) + (5 * 6))) {\n    while (0) {\n      x = 3;\n      y = 5;\n      {\n        xy = 88;\n      }\n    }\n  } else {\n  }\n}"

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

  val complex1string = "((1 + 2) - (3 * 4)) / 5;"
  val complex1string2 = "  ((1 + 2) - (3 * 4)) / 5;  "
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

  val complex2string = "((1 + 2) - (-3 * 4)) % 5;"
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
}
