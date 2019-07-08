package org.liara.expression.function;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.Type;
import org.liara.expression.Expression;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class StaticFunctionExpressionBuilder
{
  @Nullable
  private FunctionOperator _operator;

  @NonNull
  private final LinkedList<@NonNull Expression<?>> _operands;

  public StaticFunctionExpressionBuilder () {
    _operator = null;
    _operands = new LinkedList<>();
  }

  public StaticFunctionExpressionBuilder (
    @NonNull final StaticFunctionExpressionBuilder toCopy
  ) {
    _operator = toCopy.getOperator();
    _operands = new LinkedList<>(toCopy.getOperands());
  }

  public <Result> @NonNull FunctionExpression<Result> build (
    @NonNull final Type<Result> resultType
  ) {
    return new StaticFunctionExpression<>(resultType, this);
  }

  public @Nullable FunctionOperator getOperator () {
    return _operator;
  }

  public void setOperator (@Nullable final FunctionOperator operator) {
    _operator = operator;
  }

  public @NonNull LinkedList<@NonNull Expression<?>> getOperands () {
    return _operands;
  }

  public void setOperands (@Nullable final Collection<@NonNull Expression<?>> operands) {
    _operands.clear();

    if (operands != null) _operands.addAll(operands);
  }

  public void addOperand (@NonNull final Expression<?> operand) {
    _operands.add(operand);
  }

  public void addOperands (@NonNull final Collection<@NonNull Expression<?>> operands) {
    _operands.addAll(operands);
  }

  public void setOperand (@NonNegative int index, @NonNull final Expression<?> operand) {
    _operands.set(index, operand);
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof StaticFunctionExpressionBuilder) {
      @NonNull
      final StaticFunctionExpressionBuilder otherStaticFunctionExpressionBuilder = (
        (StaticFunctionExpressionBuilder) other
      );

      return (
        Objects.equals(
          _operator,
          otherStaticFunctionExpressionBuilder.getOperator()
        ) && Objects.equals(
          _operands,
          otherStaticFunctionExpressionBuilder.getOperands()
        )
      );
    }

    return false;
  }

  @Override
  public int hashCode () {
    return Objects.hash(_operator, _operands);
  }
}
