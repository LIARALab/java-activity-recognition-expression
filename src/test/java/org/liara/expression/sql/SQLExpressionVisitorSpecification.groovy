package org.liara.expression.sql

import org.liara.expression.Expression
import org.liara.expression.ExpressionFactory
import spock.lang.Specification

class SQLExpressionVisitorSpecification extends Specification {
  def "#SQLExpressionVisitor create a new empty visitor" () {
    expect: "#SQLExpressionTranspiler to create a new empty visitor"
    new SQLExpressionTranspiler().resultingSQL == ""
  }

  def "#visit successfully render booleans constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit boolean constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant(true),
      expression.constant((Boolean)true),
      expression.constant(false),
      expression.constant((Boolean) false),
      expression.constant((Boolean) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["1", "1", "0", "0", "NULL"]
  }

  def "#visit successfully render byte constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit byte constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((byte) 10),
      expression.constant((byte) 32),
      expression.constant((byte) -24),
      expression.constant((Byte) 26),
      expression.constant((Byte) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#visit successfully render short constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit short constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((short) 10),
      expression.constant((short) 32),
      expression.constant((short) -24),
      expression.constant((Short) 26),
      expression.constant((Short) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#visit successfully render int constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit int constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((int) 10),
      expression.constant((int) 32),
      expression.constant((int) -24),
      expression.constant((Integer) 26),
      expression.constant((Integer) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#visit successfully render long constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit long constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((long) 10),
      expression.constant((long) 32),
      expression.constant((long) -24),
      expression.constant((Long) 26),
      expression.constant((Long) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#visit successfully render float constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit float constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((float) 10.36),
      expression.constant((float) 32.18),
      expression.constant((float) -24.231),
      expression.constant((Float) 26.14),
      expression.constant((Float) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10.36", "32.18", "-24.231", "26.14", "NULL"]
  }

  def "#visit successfully render double constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit double constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((double) 10.36),
      expression.constant((double) 32.18),
      expression.constant((double) -24.231),
      expression.constant((Double) 26.14),
      expression.constant((Double) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["10.36", "32.18", "-24.231", "26.14", "NULL"]
  }

  def "#visit successfully render char constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit char constants"
    final List<String> results = []
    final List<Expression> expressions = [
      expression.constant((char) 'a'),
      expression.constant((char) 'b'),
      expression.constant((char) '\''),
      expression.constant(new Character((char) 'à')),
      expression.constant((Character) null)
    ]

    for (final Expression visitable : expressions) {
      visitor.visit(visitable)
      results.add(visitor.resultingSQL)
      visitor.clear()
    }

    then: "we expect that the visitor successfully rendered the visited expressions"
    results == ["'a'", "'b'", "'\''", "'à'", "NULL"]
  }

  def "#visit successfully render algebraic operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit a complex algebraic operation"
    visitor.visit(
      expression.add(
        expression.multiply(Arrays.asList(
          expression.constant(5),
          expression.add(
            expression.constant(6),
            expression.subtract(
              expression.constant(8),
              expression.constant(3)
            )
          ),
          expression.divide(
            expression.constant(1),
            expression.constant(6)
          )
        )),
        expression.constant(3)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    visitor.resultingSQL == "5 * (6 + 8 - 3) * 1 / 6 + 3"
  }

  def "#visit successfully render bitwise operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit a complex bitwise operation"
    visitor.visit(
      expression.add(
        expression.bitwiseOr(Arrays.asList(
          expression.constant(5),
          expression.bitwiseXor(
            expression.constant(6),
            expression.bitwiseOr(
              expression.constant(8),
              expression.bitwiseNot(expression.constant(3))
            )
          ),
          expression.bitwiseAnd(
            expression.constant(1),
            expression.constant(6)
          )
        )),
        expression.constant(3)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    visitor.resultingSQL == "(5 | 6 ^ (8 | ~ 3) | 1 & 6) + 3"
  }

  def "#visit successfully render logic operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler visitor = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory expression = new ExpressionFactory()

    when: "we visit a complex logic operation"
    visitor.visit(
      expression.or(
        expression.not(
          expression.and(Arrays.asList(
            expression.constant(true),
            expression.xor(
              expression.constant(false),
              expression.and(
                expression.constant(true),
                expression.not(expression.constant(false))
              )
            ),
            expression.or(
              expression.constant(true),
              expression.not(expression.not(expression.constant(true)))
            )
          ))
        ),
        expression.constant(false)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    visitor.resultingSQL == "NOT (1 AND (0 XOR 1 AND NOT 0) AND (1 OR NOT NOT 1)) OR 0"
  }
}
