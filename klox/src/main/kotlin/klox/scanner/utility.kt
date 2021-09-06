package klox.scanner

/**
 * Check whether the given character is alphanumeric.
 */
fun isAlphaNumeric(char: Char): Boolean = isAlpha(char) || isDigit(char)

/**
 * Check whether the given character is alphabetic.
 */
fun isAlpha(char: Char): Boolean = char in 'a'..'z' || char in 'A'..'Z' || char == '_'

/**
 * Check whether the given character is a digit.
 */
fun isDigit(char: Char): Boolean = char in '0'..'9'
