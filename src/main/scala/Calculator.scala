package edu.luc.cs.laufer.cs473.expressions

import jline.console.ConsoleReader
import org.parboiled2.ParseError
import scala.util.{Failure, Success}

object Calculator extends App {

  def processStatements(input: String): Unit = {
    println("You entered: " + input)
    val parser = new Parser(input)
    parser.InputLine.run() match {
      case Failure(error: ParseError) =>
        println("This statement could not be parsed:")
        println(parser.formatError(error))
      case Failure(error) =>
        println("This statement could not be evaluated: " + error)
      case Success(statements) =>
        import behaviors._
        println("\nThe parsed statements is: ")
        println(toFormattedString(statements))
        println("\nThe unparsed statements is: ")
        println(Unparser.unparse(statements))
        print("\nIt evaluates to ")
        println(Evaluator.evaluate(statements))
        println("Memory: " + Evaluator.storeAsString)
    }
  }

  def isAllBracketsClosed(s: String): Boolean =
    (s.count((c: Char) => c == '{') == s.count((c: Char) => c == '}'))

  val console = new ConsoleReader()
  console.setPrompt("minic> ")
  println("Memory: " + Evaluator.storeAsString)

  if (args.length > 0) {
    processStatements(args mkString " ")
  } else {
    println("Enter the expressions and press <enter> to parse your input (multiline expressions allowed inside blocks)")
    val input = new StringBuilder()
    Iterator continually {
      console.readLine()
    } takeWhile {
      Option(_).isDefined
    } foreach {
      (s: String) =>
        val buffer = input.append(s).toString
        if (isAllBracketsClosed(buffer)) {
          processStatements(buffer)
          input.setLength(0)
        }
    }
  }
}