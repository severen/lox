package klox.syntax

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UProperty

/**
 * Check whether this character has the XID_START character property as defined by
 * Unicode Standard Annex #31.
 */
fun Char.isXidStart() =
  UCharacter.hasBinaryProperty(this.code, UProperty.XID_START)

/**
 * Check whether this character has the XID_CONTINUE character property as defined by
 * Unicode Standard Annex #31.
 */
fun Char.isXidContinue() =
  UCharacter.hasBinaryProperty(this.code, UProperty.XID_CONTINUE)

/**
 * Check whether this character has the PATTERN_WHITE_SPACE character property as
 * defined by Unicode Standard Annex #31.
 */
fun Char.isPatternWhiteSpace() =
  UCharacter.hasBinaryProperty(this.code, UProperty.PATTERN_WHITE_SPACE)

/**
 * Check whether this character is a decimal digit.
 *
 * This method is more restrictive than isDigit in the standard library, which considers
 * a 'digit' to be any Unicode character in the DECIMAL_DIGIT_NUMBER category. For
 * example, the standard library considers the Devanagari digit २ to be a digit,
 * while this method does not.
 */
fun Char.isDigit() = this in '0'..'9'

/**
 * Strip the Unix shebang line from the beginning of this string.
 */
fun String.stripShebang() =
  if (this.startsWith("#!")) {
    val endIndex = this.indexOf('\n')
    if (endIndex != -1) this.substring(endIndex + 1) else ""
  } else {
    this
  }
