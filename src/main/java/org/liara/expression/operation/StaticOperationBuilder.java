package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.Type;
import org.liara.expression.Expression;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StaticOperationBuilder
{
  @NonNull
  private final LinkedList<@NonNull Expression> _operands;

  @Nullable
  private Operator _operator;

  public StaticOperationBuilder () {
    _operands = new LinkedList<>();
    _operator = null;
  }

  public StaticOperationBuilder (@NonNull final StaticOperationBuilder toCopy) {
    _operands = new LinkedList<>(toCopy.getOperands());
    _operator = toCopy.getOperator();
  }

  public <Result> @NonNull StaticOperation<Result> build (@NonNull final Type<Result> resultType) {
    return new StaticOperation<>(resultType, this);
  }

  public @NonNull List<@NonNull Expression> getOperands () {
    return _operands;
  }

  public void setOperands (@Nullable final Collection<@NonNull Expression> operands) {
    _operands.clear();
    if (operands != null) _operands.addAll(operands);
  }

  public <T> void addOperand (@NonNull final Expression<T> operand) {
    _operands.add(operand);
  }

  public <T> void addOperands (@NonNull final Collection<@NonNull Expression<T>> operands) {
    _operands.addAll(operands);
  }

  public void removeOperand (@NonNegative final int index) {
    _operands.remove(index);
  }

  public @Nullable Operator getOperator () {
    return _operator;
  }

  public void setOperator (@Nullable final Operator operator) {
    _operator = operator;
  }

  public void clear () {
    _operands.clear();
    _operator = null;
  }
}
