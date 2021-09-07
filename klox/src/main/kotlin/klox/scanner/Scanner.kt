package klox.scanner

import klox.error
import klox.scanner.TokenType.*

/**
 * The lexical analyser for Lox source code that transforms a string of source code into
 * a list of tokens.
 *
 * See the [Token] class for information on tokens.
 *
 * @param source The source code to lexically analyse.
 */
class Scanner(private val source: String) {
  /**
   * The tokens scanned from the original source code.
   */
  private val tokens: MutableList<Token> = arrayListOf()

  /**
   * The character position of the start of the current token.
   */
  private var start = 0

  /**
   * The current character position in the original source code.
   */
  private var current = 0

  /**
   * The current line position in the original source code.
   */
  private var line = 1

  /**
   * A map from reserved words to their corresponding token type.
   */
  private val reservedWords: Map<String, TokenType> = mapOf(
    // Boolean Operators
    Pair("and", AND),
    Pair("or", OR),

    // Literals
    Pair("true", TRUE),
    Pair("false", FALSE),
    Pair("nil", NIL),

    // Keywords
    Pair("var", VAR),
    Pair("fun", FUN),
    Pair("return", RETURN),
    Pair("class", CLASS),
    Pair("super", SUPER),
    Pair("this", THIS),
    Pair("if", IF),
    Pair("else", ELSE),
    Pair("for", FOR),
    Pair("while", WHILE),
    Pair("print", PRINT),
  )

  /**
   * Convert the underlying source code into a list of tokens.
   */
  fun scanTokens(): List<Token> {
    while (!isAtEnd()) {
      // Keep track of the position of the current token.
      start = current
      scanToken()
    }

    tokens.add(Token(EOF, "", null, line))
    return tokens
  }

  /**
   * Scan a single token.
   */
  private fun scanToken() {
    when (val char = advance()) {
      // Ignore whitespace.
      in listOf(' ', '\r', '\t') -> return
      // Ignore newlines but increment the line counter.
      '\n' -> line++

      // Punctuation
      '(' -> addToken(LEFT_PAREN)
      ')' -> addToken(RIGHT_PAREN)
      '{' -> addToken(LEFT_BRACE)
      '}' -> addToken(RIGHT_BRACE)
      ';' -> addToken(SEMICOLON)
      ',' -> addToken(COMMA)
      '.' -> addToken(DOT)

      // Arithmetic Operators
      '+' -> addToken(PLUS)
      '-' -> addToken(MINUS)
      '*' -> {
        if (!match('/')) {
          addToken(STAR)
        } else {
          error(line, "Unexpected closing block comment.")
        }
      }
      '/' ->
        when {
          match('/') -> lineComment()
          match('*') -> blockComment()
          else -> addToken(SLASH)
        }

      // Boolean Operators
      '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
      '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
      '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
      '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)

      // Literals
      '"' -> string()
      in '0'..'9' -> number()

      else -> {
        if (char.isAlpha()) {
          identifier()
        } else {
          error(line, "Unexpected character.")
        }
      }
    }
  }

  /**
   * Consume a string literal.
   */
  private fun string() {
    // Advance forwards to the closing ".
    while (peek() != '"' && !isAtEnd()) {
      if (peek() == '\n') line++
      advance()
    }

    // If we never found the closing ", then we have an error.
    if (isAtEnd()) {
      error(line, "Unterminated string.")
      return
    }

    // Consume the closing ".
    advance()

    val value = source.subSequence(start + 1, current - 1)
    addToken(STRING, value)
  }

  /**
   * Consume a number literal.
   */
  private fun number() {
    while (peek()?.isDigit() == true) advance()

    if (peek() == '.' && peekNext()?.isDigit() == true) {
      advance()

      while (peek()?.isDigit() == true) advance()
    }

    addToken(NUMBER, source.subSequence(start, current).toString().toDouble())
  }

  /**
   * Consume an identifier, or a keyword if the identifier is reserved.
   */
  private fun identifier() {
    while (peek()?.isAlphaNumeric() == true) advance()

    val text = source.subSequence(start, current)
    val type = reservedWords[text] ?: IDENTIFIER
    addToken(type)
  }

  /**
   * Ignore a line comment.
   */
  private fun lineComment() {
    while (peek() != '\n' && !isAtEnd()) advance()
  }

  /**
   * Ignore a block comment with arbitrary nesting.
   */
  private fun blockComment() {
    var level = 1
    while (level > 0 && !isAtEnd()) {
      val char = advance()
      when {
        char == '/' && match('*') -> level++
        char == '*' && match('/') -> level--
      }
    }

    // If we never found all the terminating */'s, then we have an error.
    if (level != 0) {
      error("Unterminated block comment.")
    }
  }

  /**
   * Get the current character and advance the scanner.
   */
  private fun advance(): Char = source[current++]

  /**
   * Advance the scanner if the current character matches the given character.
   * @param expected the character to match against.
   */
  private fun match(expected: Char): Boolean {
    if (isAtEnd() || source[current] != expected) return false

    current++
    return true
  }

  /**
   * Get the current character without advancing the scanner.
   */
  private fun peek(): Char? {
    if (isAtEnd()) return null
    return source[current]
  }

  /**
   * Get the next character without advancing the scanner.
   */
  private fun peekNext(): Char? {
    if (current + 1 >= source.length) return null
    return source[current + 1]
  }

  private fun addToken(type: TokenType) {
    addToken(type, null)
  }

  private fun addToken(type: TokenType, literal: Any?) {
    val text = source.subSequence(start, current)
    // TODO: Decide whether casting CharSequence to String is a good idea or not.
    tokens.add(Token(type, text as String, literal, line))
  }

  /**
   * Check whether the scanner has reached the end of the input.
   */
  private fun isAtEnd(): Boolean = current >= source.length
}
