package klox.scanner

/**
 * The type of a [Token].
 */
enum class TokenType {
  // Punctuation
  LEFT_PAREN,
  RIGHT_PAREN,
  LEFT_BRACE,
  RIGHT_BRACE,
  SEMICOLON,
  COMMA,
  DOT,
  EQUAL,

  // Arithmetic Operators
  MINUS,
  PLUS,
  SLASH,
  STAR,

  // Boolean Operators
  BANG,
  BANG_EQUAL,
  EQUAL_EQUAL,
  GREATER,
  GREATER_EQUAL,
  LESS,
  LESS_EQUAL,
  AND,
  OR,

  // Literals
  IDENTIFIER,
  STRING,
  NUMBER,
  TRUE,
  FALSE,
  NIL,

  // Keywords
  VAR,
  FUN,
  RETURN,
  CLASS,
  SUPER,
  THIS,
  IF,
  ELSE,
  FOR,
  WHILE,
  PRINT,

  // Special token to indicate the end of a file.
  EOF,
}
