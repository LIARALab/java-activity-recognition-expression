package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

/**
 * A value that never mutate.
 *
 * @param <Result> Primitive to expect from an evaluation of this expression.
 */
public final class Constant<Result> implements Expression<Result>
{
  @NonNull
  private final static View<@NonNull Expression> CHILDREN = View.readonly(Expression.class);

  private final Result _value;

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Create a new constant with a given value.
   *
   * @param type DataType of this constant.
   * @param value Value of this constant.
   */
  public Constant (@NonNull final Primitive<Result> type, final Result value) {
    _value = value;
    _type = type;
  }

  /**
   * Copy an existing constant.
   *
   * @param toCopy An existing constant to copy.
   */
  public Constant (@NonNull final Constant<Result> toCopy) {
    _value = toCopy.getValue();
    _type = toCopy.getResultType();
  }

  /**
   * @return The value of this constant.
   */
  public Result getValue () {
    return _value;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType () {
    return _type;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren () {
    return CHILDREN;
  }
}
