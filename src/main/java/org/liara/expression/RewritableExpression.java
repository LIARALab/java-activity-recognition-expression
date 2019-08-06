package org.liara.expression;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An expression that allows to replace one of its children expression.
 *
 * @param <Result> Expected result type from this expression.
 */
public interface RewritableExpression<Result> extends Expression<Result>
{
  /**
   * Return a copy of this expression with one of its children expression replaced by the given one.
   *
   * @param index Index of the children expression to replace.
   * @param expression The expression to set.
   *
   * @return A copy of this expression with one of its children expression replaced by the given
   * one.
   */
  @NonNull Expression<Result> rewrite (
    @NonNegative final int index,
    @NonNull final Expression<?> expression
  );

  /**
   * Return a copy of this expression with all of its children expression replaced by the given
   * ones.
   *
   * @param expressions New children expression of this expression.
   *
   * @return A copy of this expression with all of its children expression replaced by the given
   * ones.
   */
  @NonNull Expression<Result> rewrite (@NonNull final Expression[] expressions);
}
