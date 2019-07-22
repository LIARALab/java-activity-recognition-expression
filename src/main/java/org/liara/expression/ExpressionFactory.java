package org.liara.expression;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.MinLen;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.expression.operation.CommonOperator;
import org.liara.expression.operation.StaticOperationBuilder;
import org.liara.expression.function.StaticFunctionExpressionBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class ExpressionFactory
{
  @NonNull
  private static final Class[] NUMBER_TYPE_PRIORITY = new Class[]{
    Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
    BigInteger.class, BigDecimal.class
  };

  @NonNull
  private final StaticOperationBuilder _staticOperationBuilder;

  @NonNull
  private final StaticFunctionExpressionBuilder _staticFunctionExpressionBuilder;

  public ExpressionFactory () {
    _staticOperationBuilder = new StaticOperationBuilder();
    _staticFunctionExpressionBuilder = new StaticFunctionExpressionBuilder();
  }

  public <Type> @NonNull Expression<Type> identity (@NonNull final Expression<Type> value) {
    return value;
  }

  public <Type> @NonNull Expression<Type> placeholder (@NonNull final Primitive<Type> type) {
    return new Placeholder<>(type);
  }

  public <Type> @NonNull Expression<@Nullable Type> nullableConstant (
    @NonNull final Primitive<@Nullable Type> primitive,
    @Nullable final Type value
  ) { return new Constant<>(primitive, value); }

  public @NonNull Expression<@Nullable Byte> nullableConstant (@Nullable final Byte value) {
    return nullableConstant(Primitives.NULLABLE_BYTE, value);
  }

  public @NonNull Expression<@Nullable Short> nullableConstant (@Nullable final Short value) {
    return nullableConstant(Primitives.NULLABLE_SHORT, value);
  }

  public @NonNull Expression<@Nullable Integer> nullableConstant (@Nullable final Integer value) {
    return nullableConstant(Primitives.NULLABLE_INTEGER, value);
  }

  public @NonNull Expression<@Nullable Long> nullableConstant (@Nullable final Long value) {
    return nullableConstant(Primitives.NULLABLE_LONG, value);
  }

  public @NonNull Expression<@Nullable Float> nullableConstant (@Nullable final Float value) {
    return nullableConstant(Primitives.NULLABLE_FLOAT, value);
  }

  public @NonNull Expression<@Nullable Double> nullableConstant (@Nullable final Double value) {
    return nullableConstant(Primitives.NULLABLE_DOUBLE, value);
  }

  public @NonNull Expression<@Nullable Character> nullableConstant (
    @Nullable final Character value
  ) { return nullableConstant(Primitives.NULLABLE_CHARACTER, value); }

  public @NonNull Expression<@Nullable Boolean> nullableConstant (@Nullable final Boolean value) {
    return nullableConstant(Primitives.NULLABLE_BOOLEAN, value);
  }

  public @NonNull Expression<@Nullable String> nullableConstant (@Nullable final String value) {
    return nullableConstant(Primitives.STRING, value);
  }

  public @NonNull Expression<@Nullable ZonedDateTime> nullableConstant (
    @Nullable final ZonedDateTime value
  ) { return nullableConstant(Primitives.DATE_TIME, value); }

  public <Type> @NonNull Expression<@NonNull Type> nonNullConstant (
    @NonNull final Primitive<@NonNull Type> primitive,
    @NonNull final Type value
  ) { return new Constant<>(primitive, value); }

  public @NonNull Expression<@NonNull Byte> nonNullConstant (final byte value) {
    return nonNullConstant(Primitives.BYTE, value);
  }

  public @NonNull Expression<@NonNull Short> nonNullConstant (final short value) {
    return nonNullConstant(Primitives.SHORT, value);
  }

  public @NonNull Expression<@NonNull Integer> nonNullConstant (final int value) {
    return nonNullConstant(Primitives.INTEGER, value);
  }

  public @NonNull Expression<@NonNull Long> nonNullConstant (final long value) {
    return nonNullConstant(Primitives.LONG, value);
  }

  public @NonNull Expression<@NonNull Float> nonNullConstant (final float value) {
    return nonNullConstant(Primitives.FLOAT, value);
  }

  public @NonNull Expression<@NonNull Double> nonNullConstant (final double value) {
    return nonNullConstant(Primitives.DOUBLE, value);
  }

  public @NonNull Expression<@NonNull Character> nonNullConstant (final char value) {
    return nonNullConstant(Primitives.CHARACTER, value);
  }

  public @NonNull Expression<@NonNull Boolean> nonNullConstant (final boolean value) {
    return nonNullConstant(Primitives.BOOLEAN, value);
  }

  public @NonNull Expression<@Nullable String> nonNullConstant (@NonNull final String value) {
    return nonNullConstant(Primitives.STRING, value);
  }

  public @NonNull Expression<@Nullable ZonedDateTime> nonNullConstant (
    @NonNull final ZonedDateTime value
  ) { return nonNullConstant(Primitives.DATE_TIME, value); }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> add (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.ADDITION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> add (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.ADDITION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> subtract (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.SUBTRACTION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> subtract (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.SUBTRACTION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> multiply (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.MULTIPLICATION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> multiply (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.MULTIPLICATION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> divide (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.DIVISION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> divide (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.DIVISION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseOr (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_OR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseOr (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_OR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseAnd (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_AND);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseAnd (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_AND);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseXor (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_XOR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseXor (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_XOR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> minus (
    @NonNull final Expression<@NonNull T> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(value);
    _staticOperationBuilder.setOperator(CommonOperator.MINUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> plus (
    @NonNull final Expression<@NonNull T> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(value);
    _staticOperationBuilder.setOperator(CommonOperator.PLUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <@NonNull T extends Number> @NonNull Expression<@NonNull T> bitwiseNot (
    @NonNull final Expression<@NonNull T> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(value);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_NOT);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public @NonNull Expression<@NonNull Boolean> not (
    @NonNull final Expression<@NonNull Boolean> predicate
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(predicate);
    _staticOperationBuilder.setOperator(CommonOperator.NOT);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <@NonNull Compared, @NonNull Target extends Comparable<Compared>>
  @NonNull Expression<@NonNull Boolean> between (
    @NonNull final Expression<@NonNull Target> target,
    @NonNull final Expression<@NonNull Compared> lower,
    @NonNull final Expression<@NonNull Compared> upper
  ) {
    return and(
      greaterThanOrEqual(target, lower),
      lessThanOrEqual(target, upper)
    );
  }

  public <@NonNull Right, @NonNull Left extends Comparable<Right>>
  @NonNull Expression<@NonNull Boolean> greaterThan (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.GREATER_THAN);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <@NonNull Right, @NonNull Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean>
  greaterThanOrEqual (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.GREATER_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <@NonNull Right, @NonNull Left extends Comparable<Right>>
  @NonNull Expression<@NonNull Boolean> lessThan (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.LESS_THAN);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <@NonNull Right, @NonNull Left extends Comparable<Right>>
  @NonNull Expression<@NonNull Boolean> lessThanOrEqual (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.LESS_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> equal (
    @NonNull final Expression<Left> left,
    @NonNull final Expression<Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.EQUAL);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return nonNullConstant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return nonNullConstant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return nonNullConstant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return nonNullConstant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return nonNullConstant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return nonNullConstant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Primitives.BOOLEAN);
  }
}
