package org.liara.expression.sql

import org.liara.expression.Expression
import org.liara.expression.ExpressionFactory
import spock.lang.Specification

class ExpressionToSQLCompilerSpecification
  extends Specification {
  def "#compile successfully render booleans constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "1, 1, 0, 0, NULL"
  }

  def "#compile successfully render byte constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10, 32, -24, 26, NULL"
  }

  def "#compile successfully render short constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10, 32, -24, 26, NULL"
  }

  def "#compile successfully render int constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10, 32, -24, 26, NULL"
  }

  def "#compile successfully render long constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10, 32, -24, 26, NULL"
  }

  def "#compile successfully render float constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10.36, 32.18, -24.231, 26.14, NULL"
  }

  def "#compile successfully render double constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "10.36, 32.18, -24.231, 26.14, NULL"
  }

  def "#compile successfully render char constants" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

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
    final StringBuilder output = new StringBuilder()
    final Iterator<Expression> iterator = expressions.iterator()

    while (iterator.hasNext()) {
      compiler.setExpression(iterator.next())
      compiler.compile(output)
      if (iterator.hasNext()) output.append(", ")
    }

    then: "we expect that the compiler successfully render the given expressions"
    output.toString() == "\"a\", \"b\", \"'\", \"\\\"\", NULL"
  }

  def "#compile successfully render algebraic operations" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression expression = factory.add(
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

    when: "we compile a complex algebraic operation"
    final StringBuilder output = new StringBuilder()

    compiler.setExpression(expression)
    compiler.compile(output)

    then: "we expect that the compiler successfully rendered the visited expressions"
    output.toString() == "5 * (6 + 8 - 3) * 1 / 6 + 3"
  }

  def "#compile successfully render bitwise operations" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression expression = factory.add(
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

    when: "we compile a complex bitwise operation"
    final StringBuilder output = new StringBuilder()

    compiler.setExpression(expression)
    compiler.compile(output)

    then: "we expect that the compiler successfully rendered the visited expressions"
    output.toString() == "(5 | 6 ^ (8 | ~ 3) | 1 & 6) + 3"
  }

  def "#compile successfully render logic operations" () {
    given: "an SQL expression compiler"
    final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

    and: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression expression = factory.or(
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

    when: "we compile the logic operation"
    final StringBuilder output = new StringBuilder()

    compiler.setExpression(expression)
    compiler.compile(output)

    then: "we expect that the compiler successfully rendered the visited expressions"
    output.toString() == "NOT (1 AND (0 XOR 1 AND NOT 0) AND (1 OR NOT NOT 1)) OR 0"
  }
}
