package org.liara.expression.operation;

import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;
import org.liara.expression.Identity;
import org.liara.support.view.View;

public final class BinaryOperation<Result> implements Operation<Result> {
  @NonNull
  private final View<@NonNull ? extends Expression<?>> _operands;

  @NonNull
  private final Operator _operator;

  @NonNull
  private final Primitive<Result> _type;

  public BinaryOperation (
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Expression<?> leftOperand,
      @NonNull final Expression<?> rightOperand
  ) {
    _type = type;
    _operator = operator;
    _operands = View.readonly(new Expression<?>[] {leftOperand, rightOperand});
  }

  public BinaryOperation (
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Expression<?>[] operands
  ) {
    this(type, operator, operands[0], operands[1]);
  }

  public BinaryOperation (
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final List<@NonNull ? extends Expression<?>> operands
  ) {
    this(type, operator, operands.get(0), operands.get(1));
  }

  public BinaryOperation (@NonNull final BinaryOperation<Result> toCopy) {
    this(
        toCopy.getResultType(),
        toCopy.getOperator(),
        toCopy.getLeftOperand(),
        toCopy.getRightOperand()
    );
  }

  public @NonNull Expression<?> getLeftOperand () {
    return _operands.get(0);
  }

  public @NonNull Expression<?> getRightOperand () {
    return _operands.get(1);
  }

  public @NonNull Operator getOperator () {
    return _operator;
  }

  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _type;
  }

  @Override
  public @NonNull View<? extends Expression<?>> getChildren() {
    return _operands;
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof BinaryOperation) {
      @NonNull final BinaryOperation otherBinaryOperation = (BinaryOperation) other;

      return Objects.equals(
          _operands,
          otherBinaryOperation.getChildren()
      ) && Objects.equals(
          _operator,
          otherBinaryOperation.getOperator()
      ) && Objects.equals(
          _type,
          otherBinaryOperation.getResultType()
      );
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(_operands, _operator, _type);
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _type.getName() + " " + _operator.toString() + " " +
        _operands.toString() + " }";
  }
}
