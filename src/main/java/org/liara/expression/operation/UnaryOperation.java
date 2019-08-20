package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

public interface UnaryOperation<Result> extends Operation<Result>
{
  /**
   * Return the operand of this unary operation.
   *
   * @return The operand of this unary operation.
   */
  default @NonNull Expression<?> getOperand () {
    return getChildren().get(0);
  }
}
