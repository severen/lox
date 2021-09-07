package klox.scanner

// TODO: Consider switching to Kotlin's Char.isLetter, Char.isDigit,
//       and Char.isLetterOrDigit functions.

/**
 * Check whether the given character is alphanumeric.
 */
fun Char.isAlphaNumeric(): Boolean = this.isAlpha() || this.isDigit()

/**
 * Check whether the given character is alphabetic.
 */
fun Char.isAlpha(): Boolean = this in 'a'..'z' || this in 'A'..'Z' || this == '_'

/**
 * Check whether the given character is a digit.
 */
fun Char.isDigit(): Boolean = this in '0'..'9'
