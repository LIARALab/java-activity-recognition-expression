package org.liara.support.view.primitive;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

public final class CharArrayView implements PrimitiveView<@NonNull Character> {
  private final char[] _array;

  public CharArrayView(final char[] array) {
    _array = array;
  }

  public CharArrayView(final CharArrayView toCopy) {
    _array = toCopy._array;
  }

  @Override
  public @NonNegative int getSize() {
    return _array.length;
  }

  @Override
  public @NonNull Character get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _array[index];
  }

  @Override
  public @NonNull Stream<@NonNull Character> stream() {
    return IntStream.range(0, _array.length).mapToObj(this::get);
  }

  @Override
  public @NonNull Character[] toArray() {
    return (Character[]) stream().toArray();
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
