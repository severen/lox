package klox.syntax

/**
 * A token of Lox source code.
 *
 * @param type The type of the token.
 * @param lexeme The subsequence that matched this token in the original source code.
 * @param literal The literal value, or null if this token is not a literal.
 * @param line The line in the original source code from which this token was scanned.
 */
data class Token(
  val type: TokenType,
  val lexeme: String,
  val literal: Any?,
  val line: Int,
) {
  override fun toString(): String {
    return "$type $lexeme $literal"
  }
}
