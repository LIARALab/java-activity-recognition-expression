package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

/**
 * A value to define at the evaluation of the given expression.
 *
 * @param <Result> Primitive to expect from an evaluation of this expression.
 */
public final class Placeholder<Result> implements Expression<Result>{
  @NonNull
  private final static View<@NonNull ? extends Expression<?>> CHILDREN = View.empty();

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Instantiate a new placeholder of a given type.
   *
   * @param type Primitive to expect from an evaluation of this expression.
   */
  public Placeholder(@NonNull final Primitive<Result> type) {
    _type = type;
  }

  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _type;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ? extends Expression<?>> getChildren() {
    return CHILDREN;
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _type.getName() + " }";
  }
}
