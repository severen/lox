package klox

import klox.syntax.Scanner
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.system.exitProcess

/**
 * Whether we have previously encountered an error while trying to run code or not.
 */
var hadError = false

fun main(args: Array<String>) = when {
  args.size > 1 -> {
    println("Usage: klox [file]")
    // For exit codes, we use the conventions defined in the Unix "sysexits.h" header.
    exitProcess(64)
  }
  args.size == 1 -> runFile(args[0])
  else -> runPrompt()
}

/**
 * Run the given file.
 */
fun runFile(path: String) {
  if (hadError) {
    exitProcess(65)
  }

  val file = File(path)
  run(file.readText())
}

/**
 * Run code in a REPL.
 */
fun runPrompt() {
  // The prefix for REPL-level commands. This should not conflict with what constitutes
  // a valid expression in the language.
  val commandPrefix = ":"

  val reader = BufferedReader(InputStreamReader(System.`in`))
  while (true) {
    print("> ")
    val line = reader.readLine() ?: break
    when (line.removePrefix(commandPrefix)) {
      "?" -> println(":q to quit")
      "q" -> break
      "quit" -> break
      else -> {
        run(line)
        // Reset the error flag so that the entire session isn't killed if a user makes
        // a mistake.
        hadError = false
      }
    }
  }
}

/**
 * Execute the given source code.
 */
fun run(source: String) {
  val scanner = Scanner(source)
  val tokens = scanner.scanTokens()

  for (token in tokens) {
    // Just print the token for now.
    println(token)
  }
}
