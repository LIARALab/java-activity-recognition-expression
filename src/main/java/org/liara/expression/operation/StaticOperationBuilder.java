package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that allows to build static operation objects.
 */
public class StaticOperationBuilder
{
  @NonNull
  private final LinkedList<@NonNull Expression> _operands;

  @Nullable
  private Operator _operator;

  /**
   * Create a new empty operation builder.
   */
  public StaticOperationBuilder () {
    _operands = new LinkedList<>();
    _operator = null;
  }

  /**
   * Create a copy of an existing operation builder.
   *
   * @param toCopy An operation builder instance to copy.
   */
  public StaticOperationBuilder (@NonNull final StaticOperationBuilder toCopy) {
    _operands = new LinkedList<>(toCopy.getOperands());
    _operator = toCopy.getOperator();
  }

  /**
   * Assert that an operand of this builder is of the given type.
   *
   * @param index Index of the operand to assert.
   * @param type Expected type of the operand.
   *
   * @param <Type> Java type expected from a resolution of the operand.
   *
   * @return The operand if the assertion succeed.
   *
   * @throws Exception If the assertion fails.
   */
  @SuppressWarnings("unchecked") // Checked by the comparison of the result type instance.
  private <Type> @Nullable Expression<Type> assertThatOperandIsOfType (
    @NonNegative final int index, @NonNull final Primitive<Type> type
  ) throws Exception {
    if (_operands.size() <= index) {
      return null;
    } else if (!_operands.get(index).getResultType().equals(type)) {
      throw new Exception(
        "The " + index + "th operand of this builder, expected to be of type " + type.getName() +
        ", is of type " + _operands.get(index).getResultType().getName()
      );
    } else {
      return (Expression<Type>) _operands.get(index);
    }
  }

  /**
   * Assert that the given operand exists.
   *
   * @param index Index of the operand to assert.
   *
   * @return The operand if the assertion succeed.
   *
   * @throws Exception If the assertion fails.
   */
  private @NonNull Expression<?> assertThatOperandExists (@NonNegative final int index)
  throws Exception {
    if (_operands.size() <= index) {
      throw new Exception("The " + index + "th operand of this builder is not defined.");
    } else {
      return _operands.get(index);
    }
  }

  /**
   * Build a static operation from this builder and return it.
   *
   * @param resultType The resulting type of the operation.
   *
   * @param <Result> The resulting java type to expect from an execution of the operation.
   *
   * @return A new static operation instance in accordance with this builder state.
   */
  public <Result> @NonNull StaticOperation<Result> build (
    @NonNull final Primitive<Result> resultType
  ) { return new StaticOperation<>(resultType, this); }

  /**
   * @return A mutable list of operands.
   */
  public @NonNull List<@NonNull Expression> getOperands () {
    return _operands;
  }

  /**
   * Update all operands of the operation to create.
   *
   * @param operands New operands of the operation to create.
   */
  public void setOperands (@Nullable final Collection<@NonNull Expression> operands) {
    _operands.clear();
    if (operands != null) _operands.addAll(operands);
  }

  /**
   * @return The operator that describe the operation to create.
   */
  public @Nullable Operator getOperator () {
    return _operator;
  }

  /**
   * Update the operator of the operation to create.
   *
   * @param operator The new operator of the operation to create.
   */
  public void setOperator (@Nullable final Operator operator) {
    _operator = operator;
  }

  /**
   * Clear this builder state.
   */
  public void clear () {
    _operands.clear();
    _operator = null;
  }
}
