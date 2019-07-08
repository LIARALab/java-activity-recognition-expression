package org.liara.support.view;

import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ArrayView<T> implements View<T>
{
  @NonNull
  private final Class<T> _valueClass;

  @NonNull
  private final T[] _wrapped;

  public ArrayView (@NonNull final Class<T> valueClass, @NonNull final T[] wrapped) {
    _wrapped = wrapped;
    _valueClass = valueClass;
  }

  public ArrayView (@NonNull final ArrayView<T> toCopy) {
    _wrapped = toCopy._wrapped;
    _valueClass = toCopy.getValueClass();
  }

  @Override
  public @NonNegative int getSize () {
    return _wrapped.length;
  }

  @Override
  public @Nullable T get (
    @NonNegative @LessThan("getSize()") final int index
  )
  throws IndexOutOfBoundsException {
    return _wrapped[index];
  }

  @Override
  public @NonNull Class<T> getValueClass () {
    return _valueClass;
  }
}
