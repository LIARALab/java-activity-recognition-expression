package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public final class Identity<Result> implements Expression<Result>
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
}
