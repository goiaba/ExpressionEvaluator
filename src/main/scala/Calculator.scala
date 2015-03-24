package edu.luc.cs.laufer.cs473.expressions

import java.io.{BufferedInputStream, BufferedOutputStream, PrintWriter}

import jline.TerminalFactory
import jline.console.ConsoleReader
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
        println("\nThe parsed expression is: ")
        println(toFormattedString(statements))

        println("\nThe unparsed expression is: ")
        println(unparse(statements))
        //println("It has size " + size(expr) + " and depth " + depth(expr))
        //println("It evaluates to " + evaluate(expr))
    }
  }

  val console = new ConsoleReader()
  console.setPrompt("minic> ")
  var line = ""

  if (args.length > 0) {
    processStatements(args mkString " ")
  } else {
    println("Enter the expressions and leave an empty line to parse your input")
    val input = new StringBuilder()
    while (true) {
      line = console.readLine()
      input.append(line)
      if (line ==  "") {
        processStatements(input.toString())
        input.setLength(0)
      }
    }
  }
}
