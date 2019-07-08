package org.liara.expression;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.MinLen;
import org.liara.data.type.Type;
import org.liara.expression.operation.CommonOperator;
import org.liara.expression.operation.StaticOperationBuilder;
import org.liara.expression.function.StaticFunctionExpressionBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
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

  public <T> @NonNull Expression<T> identity (@NonNull final Expression<T> value) {
    return value;
  }

  public <T> @NonNull Expression<T> placeholder (@NonNull final Type<T> type) {
    return new Placeholder<>(type);
  }

  public @NonNull Expression<@Nullable Byte> constant (@Nullable final Byte value) {
    return new Constant<>(Type.nullableByte(), value);
  }

  public @NonNull Expression<@Nullable Short> constant (@Nullable final Short value) {
    return new Constant<>(Type.nullableShort(), value);
  }

  public @NonNull Expression<@Nullable Integer> constant (@Nullable final Integer value) {
    return new Constant<>(Type.nullableInteger(), value);
  }

  public @NonNull Expression<@Nullable Long> constant (@Nullable final Long value) {
    return new Constant<>(Type.nullableLong(), value);
  }

  public @NonNull Expression<@Nullable Float> constant (@Nullable final Float value) {
    return new Constant<>(Type.nullableFloat(), value);
  }

  public @NonNull Expression<@Nullable Double> constant (@Nullable final Double value) {
    return new Constant<>(Type.nullableDouble(), value);
  }

  public @NonNull Expression<@Nullable Character> constant (@Nullable final Character value) {
    return new Constant<>(Type.nullableCharacter(), value);
  }

  public @NonNull Expression<@Nullable Boolean> constant (@Nullable final Boolean value) {
    return new Constant<>(Type.nullableBoolean(), value);
  }

  public @NonNull Expression<@NonNull Byte> constant (final byte value) {
    return new Constant<>(Type.nonNullByte(), value);
  }

  public @NonNull Expression<@NonNull Short> constant (final short value) {
    return new Constant<>(Type.nonNullShort(), value);
  }

  public @NonNull Expression<@NonNull Integer> constant (final int value) {
    return new Constant<>(Type.nonNullInteger(), value);
  }

  public @NonNull Expression<@NonNull Long> constant (final long value) {
    return new Constant<>(Type.nonNullLong(), value);
  }

  public @NonNull Expression<@NonNull Float> constant (final float value) {
    return new Constant<>(Type.nonNullFloat(), value);
  }

  public @NonNull Expression<@NonNull Double> constant (final double value) {
    return new Constant<>(Type.nonNullDouble(), value);
  }

  public @NonNull Expression<@NonNull Character> constant (final char value) {
    return new Constant<>(Type.nonNullCharacter(), value);
  }

  public @NonNull Expression<@NonNull Boolean> constant (final boolean value) {
    return new Constant<>(Type.nonNullBoolean(), value);
  }

  /*
  public @NonNull Expression<@Nullable String> constant (@Nullable final String value) {
    return new Constant<>(value);
  }

  public @NonNull Expression<@Nullable ZonedDateTime> constant (@Nullable final ZonedDateTime
  value) {
    return new Constant<>(value);
  }*/

  public <T extends Number> @NonNull Expression<T> add (
    @NonNull final Expression<T> leftOperand,
    @NonNull final Expression<T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.ADDITION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> add (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.ADDITION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> subtract (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.SUBTRACTION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> subtract (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.SUBTRACTION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> multiply (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.MULTIPLICATION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> multiply (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.MULTIPLICATION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> divide (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.DIVISION);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> divide (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.DIVISION);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseOr (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_OR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseOr (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_OR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseAnd (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_AND);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseAnd (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_AND);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseXor (
    @NonNull final Expression<@NonNull T> leftOperand,
    @NonNull final Expression<@NonNull T> rightOperand
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(leftOperand);
    _staticOperationBuilder.addOperand(rightOperand);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_XOR);

    return _staticOperationBuilder.build(leftOperand.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseXor (
    @NonNull @MinLen(1) final List<@NonNull Expression<@NonNull T>> operands
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(operands);
    _staticOperationBuilder.setOperator(CommonOperator.BITWISE_XOR);

    return _staticOperationBuilder.build(operands.get(0).getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> minus (
    @NonNull final Expression<@NonNull T> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(value);
    _staticOperationBuilder.setOperator(CommonOperator.MINUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> plus (
    @NonNull final Expression<@NonNull T> value
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(value);
    _staticOperationBuilder.setOperator(CommonOperator.PLUS);

    return _staticOperationBuilder.build(value.getResultType());
  }

  public <T extends Number> @NonNull Expression<@NonNull T> bitwiseNot (
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

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public <Compared, Target extends Comparable<Compared>> @NonNull Expression<@NonNull Boolean> between (
    @NonNull final Expression<@NonNull Target> target,
    @NonNull final Expression<@NonNull Compared> lower,
    @NonNull final Expression<@NonNull Compared> upper
  ) {
    return and(
      greaterThanOrEqual(target, lower),
      lessThanOrEqual(target, upper)
    );
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> greaterThan (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.GREATER_THAN);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> greaterThanOrEqual (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.GREATER_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> lessThan (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.LESS_THAN);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> lessThanOrEqual (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.LESS_THAN_OR_EQUAL);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public <Right, Left extends Comparable<Right>> @NonNull Expression<@NonNull Boolean> equal (
    @NonNull final Expression<@NonNull Left> left,
    @NonNull final Expression<@NonNull Right> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.EQUAL);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return constant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> and (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return constant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.AND);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return constant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> or (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return constant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.OR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final Expression<@NonNull Boolean> left,
    @NonNull final Expression<@NonNull Boolean> right
  ) {
    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperand(left);
    _staticOperationBuilder.addOperand(right);
    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final Expression<@NonNull Boolean>... predicates
  ) {
    if (predicates.length <= 0) return constant(true);

    _staticOperationBuilder.clear();
    _staticOperationBuilder.addOperands(Arrays.asList(predicates));
    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }

  public @NonNull Expression<@NonNull Boolean> xor (
    @NonNull final List<@NonNull Expression<@NonNull Boolean>> predicates
  ) {
    if (predicates.isEmpty()) return constant(true);

    _staticOperationBuilder.clear();

    for (@NonNegative int index = 0, length = predicates.size(); index < length; ++index) {
      _staticOperationBuilder.addOperand(predicates.get(index));
    }

    _staticOperationBuilder.setOperator(CommonOperator.XOR);

    return _staticOperationBuilder.build(Type.nonNullBoolean());
  }
}
