package org.liara.support;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.generic.Generic;

public class Box<T> implements Mutable<T>
{
  @NonNull
  private final Generic<T> _generic;

  private T _value;

  public Box (@NonNull final Generic<T> generic, final T value) {
    _generic = generic;
    _value = value;
  }

  @Override
  public T getValue () {
    return _value;
  }

  @Override
  public void setValue (final T value) {
    _value = value;
  }

  public @NonNull Generic<T> getGeneric () {
    return _generic;
  }
}
