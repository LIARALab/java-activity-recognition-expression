package org.liara.expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.MinLen;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.expression.operation.Operation;
import org.liara.expression.operation.Operator;
import org.liara.expression.operation.StaticOperation;
import org.liara.expression.operation.StaticOperationBuilder;

public class ExpressionFactory {
  @NonNull
  private static final Class[] NUMBER_TYPE_PRIORITY = new Class[]{
      Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
      BigInteger.class, BigDecimal.class
  };

  @NonNull
  private final StaticOperationBuilder _staticOperationBuilder;

  /**
   * Instantiate a new expression factory.
   */
  public ExpressionFactory() {
    _staticOperationBuilder = new StaticOperationBuilder();
  }

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
    return new StaticPlaceholder<>(type);
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
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.ADDITION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> add(
      @NonNull @MinLen(1) final List<? extends @NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.ADDITION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> subtract(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.SUBTRACTION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> subtract (
      @NonNull @MinLen(1) final List<? extends @NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.SUBTRACTION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> multiply (
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.MULTIPLICATION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> multiply(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.MULTIPLICATION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> divide(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.DIVISION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> divide(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.DIVISION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> modulus(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.MODULUS);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> modulus(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.MODULUS);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseOr(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.BITWISE_OR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseOr(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.BITWISE_OR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseAnd(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.BITWISE_AND);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseAnd(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.BITWISE_AND);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseXor(
      @NonNull final Expression<@NonNull Value> leftOperand,
      @NonNull final Expression<@NonNull Value> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(leftOperand);
    _staticOperationBuilder.getOperands().add(rightOperand);
    _staticOperationBuilder.setOperator(Operator.BITWISE_XOR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseXor(
      @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull Value>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(operands);
    _staticOperationBuilder.setOperator(Operator.BITWISE_XOR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> minus(
      @NonNull final Expression<@NonNull Value> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(value);
    _staticOperationBuilder.setOperator(Operator.MINUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> plus(
      @NonNull final Expression<@NonNull Value> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(value);
    _staticOperationBuilder.setOperator(Operator.PLUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <@NonNull Value extends Number> @NonNull Expression<@NonNull Value> bitwiseNot(
      @NonNull final Expression<@NonNull Value> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(value);
    _staticOperationBuilder.setOperator(Operator.BITWISE_NOT);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public @NonNull Expression<@NonNull Boolean> not(
      @NonNull final Expression<@NonNull Boolean> predicate
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(predicate);
    _staticOperationBuilder.setOperator(Operator.NOT);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> between(
      @NonNull final Expression<@NonNull Value> target,
      @NonNull final Expression<@NonNull Value> lower,
      @NonNull final Expression<@NonNull Value> upper
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(target);
    _staticOperationBuilder.getOperands().add(lower);
    _staticOperationBuilder.getOperands().add(upper);
    _staticOperationBuilder.setOperator(Operator.BETWEEN);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> greaterThan(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.GREATER_THAN);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> greaterThanOrEqual(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.GREATER_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> lessThan(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.LESS_THAN);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value extends Comparable<? super Value>> @NonNull Expression<@NonNull Boolean> lessThanOrEqual(
      @NonNull final Expression<@NonNull Value> left,
      @NonNull final Expression<@NonNull Value> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.LESS_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value> @NonNull Expression<@NonNull Boolean> equal(
      @NonNull final Expression<Value> left,
      @NonNull final Expression<Value> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> like(
      @NonNull final Expression<@NonNull String> left,
      @NonNull final Expression<@NonNull String> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.LIKE);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> regexp(
      @NonNull final Expression<@NonNull String> left,
      @NonNull final Expression<@NonNull String> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.REGEXP);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Value> @NonNull Expression<Value> function(
      @NonNull final Primitive<Value> expectedType,
      @NonNull final String identifier,
      @NonNull final List<Expression<?>> parameters
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(new Identifier(identifier));
    _staticOperationBuilder.getOperands().addAll(parameters);
    _staticOperationBuilder.setOperator(Operator.FUNCTION);

    return _staticOperationBuilder.build(expectedType);
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.AND);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    switch (predicates.length) {
      case 0: return nonnull(true);
      case 1: return predicates[0];
      default:
        _staticOperationBuilder.clear();
        _staticOperationBuilder.setOperands(Arrays.asList(predicates));
        _staticOperationBuilder.setOperator(Operator.AND);

        return _staticOperationBuilder.build(Primitives.BOOLEAN);
    }
  }

  public @NonNull Expression<@NonNull Boolean> and(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    switch (predicates.size()) {
      case 0: return nonnull(true);
      case 1: return predicates.get(0);
      default:
        _staticOperationBuilder.clear();

        for (@NonNull final Expression<@NonNull Boolean> predicate : predicates) {
          _staticOperationBuilder.getOperands().add(predicate);
        }

        _staticOperationBuilder.setOperator(Operator.AND);

        return _staticOperationBuilder.build(Primitives.BOOLEAN);
    }
  }

  public @NonNull Expression<@NonNull Boolean> or(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.OR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> or (
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    switch (predicates.length) {
      case 0: return nonnull(true);
      case 1: return predicates[0];
      default:
        _staticOperationBuilder.clear();
        _staticOperationBuilder.setOperands(Arrays.asList(predicates));
        _staticOperationBuilder.setOperator(Operator.OR);

        return _staticOperationBuilder.build(Primitives.BOOLEAN);
    }
  }

  public @NonNull Expression<@NonNull Boolean> or(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    switch (predicates.size()) {
      case 0: return nonnull(true);
      case 1: return predicates.get(0);
      default:
        _staticOperationBuilder.clear();

        for (@NonNull final Expression<@NonNull Boolean> predicate : predicates) {
          _staticOperationBuilder.getOperands().add(predicate);
        }

        _staticOperationBuilder.setOperator(Operator.OR);

        return _staticOperationBuilder.build(Primitives.BOOLEAN);
    }
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final Expression<@NonNull Boolean> left,
      @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.getOperands().add(left);
    _staticOperationBuilder.getOperands().add(right);
    _staticOperationBuilder.setOperator(Operator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final Expression<@NonNull Boolean>[] predicates
  ) {
    if (predicates.length <= 0) {
      return nonnull(true);
    }

    _staticOperationBuilder.clear();
    _staticOperationBuilder.setOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(Operator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> xor(
      @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) {
      return nonnull(true);
    }

    _staticOperationBuilder.clear();

    for (@NonNull final Expression<@NonNull Boolean> predicate : predicates) {
      _staticOperationBuilder.getOperands().add(predicate);
    }

    _staticOperationBuilder.setOperator(Operator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public Expression<?> rewrite (
      @NonNull final Expression<?> expression,
      @NonNull final Expression<?>[] children
  ) {
    if (expression instanceof Identity) {
      return rewriteIdentity((Identity<?>) expression, children);
    } else if (expression instanceof StaticOperation) {
      return rewriteOperation((StaticOperation<?>) expression, children);
    } else {
      throw new Error("Unable to rewrite expression : " + expression.getClass().getName());
    }
  }

  private @NonNull Expression<?> rewriteOperation(
      @NonNull final StaticOperation<?> expression,
      @NonNull final Expression<?>[] children
  ) {
    return new StaticOperation<>(
        expression.getResultType(),
        expression.getOperator(),
        children
    );
  }

  private @NonNull Identity<?> rewriteIdentity (
      @NonNull final Identity<?> expression,
      @NonNull final Expression<?>[] children
  ) {
    if (children.length == 1) {
      return new Identity<>(children[0]);
    } else {
      throw new Error("Unable to rewrite identity.");
    }
  }
}
