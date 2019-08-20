package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

import java.util.Arrays;
import java.util.Collection;

/**
 * An expression that is an operator applied to a list of operands.
 */
public interface Operation extends Expression
{
  /**
   * @return The operator that describe this operation.
   */
  @NonNull Operator getOperator();

  /**
   * Return the nth operand of this operation.
   *
   * @param index Index of the operand to get from left (0) to right (size - 1)
   *
   * @return The requested operand if exists.
   *
   * @throws IndexOutOfBoundsException When the given index is out of range.
   */
  default @NonNull Expression getOperand (@NonNegative final int index) {
    return getChildren().get(index);
  }

  /**
   * Interface of operations that can be rewrite.
   */
  interface Rewritable extends Operation, Expression.Rewritable {
    /**
     * @see Expression.Rewritable#rewrite(int, Expression)
     */
    @Override
    @NonNull Operation rewrite (
      @NonNegative final int index,
      @NonNull final Expression expression
    );

    /**
     * @see Expression.Rewritable#rewrite(Expression[])
     */
    @Override
    @NonNull Operation rewrite (
      @NonNull final Expression[] expressions
    );

    /**
     * Create a new operation like this one by replacing one of its operand.
     *
     * @param index Index of the operand to replace.
     * @param operand The new operand to set.
     *
     * @return A new operation like this one with one of its operand replaced by the given one.
     */
    default @NonNull Operation setOperand (
      @NonNegative final int index,
      @NonNull final Expression operand
    ) {
      return rewrite(index, operand);
    }

    /**
     * Create a new operation like this one by replacing all of its operand.
     *
     * @param operands The new operands of this operation.
     *
     * @return A new operation like this one with all of its operand replaced by the given ones.
     */
    default @NonNull Operation setOperands (@NonNull final Expression[] operands) {
      return rewrite(operands);
    }

    /**
     * Create a new operation like this one by replacing all of its operand.
     *
     * @param operands The new operands of this operation.
     *
     * @return A new operation like this one with all of its operand replaced by the given ones.
     */
    default @NonNull Operation setOperands (
      @NonNull final Collection<@NonNull Expression> operands
    ) {
      return setOperands(operands.toArray(new Expression[0]));
    }
  }
}
