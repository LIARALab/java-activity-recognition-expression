package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.data.type.DataType;

/**
 * A value to define at the evaluation of the given expression.
 *
 * @param <Result> Primitive to expect from an evaluation of this expression.
 */
public class Placeholder<Result> implements Expression<Result>
{
  @NonNull
  private final Primitive<Result> _type;

  /**
   * Instantiate a new placeholder of a given type.
   *
   * @param type Primitive to expect from an evaluation of this expression.
   */
  public Placeholder (@NonNull final Primitive<Result> type) {
    _type = type;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType () {
    return _type;
  }
}
