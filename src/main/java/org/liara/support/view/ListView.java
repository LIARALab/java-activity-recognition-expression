package org.liara.support.view;

import java.util.List;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ListView<T> implements View<T> {
  @NonNull
  private final List<? extends T> _list;

  public ListView(@NonNull final List<? extends T> list) {
    _list = list;
  }

  public ListView(@NonNull final ListView<T> toCopy) {
    _list = toCopy._list;
  }

  @Override
  public @NonNegative int getSize() {
    return _list.size();
  }

  @Override
  public @Nullable T get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _list.get(index);
  }

  @Override
  public @NonNull Stream<T> stream() {
    return _list.stream().map(x -> (T) x);
  }

  @Override
  public Object[] toArray() {
    return _list.toArray();
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) {
      return false;
    }

    if (other == this) {
      return true;
    }

    if (other instanceof View<?>) {
      return View.equals(this, (View<?>) other);
    }

    return false;
  }
}
