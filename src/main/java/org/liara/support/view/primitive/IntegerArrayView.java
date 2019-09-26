package org.liara.support.view.primitive;

import java.util.Arrays;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

public final class IntegerArrayView implements PrimitiveView<@NonNull Integer> {
  private final int[] _array;

  public IntegerArrayView(final int[] array) {
    _array = array;
  }

  public IntegerArrayView(final IntegerArrayView toCopy) {
    _array = toCopy._array;
  }

  @Override
  public @NonNegative int getSize() {
    return _array.length;
  }

  @Override
  public @NonNull Integer get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _array[index];
  }

  @Override
  public @NonNull Stream<@NonNull Integer> stream() {
    return Arrays.stream(_array).boxed();
  }

  @Override
  public @NonNull Integer[] toArray() {
    return (Integer[]) stream().toArray();
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
