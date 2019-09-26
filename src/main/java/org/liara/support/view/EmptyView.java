package org.liara.support.view;

import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EmptyView<T> implements View<T> {
  private static final Object[] EMPTY = new Object[0];

  @Override
  public @NonNegative int getSize() {
    return 0;
  }

  @Override
  public @Nullable T get(@NonNegative final int index) throws IndexOutOfBoundsException {
    throw new IndexOutOfBoundsException(
        "The given index " + index + "is invalid because this view is empty."
    );
  }

  @Override
  public @NonNull Stream<T> stream() {
    return Stream.empty();
  }

  @Override
  public Object[] toArray() {
    return EMPTY;
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }
}
