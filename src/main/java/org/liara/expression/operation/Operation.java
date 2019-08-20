package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

/**
 * An expression that is an operator applied to a list of operands.
 */
public interface Operation<Result> extends Expression<Result> {

  /**
   * @return The operator that describe this operation.
   */
  @NonNull Operator getOperator();

  /**
   * Return the nth operand of this operation.
   *
   * @param index Index of the operand to get from left (0) to right (size - 1)
   * @return The requested operand if exists.
   * @throws IndexOutOfBoundsException When the given index is out of range.
   */
  default @NonNull Expression getOperand(@NonNegative final int index) {
    return getChildren().get(index);
  }
}
