package org.liara.expression;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.MinLen;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.expression.operation.BinaryOperation;
import org.liara.expression.operation.Function;
import org.liara.expression.operation.Operator;
import org.liara.expression.operation.Range;
import org.liara.expression.operation.SequentialOperation;
import org.liara.expression.operation.UnaryOperation;

public class ExpressionFactory {
  /**
   * Return the given expression.
   * 
   * @param value An expression.
   * 
   * @return The given expression.
   */
  public <Result> @NonNull Expression<Result>  identity (@NonNull final Expression<Result>  value) {
    return value;
  }

  public <Result> @NonNull Expression<Result> placeholder (@NonNull final Primitive<Result> type) {
    return new Placeholder<>(type);
  }

  /**
   * Returns a constant of the given type.
   *
   * @param type Type of constant to instantiate.
   * @param value The value of the constant to instantiate.
   *
   * @return A constant of the given type with the given value.
   */
  public <Result> @NonNull Expression<Result> constant(
      @NonNull final Primitive<Result> type,
      final Result value
  ) {
    return new Constant<>(type, value);
  }

  public @NonNull Expression<@Nullable Byte> nullable (@Nullable final Byte value) {
    return constant(Primitives.NULLABLE_BYTE, value);
  }

  public @NonNull Expression<@Nullable Short> nullable (@Nullable final Short value) {
    return constant(Primitives.NULLABLE_SHORT, value);
  }

  public @NonNull Expression<@Nullable Integer> nullable (@Nullable final Integer value) {
    return constant(Primitives.NULLABLE_INTEGER, value);
  }

  public @NonNull Expression<@Nullable Long> nullable (@Nullable final Long value) {
    return constant(Primitives.NULLABLE_LONG, value);
  }

  public @NonNull Expression<@Nullable Float> nullable (@Nullable final Float value) {
    return constant(Primitives.NULLABLE_FLOAT, value);
  }

  public @NonNull Expression<@Nullable Double> nullable (@Nullable final Double value) {
    return constant(Primitives.NULLABLE_DOUBLE, value);
  }

  public @NonNull Expression<@Nullable Character> nullable (@Nullable final Character value) {
    return constant(Primitives.NULLABLE_CHARACTER, value);
  }

  public @NonNull Expression<@Nullable Boolean> nullable (@Nullable final Boolean value) {
    return constant(Primitives.NULLABLE_BOOLEAN, value);
  }

  public @NonNull Expression<@Nullable String> nullable (@Nullable final String value) {
    return constant(Primitives.NULLABLE_STRING, value);
  }

  public @NonNull Expression<@Nullable ZonedDateTime> nullable(@Nullable final ZonedDateTime value) {
    return constant(Primitives.NULLABLE_DATE_TIME, value);
  }

  public @NonNull Expression<@Nullable LocalDate> nullable(@Nullable final LocalDate value) {
    return constant(Primitives.NULLABLE_DATE, value);
  }

  public @NonNull Expression<@Nullable LocalTime> nullable(@Nullable final LocalTime value) {
    return constant(Primitives.NULLABLE_TIME, value);
  }

  public @NonNull Expression<@NonNull Byte> nonnull(final byte value) {
    return constant(Primitives.BYTE, value);
  }

  public @NonNull Expression<@NonNull Short> nonnull(final short value) {
    return constant(Primitives.SHORT, value);
  }

  public @NonNull Expression<@NonNull Integer> nonnull(final int value) {
    return constant(Primitives.INTEGER, value);
  }

  public @NonNull Expression<@NonNull Long> nonnull(final long value) {
    return constant(Primitives.LONG, value);
  }

  public @NonNull Expression<@NonNull Float> nonnull(final float value) {
    return constant(Primitives.FLOAT, value);
  }

  public @NonNull Expression<@NonNull Double> nonnull(final double value) {
    return constant(Primitives.DOUBLE, value);
  }

  public @NonNull Expression<@NonNull Character> nonnull(final char value) {
    return constant(Primitives.CHARACTER, value);
  }

  public @NonNull Expression<@NonNull Boolean> nonnull(final boolean value) {
    return constant(Primitives.BOOLEAN, value);
  }

  public @NonNull Expression<@NonNull String> nonnull(@NonNull final String value) {
    return constant(Primitives.STRING, value);
  }

  public @NonNull Expression<@NonNull ZonedDateTime> nonnull(@NonNull final ZonedDateTime value) {
    return constant(Primitives.DATE_TIME, value);
  }

  public @NonNull Expression<@NonNull LocalDate> nonnull(@NonNull final LocalDate value) {
    return constant(Primitives.DATE, value);
  }

  public @NonNull Expression<@NonNull LocalTime> nonnull(@NonNull final LocalTime value) {
    return constant(Primitives.TIME, value);
  }

  public <Value extends Number> @NonNull Expression<@NonNull Value> add(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.ADDITION,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> add(
      @NonNull @MinLen(1) final List<? extends @NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.ADDITION,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> subtract(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.SUBTRACTION,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> subtract (
      @NonNull @MinLen(1) final List<? extends @NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.SUBTRACTION,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> multiply (
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.MULTIPLICATION,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> multiply(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.MULTIPLICATION,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> divide(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.DIVISION,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> divide(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.DIVISION,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> modulus(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.MODULUS,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> modulus(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.MODULUS,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseOr(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.BITWISE_OR,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseOr(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.BITWISE_OR,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseAnd(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.BITWISE_AND,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseAnd(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.BITWISE_AND,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseXor(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    return new SequentialOperation<>(
        leftOperand.getResultType(),
        Operator.BITWISE_XOR,
        leftOperand,
        rightOperand
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseXor(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    return new SequentialOperation<>(
        operands.get(0).getResultType(),
        Operator.BITWISE_XOR,
        operands
    );
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> minus(
      @NonNull final Expression<@NonNull Value> value
  ) {
    return new UnaryOperation<>(value.getResultType(), Operator.MINUS, value);
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> plus(
      @NonNull final Expression<@NonNull Value> value
  ) {
    return new UnaryOperation<>(value.getResultType(), Operator.PLUS, value);
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseNot(
      @NonNull final Expression<@NonNull Value> value
  ) {
    return new UnaryOperation<>(value.getResultType(), Operator.BITWISE_NOT, value);
  }

  public @NonNull Expression<@NonNull Boolean> not(
      @NonNull final Expression<@NonNull Boolean> predicate
  ) {
    return new UnaryOperation<>(Primitives.BOOLEAN, Operator.NOT, predicate);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> between(
      @NonNull final Expression<@NonNull Value> target,
      @NonNull final Expression<@NonNull Value> lower,
      @NonNull final Expression<@NonNull Value> upper
  ) {
    return new Range<>(target, lower, upper);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> greaterThan(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.GREATER_THAN,
        left, right
    );
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> greaterThanOrEqual(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.GREATER_THAN_OR_EQUAL,
        left, right
    );
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> lessThan(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.LESS_THAN,
        left, right
    );
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> lessThanOrEqual(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.LESS_THAN_OR_EQUAL,
        left, right
    );
  }

  public <Value> @NonNull Expression<@NonNull Boolean> equal(
      @NonNull final Expression<Value> left,
      @NonNull final Expression<Value> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.EQUAL,
        left, right
    );
  }

  public @NonNull Expression<@NonNull Boolean> like(
      @NonNull final Expression<@NonNull String> left,
      @NonNull final Expression<@NonNull String> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.LIKE,
        left, right
    );
  }

  public @NonNull Expression<@NonNull Boolean> regexp(
      @NonNull final Expression<@NonNull String> left,
      @NonNull final Expression<@NonNull String> right
  ) {
    return new BinaryOperation<>(
        Primitives.BOOLEAN,
        Operator.REGEXP,
        left, right
    );
  }

  public <Value> @NonNull Expression<Value> function(
      @NonNull final Primitive<Value> expectedType,
      @NonNull final String identifier,
      @NonNull final List<@NonNull ? extends Expression<?>> parameters
  ) {
    return new Function<>(
        expectedType,
        identifier,
        parameters
    );
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    return new SequentialOperation<>(
        Primitives.BOOLEAN,
        Operator.AND,
        left, right
    );
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    switch (predicates.length) {
      case 0: return nonnull(true);
      case 1: return predicates[0];
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.AND, predicates);
    }
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    switch (predicates.size()) {
      case 0: return nonnull(true);
      case 1: return predicates.get(0);
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.AND, predicates);
    }
  }

  public @NonNull Expression<@NonNull Boolean> or(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    return new SequentialOperation<>(
        Primitives.BOOLEAN,
        Operator.OR,
        left, right
    );
  }

  public @NonNull Expression<@NonNull Boolean> or (
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    switch (predicates.length) {
      case 0: return nonnull(true);
      case 1: return predicates[0];
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.OR, predicates);
    }
  }

  public @NonNull Expression<@NonNull Boolean> or(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    switch (predicates.size()) {
      case 0: return nonnull(true);
      case 1: return predicates.get(0);
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.OR, predicates);
    }
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    return new SequentialOperation<>(
        Primitives.BOOLEAN,
        Operator.XOR,
        left, right
    );
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    switch (predicates.length) {
      case 0: return nonnull(true);
      case 1: return predicates[0];
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.XOR, predicates);
    }
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    switch (predicates.size()) {
      case 0: return nonnull(true);
      case 1: return predicates.get(0);
      default: return new SequentialOperation<>(Primitives.BOOLEAN, Operator.XOR, predicates);
    }
  }
}
