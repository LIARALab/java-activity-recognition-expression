package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

/**
 * An identity expression.
 */
public final class Identity implements Expression {

  @NonNull
  private final Expression _child;

  @NonNull
  private final View<@NonNull Expression> _children;

  /**
   * Create a new identity expression of another one.
   *
   * @param expression An expression to wrap into an identity one.
   */
  public Identity(@NonNull final Expression expression) {
    _child = expression;
    _children = View.readonly(Expression.class, new Expression[]{expression});
  }

  /**
   * Create a copy of an existing identity expression.
   *
   * @param toCopy An identity expression instance to copy.
   */
  public Identity(@NonNull final Identity toCopy) {
    _child = toCopy._child;
    _children = View.readonly(Expression.class, new Expression[]{_child});
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren() {
    return _children;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<?> getResultType() {
    return _child.getResultType();
  }
}
