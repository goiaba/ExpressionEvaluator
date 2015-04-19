package edu.luc.cs.spring2015.comp471

import jline.console.ConsoleReader
import org.parboiled2.ParseError

import scala.util.{Failure, Success}

object Calculator extends App {

  def processStatements(input: String): Unit = {
    println("You entered: " + input)
    val parser = new MiniJSParser(input)
    parser.InputLine.run() match {
      case Failure(error: ParseError) =>
        println("This statement could not be parsed:")
        println(parser.formatError(error))
      case Failure(error) =>
        println("This statement could not be evaluated: " + error)
      case Success(statements) =>
        import edu.luc.cs.spring2015.comp471.behaviors._
        println("The parsed statements are: ")
        println(toFormattedString(statements))
        println("The unparsed statements are: ")
        println(Unparser.unparse(statements))
        print("It evaluates to ")
        println(Evaluator.evaluate(statements))
        println("Memory: " + Evaluator.memoryAsString)
    }
  }

  def isAllBracketsClosed(s: String): Boolean =
    (s.count((c: Char) => c == '{') == s.count((c: Char) => c == '}'))

  val console = new ConsoleReader()
  console.setPrompt("minic> ")
  println("Enter the expressions and press <enter> to parse your input (multiline expressions allowed inside blocks)")
  println("Memory: " + Evaluator.memoryAsString)

  if (args.length > 0) {
    processStatements(args mkString " ")
  } else {
    val input = new StringBuilder()
    var emptyLinesCount = 0
    Iterator continually {
      console.readLine()
    } takeWhile {
      Option(_).isDefined
    } foreach {
      (s: String) =>
        val buffer = input.append(s).toString
        if ("".equals(s)) emptyLinesCount += 1
        if (isAllBracketsClosed(buffer)) {
          processStatements(buffer)
          input.setLength(0)
        } else if (emptyLinesCount == 2) {
          println("You typed two blank lines.  Starting a new command.")
          emptyLinesCount = 0
          input.setLength(0)
        }
    }
  }
}
