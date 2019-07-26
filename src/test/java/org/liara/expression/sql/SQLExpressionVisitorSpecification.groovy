package org.liara.expression.sql

import org.liara.expression.Expression
import org.liara.expression.ExpressionFactory
import spock.lang.Specification

class SQLExpressionVisitorSpecification extends Specification {
  def "#transpile successfully render booleans constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant(true),
      factory.nullableConstant(true),
      factory.nonNullConstant(false),
      factory.nullableConstant(false),
      factory.nullableConstant((Boolean) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["1", "1", "0", "0", "NULL"]
  }

  def "#transpile successfully render byte constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((byte) 10),
      factory.nonNullConstant((byte) 32),
      factory.nonNullConstant((byte) -24),
      factory.nullableConstant((Byte) 26),
      factory.nullableConstant((Byte) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#transpile successfully render short constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((short) 10),
      factory.nonNullConstant((short) 32),
      factory.nonNullConstant((short) -24),
      factory.nullableConstant((Short) 26),
      factory.nullableConstant((Short) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#transpile successfully render int constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((int) 10),
      factory.nonNullConstant((int) 32),
      factory.nonNullConstant((int) -24),
      factory.nullableConstant((Integer) 26),
      factory.nullableConstant((Integer) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#transpile successfully render long constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((long) 10),
      factory.nonNullConstant((long) 32),
      factory.nonNullConstant((long) -24),
      factory.nullableConstant((Long) 26),
      factory.nullableConstant((Long) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10", "32", "-24", "26", "NULL"]
  }

  def "#transpile successfully render float constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((float) 10.36),
      factory.nonNullConstant((float) 32.18),
      factory.nonNullConstant((float) -24.231),
      factory.nullableConstant((Float) 26.14),
      factory.nullableConstant((Float) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10.36", "32.18", "-24.231", "26.14", "NULL"]
  }

  def "#transpile successfully render double constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((double) 10.36),
      factory.nonNullConstant((double) 32.18),
      factory.nonNullConstant((double) -24.231),
      factory.nullableConstant((Double) 26.14),
      factory.nullableConstant((Double) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["10.36", "32.18", "-24.231", "26.14", "NULL"]
  }

  def "#transpile successfully render char constants" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "a selection of expression"
    final List<Expression> expressions = [
      factory.nonNullConstant((char) 'a'),
      factory.nonNullConstant((char) 'b'),
      factory.nonNullConstant((char) '\''),
      factory.nullableConstant(new Character((char) '"')),
      factory.nullableConstant((Character) null)
    ]

    when: "we visit the given expressions"
    final List<String> results = []

    for (final Expression expression : expressions) {
      results.add(transpiler.transpile(expression))
    }

    then: "we expect that the visitor successfully render the given expressions"
    results == ["\"a\"", "\"b\"", "\"'\"", "\"\\\"\"", "NULL"]
  }

  def "#transpile successfully render algebraic operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    when: "we transpile a complex algebraic operation"
    final String result = transpiler.transpile(
      factory.add(
        factory.multiply(Arrays.asList(
          factory.nonNullConstant(5),
          factory.add(
            factory.nonNullConstant(6),
            factory.subtract(
              factory.nonNullConstant(8),
              factory.nonNullConstant(3)
            )
          ),
          factory.divide(
            factory.nonNullConstant(1),
            factory.nonNullConstant(6)
          )
        )),
        factory.nonNullConstant(3)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    result == "5 * (6 + 8 - 3) * 1 / 6 + 3"
  }

  def "#transpile successfully render bitwise operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    when: "we transpile a complex bitwise operation"
    final String result = transpiler.transpile(
      factory.add(
        factory.bitwiseOr(Arrays.asList(
          factory.nonNullConstant(5),
          factory.bitwiseXor(
            factory.nonNullConstant(6),
            factory.bitwiseOr(
              factory.nonNullConstant(8),
              factory.bitwiseNot(factory.nonNullConstant(3))
            )
          ),
          factory.bitwiseAnd(
            factory.nonNullConstant(1),
            factory.nonNullConstant(6)
          )
        )),
        factory.nonNullConstant(3)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    result == "(5 | 6 ^ (8 | ~ 3) | 1 & 6) + 3"
  }

  def "#transpile successfully render logic operations" () {
    given: "an SQL expression visitor"
    final SQLExpressionTranspiler transpiler = new SQLExpressionTranspiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    when: "we visit a complex logic operation"
    final String result = transpiler.transpile(
      factory.or(
        factory.not(
          factory.and(Arrays.asList(
            factory.nonNullConstant(true),
            factory.xor(
              factory.nonNullConstant(false),
              factory.and(
                factory.nonNullConstant(true),
                factory.not(factory.nonNullConstant(false))
              )
            ),
            factory.or(
              factory.nonNullConstant(true),
              factory.not(factory.not(factory.nonNullConstant(true)))
            )
          ))
        ),
        factory.nonNullConstant(false)
      )
    )

    then: "we expect that the visitor successfully rendered the visited expressions"
    result == "NOT (1 AND (0 XOR 1 AND NOT 0) AND (1 OR NOT NOT 1)) OR 0"
  }
}
