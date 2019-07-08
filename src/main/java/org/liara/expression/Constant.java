package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.Type;

import java.util.Objects;

/**
 * A value that never mutate.
 *
 * @param <T> Type of the constant expression.
 */
public class Constant<T> implements Expression<T>
{
  private final T _value;

  @NonNull
  private final Type<T> _type;

  /**
   * Create a new constant with a given value.
   *
   * @param type Type of this constant.
   * @param value Value of this constant.
   */
  public Constant (@NonNull final Type<T> type, final T value) {
    _value = value;
    _type = type;
  }

  /**
   * Copy an existing constant.
   *
   * @param toCopy An existing constant to copy.
   */
  public Constant (@NonNull final Constant<T> toCopy) {
    _value = toCopy.getValue();
    _type = toCopy.getResultType();
  }

  /**
   * @return The value of this constant.
   */
  public T getValue () {
    return _value;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Type<T> getResultType () {
    return _type;
  }
}
