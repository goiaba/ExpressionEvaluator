package edu.luc.cs.laufer.cs473.expressions

import scala.collection.immutable.List

object TestFixtures {

  import ast._

  val singleAssignmentString = "x = 5;"
  val singleAssignment =
    List(
      Assignment(
        Identifier("x"),
        Constant(5)
      )
    )

  val complexAssignmentString = "x = ((1 + y2) - (3 * y4)) / 5;"
  val complexAssignment =
    List(
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
    List(
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
    List(
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
    List(
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

  val assignmentWithinBlockString = "{ r = r + x; y = y + 1 ; }"
  val assignmentWithinBlock =
    List(
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

  val ifWithDoubleAssignmentString = "if (4) { r = r + x; y = y + 1; }"
  val ifWithDoubleAssignment =
    List(
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
    List(
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
    List(
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
    List(
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
