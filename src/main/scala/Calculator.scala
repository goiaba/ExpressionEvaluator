package edu.luc.cs.laufer.cs473.expressions

import org.parboiled2.ParseError
import scala.util.{Failure, Success}

object Calculator extends App {

  def processStatements(input: String): Unit = {
    println("You entered: " + input)
    val parser = new Parser(input)
    parser.InputLine.run() match {
      case Failure(error: ParseError) =>
        println("This expression could not be parsed:")
        println(parser.formatError(error))
      case Failure(error) =>
        println("This expression could not be evaluated: " + error)
      case Success(statements) =>
        import behaviors._
        println("The parsed expression is: ")
        println(toFormattedString(statements))
        println("It has depth " + depth(statements))
        //println("It has size " + size(expr) + " and depth " + depth(expr))
        //println("It evaluates to " + evaluate(expr))
    }
  }

  if (args.length > 0) {
    processStatements(args mkString " ")
  } else {
    print("Enter infix expression: ")
    scala.io.Source.stdin.getLines foreach { line =>
      processStatements(line)
      print("Enter infix expression: ")
    }
  }
}
