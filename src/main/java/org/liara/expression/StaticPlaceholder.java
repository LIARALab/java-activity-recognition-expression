package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

/**
 * A value to define at the evaluation of the given expression.
 *
 * @param <Result> Primitive to expect from an evaluation of this expression.
 */
public class StaticPlaceholder<Result> implements Placeholder<Result> {

  @NonNull
  private final static View<@NonNull Expression> CHILDREN = View.readonly(Expression.class);

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Instantiate a new placeholder of a given type.
   *
   * @param type Primitive to expect from an evaluation of this expression.
   */
  public StaticPlaceholder(@NonNull final Primitive<Result> type) {
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
  public @NonNull View<@NonNull Expression> getChildren() {
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
