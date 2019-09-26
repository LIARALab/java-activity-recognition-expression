package org.liara.expression.operation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;
import org.liara.support.view.View;

public final class UnaryOperation<Result> implements Operation<Result> {
  @NonNull
  private final View<@NonNull ? extends Expression<?>> _operands;

  @NonNull
  private final Operator _operator;

  @NonNull
  private final Primitive<Result> _type;

  public UnaryOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Expression<?> operand
  ) {
    _type = type;
    _operator = operator;
    _operands = View.readonly(new Expression<?>[] {operand});
  }

  public UnaryOperation(@NonNull final UnaryOperation<Result> toCopy) {
    this(
        toCopy.getResultType(),
        toCopy.getOperator(),
        toCopy.getOperand()
    );
  }

  public @NonNull Expression<?> getOperand () {
    return _operands.get(0);
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

    if (other instanceof UnaryOperation) {
      @NonNull final UnaryOperation otherUnaryOperation = (UnaryOperation) other;

      return Objects.equals(
          _operands,
          otherUnaryOperation.getChildren()
      ) && Objects.equals(
          _operator,
          otherUnaryOperation.getOperator()
      ) && Objects.equals(
          _type,
          otherUnaryOperation.getResultType()
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
