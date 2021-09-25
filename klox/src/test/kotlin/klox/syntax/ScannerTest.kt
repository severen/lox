package klox.syntax

import klox.syntax.TokenType.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ScannerTest {
  /**
   * The corresponding list of tokens for an empty program.
   */
  private val emptyProgram = listOf(Token(EOF, "", null, 1))

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
      Pair(AND, "and"),
      Pair(OR, "or"),
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
    matchTokens(expected, actual)
  }

  @Test
  fun scanDecimals() {
    val actual = Scanner("0.0 1.0 12.0 1729.0 3.14159").scanTokens()
    val expected = listOf(
      Token(NUMBER, "0.0", 0.0, 1),
      Token(NUMBER, "1.0", 1.0, 1),
      Token(NUMBER, "12.0", 12.0, 1),
      Token(NUMBER, "1729.0", 1729.0, 1),
      Token(NUMBER, "3.14159", 3.14159, 1),
      // EOF should always be present.
      Token(EOF, "", null, 1),
    )
    matchTokens(expected, actual)
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
    matchTokens(expected, actual)
  }

  @Test
  fun scanHelloWorldProgram() {
    val program = """
      fun main() {
        print "Hello, world!"
      }
    """.trimIndent()
    val actual = Scanner(program).scanTokens()
    val expected = listOf(
      Token(FUN, "fun", null, 1),
      Token(IDENTIFIER, "main", null, 1),
      Token(LEFT_PAREN, "(", null, 1),
      Token(RIGHT_PAREN, ")", null, 1),
      Token(LEFT_BRACE, "{", null, 1),
      Token(PRINT, "print", null, 2),
      Token(STRING, "\"Hello, world!\"", "Hello, world!", 2),
      Token(RIGHT_BRACE, "}", null, 3),
      // EOF should always be present.
      Token(EOF, "", null, 3),
    )
    matchTokens(expected, actual)
  }

  @Test
  fun ignoreLineComment() {
    val tokens = Scanner("// This is a line comment...").scanTokens()
    assertEquals(emptyProgram, tokens)
  }

  @Test
  fun ignoreBlockComment() {
    val tokens = Scanner("/* This is a block comment... */").scanTokens()
    assertEquals(emptyProgram, tokens)
  }

  @Test
  fun ignoreNestedBlockComment() {
    val tokens = Scanner("/* /* This is a nested block comment... */ */")
      .scanTokens()
    assertEquals(emptyProgram, tokens)
  }
  }
}
