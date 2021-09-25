package klox

import klox.syntax.Token
import klox.syntax.TokenType

/**
 * Report a generic error.
 */
fun error(line: Int, message: String) = report(line, "", message)

/**
 * Report an error involving a token.
 */
fun error(token: Token, message: String) =
  if (token.type == TokenType.EOF) {
    report(token.line, " at end", message)
  } else {
    report(token.line, " at '${token.lexeme}'", message)
  }

/**
 * Report an error.
 *
 * @param line The line on which the error occurred.
 * @param where A description of where the error occurred.
 * @param message The error message to report.
 */
fun report(line: Int, where: String, message: String) {
  System.err.println("[line $line] Error$where: $message")
  hadError = true
}
