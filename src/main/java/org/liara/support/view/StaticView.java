package org.liara.support.view;

import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StaticView<T> implements View<T> {

  @NonNull
  private final View<T> _wrapped;

  public StaticView(@NonNull final View<T> view) {
    if (view instanceof StaticView) {
      _wrapped = ((StaticView<T>) view)._wrapped;
    } else {
      _wrapped = view;
    }
  }

  public StaticView(@NonNull final StaticView<T> toCopy) {
    _wrapped = toCopy._wrapped;
  }

  @Override
  public @NonNegative int getSize() {
    return _wrapped.getSize();
  }

  @Override
  public @Nullable T get(
      @NonNegative @LessThan("getBytes()") final int index
  )
      throws IndexOutOfBoundsException {
    return _wrapped.get(index);
  }

  @Override
  public @NonNull Class<T> getValueClass() {
    return _wrapped.getValueClass();
  }
}
