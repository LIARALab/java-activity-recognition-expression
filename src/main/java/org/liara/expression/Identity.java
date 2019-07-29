package org.liara.expression;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public final class Identity<Result> implements RewritableExpression<Result>
{
  @NonNull
  private final Expression<Result> _child;

  @NonNull
  private final View<@NonNull Expression> _children;

  public Identity (@NonNull final Expression<Result> expression) {
    _child = expression;
    _children = View.readonly(Expression.class, new Expression[] {expression});
  }

  public Identity (@NonNull final Identity<Result> toCopy) {
    _child = toCopy._child;
    _children = View.readonly(Expression.class, new Expression[] { _child });
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren () {
    return _children;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType () {
    return _child.getResultType();
  }

  /**
   * @see RewritableExpression#rewrite(int, Expression)
   */
  @Override
  public @NonNull Identity<Result> rewrite (
    @NonNegative final int index,
    @NonNull final Expression<?> expression
  ) {
    if (index == 0) {
      if (expression.getResultType().equals(_child.getResultType())) {
        return new Identity<>(Expression.cast(_child.getResultType(), expression));
      } else {
        throw new IllegalArgumentException(
          "Unable to rewrite this identity expression, the given expression is not an " +
          "expression of " + _child.getResultType() + " result but an expression of " +
          expression.getResultType() + " result."
        );
      }
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite the " + index + "th child of an identity expression because " +
        "the given child index does not exists."
      );
    }
  }
}
