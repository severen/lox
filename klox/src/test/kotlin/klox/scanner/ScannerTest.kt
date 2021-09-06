package klox.scanner

import klox.scanner.TokenType.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ScannerTest {
  /**
   * Helper function that tests scanning a single token on one line.
   */
  private fun scanToken(type: TokenType, lexeme: String) {
    val actual = Scanner(lexeme).scanTokens()
    val expected = listOf(
      Token(type, lexeme, null, 1),
      // EOF should always be present.
      Token(EOF, "", null, 1),
    )
    matchTokens(expected, actual)
  }

  /**
   * Helper function for testing that two lists of tokens match.
   */
  private fun matchTokens(expectedTokens: List<Token>, actualTokens: List<Token>) {
    for ((expected, actual) in expectedTokens.zip(actualTokens)) {
      matchToken(expected, actual)
    }
  }

  /**
   * Helper function for testing that two tokens match.
   */
  private fun matchToken(expected: Token, actual: Token) {
    assertEquals(expected.type, actual.type)
    assertEquals(expected.lexeme, actual.lexeme)
    assertEquals(expected.line, actual.line)
    assertEquals(expected.literal, actual.literal)
    // If all the fields match, then this should certainly be true.
    assertEquals(expected, actual)
  }

  @Test
  fun scanPunctuation() {
    val punctuation = listOf(
      Pair(LEFT_PAREN, "("),
      Pair(RIGHT_PAREN, ")"),
      Pair(LEFT_BRACE, "{"),
      Pair(RIGHT_BRACE, "}"),
      Pair(SEMICOLON, ";"),
      Pair(COMMA, ","),
      Pair(DOT, "."),
      Pair(EQUAL, "="),
    )
    for ((type, lexeme) in punctuation) {
      scanToken(type, lexeme)
    }
  }

  @Test
  fun scanArithmeticOperators() {
    val operators = listOf(
      Pair(PLUS, "+"),
      Pair(MINUS, "-"),
      Pair(STAR, "*"),
      Pair(SLASH, "/"),
    )
    for ((type, lexeme) in operators) {
      scanToken(type, lexeme)
    }
  }

  @Test
  fun scanBooleanOperators() {
    val operators = listOf(
      Pair(BANG, "!"),
      Pair(EQUAL_EQUAL, "=="),
      Pair(BANG_EQUAL, "!="),
      Pair(LESS, "<"),
      Pair(LESS_EQUAL, "<="),
      Pair(GREATER, ">"),
      Pair(GREATER_EQUAL, ">="),
    )
    for ((type, lexeme) in operators) {
      scanToken(type, lexeme)
    }
  }

  @Test
  fun scanEmpty() {
    val actual = Scanner("").scanTokens()
    val expected = listOf(Token(EOF, "", null, 1))
    matchTokens(expected, actual)
  }

  @Test
  fun scanString() {
    val actual = Scanner("\"Hello, world!\"").scanTokens()
    val expected = listOf(
      Token(STRING, "\"Hello, world!\"", "Hello, world!", 1),
      // EOF should always be present.
      Token(EOF, "", null, 1),
    )
    matchTokens(expected, actual)
  }

  @Test
  fun scanMultilineString() {
    val actual = Scanner("\"Hello, world!\nMy name is...\"").scanTokens()
    val expected = listOf(
      Token(
        STRING,
        "\"Hello, world!\nMy name is...\"",
        "Hello, world!\nMy name is...",
        2
      ),
      // EOF should always be present.
      Token(EOF, "", null, 2),
    )
    matchTokens(expected, actual)
  }

  @Test
  fun scanInts() {
    val actual = Scanner("0 1 12 1729").scanTokens()
    val expected = listOf(
      Token(NUMBER, "0", 0.0, 1),
      Token(NUMBER, "1", 1.0, 1),
      Token(NUMBER, "12", 12.0, 1),
      Token(NUMBER, "1729", 1729.0, 1),
      // EOF should always be present.
      Token(EOF, "", null, 1),
    )
    matchTokens(expected, actual);
  }

  @Test
  fun scanIdentifier() {
    val actual = Scanner("foo bar baz Vec2 Vec3").scanTokens()
    val expected = listOf(
      Token(IDENTIFIER, "foo", null, 1),
      Token(IDENTIFIER, "bar", null, 1),
      Token(IDENTIFIER, "baz", null, 1),
      Token(IDENTIFIER, "Vec2", null, 1),
      Token(IDENTIFIER, "Vec3", null, 1),
      // EOF should always be present.
      Token(EOF, "", null, 1),
    )
    matchTokens(expected, actual);
  }

  @Test
  fun ignoreComment() {
    val tokens = Scanner("// This is a comment...").scanTokens()
    val expected = listOf(Token(EOF, "", null, 1))
    assertEquals(expected, tokens)
  }
}
