package klox

/**
 * Report an error.
 */
fun error(line: Int, message: String) {
  report(line, "", message)
}

/**
 * Report an error.
 *
 * @param line The line on which the error occurred.
 * @param where The file in which the error occurred.
 * @param message The error message to report.
 */
fun report(line: Int, where: String, message: String) {
  System.err.println("[line $line] Error$where: $message")
  hadError = true
}
