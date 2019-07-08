package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

/**
 * An expression that is an operator applied to a list of operands.
 *
 * @param <Result> Type of value to expect from an evaluation of this expression.
 */
public interface Operation<Result>
  extends Expression<Result>
{
  /**
   * @return The operator used in this expression.
   */
  @NonNull Operator getOperator();
}
