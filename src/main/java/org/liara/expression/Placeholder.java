package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.Type;

/**
 * A value to define at the evaluation of the given expression.
 *
 * @param <T> Type of this placeholder.
 */
public class Placeholder<T> implements Expression<T>
{
  @NonNull
  private final Type<T> _type;

  /**
   * Instantiate a new placeholder of a given type.
   *
   * @param type Type of the placeholder to instantiate.
   */
  public Placeholder (@NonNull final Type<T> type) {
    _type = type;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Type<T> getResultType () {
    return _type;
  }
}
