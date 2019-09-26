package org.liara.expression;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

/**
 * An identity expression.
 */
public final class Identity<Result> implements Expression<Result> {
  @NonNull
  private final Expression<Result> _child;

  @NonNull
  private final View<@NonNull ? extends Expression<?>> _children;

  /**
   * Create a new identity expression of another one.
   *
   * @param expression An expression to wrap into an identity one.
   */
  public Identity (@NonNull final Expression<Result> expression) {
    _child = expression;
    _children = View.readonly(new Expression<?>[] { expression });
  }

  /**
   * Create a copy of an existing identity expression.
   *
   * @param toCopy An identity expression instance to copy.
   */
  public Identity (@NonNull final Identity<Result> toCopy) {
    _child = toCopy._child;
    _children = View.readonly(new Expression<?>[]{_child});
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ? extends Expression<?>> getChildren() {
    return _children;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _child.getResultType();
  }

  /**
   * @return The only child of this identity expression.
   */
  public @NonNull Expression<Result> getChild() {
    return _child;
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof Identity) {
      @NonNull final Identity otherIdentity = (Identity) other;

      return Objects.equals(_child, otherIdentity.getChild());
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(_child);
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _child.toString() + " }";
  }
}
