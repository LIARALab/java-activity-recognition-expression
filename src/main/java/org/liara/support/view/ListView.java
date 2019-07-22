package org.liara.support.view;

import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class ListView<T> implements View<T>
{
  @NonNull
  private final Class<T> _valueClass;

  @NonNull
  private final List<T> _wrapped;

  public ListView (@NonNull final Class<T> valueClass, @NonNull final List<T> list) {
    _wrapped = list;
    _valueClass = valueClass;
  }

  public ListView (@NonNull final ListView<T> toCopy) {
    _wrapped = toCopy._wrapped;
    _valueClass = toCopy.getValueClass();
  }

  @Override
  public @NonNegative int getSize () {
    return _wrapped.size();
  }

  @Override
  public @Nullable T get (
    @NonNegative @LessThan("getSize()") final int index
  )
  throws IndexOutOfBoundsException {
    return _wrapped.get(index);
  }

  @Override
  public @NonNull Class<T> getValueClass () {
    return _valueClass;
  }
}
