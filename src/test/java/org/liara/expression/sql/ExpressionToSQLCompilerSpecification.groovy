package org.liara.expression.sql

import org.liara.expression.Expression
import org.liara.expression.ExpressionFactory
import spock.lang.Specification

class ExpressionToSQLCompilerSpecification
        extends Specification {
    def "#compile successfully render booleans constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull(true),
                factory.nullable(true),
                factory.nonnull(false),
                factory.nullable(false),
                factory.nullable((Boolean) null)
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

    def "#compile successfully render byte constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((byte) 10),
                factory.nonnull((byte) 32),
                factory.nonnull((byte) -24),
                factory.nullable((Byte) 26),
                factory.nullable((Byte) null)
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

    def "#compile successfully render short constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((short) 10),
                factory.nonnull((short) 32),
                factory.nonnull((short) -24),
                factory.nullable((Short) 26),
                factory.nullable((Short) null)
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

    def "#compile successfully render int constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((int) 10),
                factory.nonnull((int) 32),
                factory.nonnull((int) -24),
                factory.nullable((Integer) 26),
                factory.nullable((Integer) null)
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

    def "#compile successfully render long constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((long) 10),
                factory.nonnull((long) 32),
                factory.nonnull((long) -24),
                factory.nullable((Long) 26),
                factory.nullable((Long) null)
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

    def "#compile successfully render float constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((float) 10.36),
                factory.nonnull((float) 32.18),
                factory.nonnull((float) -24.231),
                factory.nullable((Float) 26.14),
                factory.nullable((Float) null)
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

    def "#compile successfully render double constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((double) 10.36),
                factory.nonnull((double) 32.18),
                factory.nonnull((double) -24.231),
                factory.nullable((Double) 26.14),
                factory.nullable((Double) null)
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

    def "#compile successfully render char constants"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "a selection of expression"
        final List<Expression> expressions = [
                factory.nonnull((char) 'a'),
                factory.nonnull((char) 'b'),
                factory.nonnull((char) '\''),
                factory.nullable(new Character((char) '"')),
                factory.nullable((Character) null)
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

    def "#compile successfully render algebraic operations"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "an expression"
        final Expression expression = factory.add(
                factory.multiply(Arrays.asList(
                        factory.nonnull(5),
                        factory.add(
                                factory.nonnull(6),
                                factory.subtract(
                                        factory.nonnull(8),
                                        factory.nonnull(3)
                                )
                        ),
                        factory.divide(
                                factory.nonnull(1),
                                factory.nonnull(6)
                        )
                )),
                factory.nonnull(3)
        )

        when: "we compile a complex algebraic operation"
        final StringBuilder output = new StringBuilder()

        compiler.setExpression(expression)
        compiler.compile(output)

        then: "we expect that the compiler successfully rendered the visited expressions"
        output.toString() == "5 * (6 + 8 - 3) * 1 / 6 + 3"
    }

    def "#compile successfully render bitwise operations"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "an expression"
        final Expression expression = factory.add(
                factory.bitwiseOr(Arrays.asList(
                        factory.nonnull(5),
                        factory.bitwiseXor(
                                factory.nonnull(6),
                                factory.bitwiseOr(
                                        factory.nonnull(8),
                                        factory.bitwiseNot(factory.nonnull(3))
                                )
                        ),
                        factory.bitwiseAnd(
                                factory.nonnull(1),
                                factory.nonnull(6)
                        )
                )),
                factory.nonnull(3)
        )

        when: "we compile a complex bitwise operation"
        final StringBuilder output = new StringBuilder()

        compiler.setExpression(expression)
        compiler.compile(output)

        then: "we expect that the compiler successfully rendered the visited expressions"
        output.toString() == "(5 | 6 ^ (8 | ~ 3) | 1 & 6) + 3"
    }

    def "#compile successfully render logic operations"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "an expression"
        final Expression expression = factory.or(
                factory.not(
                        factory.and(Arrays.asList(
                                factory.nonnull(true),
                                factory.xor(
                                        factory.nonnull(false),
                                        factory.and(
                                                factory.nonnull(true),
                                                factory.not(factory.nonnull(false))
                                        )
                                ),
                                factory.or(
                                        factory.nonnull(true),
                                        factory.not(factory.not(factory.nonnull(true)))
                                )
                        ))
                ),
                factory.nonnull(false)
        )

        when: "we compile the logic operation"
        final StringBuilder output = new StringBuilder()

        compiler.setExpression(expression)
        compiler.compile(output)

        then: "we expect that the compiler successfully rendered the visited expressions"
        output.toString() == "NOT (1 AND (0 XOR 1 AND NOT 0) AND (1 OR NOT NOT 1)) OR 0"
    }

    def "#compile successfully render range operations"() {
        given: "an SQL expression compiler"
        final ExpressionToSQLCompiler compiler = new ExpressionToSQLCompiler()

        and: "an expression factory"
        final ExpressionFactory factory = new ExpressionFactory()

        and: "an expression"
        final Expression expression = factory.between(
                factory.bitwiseOr(Arrays.asList(
                        factory.nonnull(5),
                        factory.bitwiseAnd(
                                factory.nonnull(1),
                                factory.nonnull(6)
                        )
                )),
                factory.bitwiseXor(
                        factory.nonnull(6),
                        factory.bitwiseOr(
                                factory.nonnull(8),
                                factory.bitwiseNot(factory.nonnull(3))
                        )
                ),
                factory.nonnull(3)
        )

        when: "we compile a range operation"
        final StringBuilder output = new StringBuilder()

        compiler.setExpression(expression)
        compiler.compile(output)

        then: "we expect that the compiler successfully rendered the range expression"
        output.toString() == "5 | 1 & 6 BETWEEN 6 ^ (8 | ~ 3) AND 3"
    }
}
