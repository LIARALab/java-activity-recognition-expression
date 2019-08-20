package org.liara.expression.operation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;

/**
 * A class that allows to build static operation objects.
 */
public class StaticOperationBuilder {
  @NonNull
  private final LinkedList<@NonNull Expression> _operands;

  @Nullable
  private Operator _operator;

  /**
   * Create a new empty operation builder.
   */
  public StaticOperationBuilder() {
    _operands = new LinkedList<>();
    _operator = null;
  }

  /**
   * Create a copy of an existing operation builder.
   *
   * @param toCopy An operation builder instance to copy.
   */
  public StaticOperationBuilder(@NonNull final StaticOperationBuilder toCopy) {
    _operands = new LinkedList<>(toCopy.getOperands());
    _operator = toCopy.getOperator();
  }

  /**
   * Build a static operation from this builder and return it.
   *
   * @return A new static operation instance in accordance with this builder state.
   */
  public <Result> @NonNull Operation<Result> build (@NonNull final Primitive<Result> resultType) {
    return new StaticOperation<>(resultType, this);
  }

  /**
   * @return A mutable list of operands.
   */
  public @NonNull List<@NonNull Expression> getOperands() {
    return _operands;
  }

  /**
   * Update all operands of the operation to create.
   *
   * @param operands New operands of the operation to create.
   */
  public void setOperands(@Nullable final Collection<? extends @NonNull Expression<?>> operands) {
    _operands.clear();

    if (operands != null) {
      _operands.addAll(operands);
    }
  }

  /**
   * @return The operator that describe the operation to create.
   */
  public @Nullable Operator getOperator() {
    return _operator;
  }

  /**
   * Update the operator of the operation to create.
   *
   * @param operator The new operator of the operation to create.
   */
  public void setOperator(@Nullable final Operator operator) {
    _operator = operator;
  }

  /**
   * Clear this builder state.
   */
  public void clear() {
    _operands.clear();
    _operator = null;
  }
}
