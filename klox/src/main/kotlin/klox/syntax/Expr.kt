package klox.syntax

/**
 * The base class for all expressions in an abstract syntax tree of Lox source code.
 */
sealed class Expr {
  /**
   * Return a 'pretty' LISP-like representation of the abstract syntax tree formed by
   * this expression.
   */
  abstract fun toPrettyString(): String

  /**
   * An expression consisting of a single literal.
   *
   * @param value The value of the literal within the expression.
   */
  data class Literal(val value: Any?) : Expr() {
    override fun toPrettyString() = value?.toString() ?: "nil"
  }

  /**
   * An expression consisting of a unary operation and its (right) operand.
   *
   * @param operator The operator in the unary expression.
   * @param right The operand in the unary expression.
   */
  data class Unary(val operator: Token, val right: Expr) : Expr() {
    override fun toPrettyString() = "(${operator.lexeme} ${right.toPrettyString()})"
  }

  /**
   * An expression consisting of a binary operation and its left and right operands.
   *
   * @param left The left-hand operand of the binary expression.
   * @param operator The operator in the binary expression.
   * @param right The right-hand operand of the binary expression.
   */
  data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr() {
    override fun toPrettyString() =
      "(${operator.lexeme} ${left.toPrettyString()} ${right.toPrettyString()})"
  }

  /**
   * An expression consisting of a grouped (e.g. bracketed) subexpression.
   *
   * @param expr The subexpression within the grouping.
   */
  data class Grouping(val expr: Expr) : Expr() {
    override fun toPrettyString() = "(group ${expr.toPrettyString()})"
  }
}
