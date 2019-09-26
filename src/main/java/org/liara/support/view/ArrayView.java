package org.liara.support.view;

import java.util.Arrays;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A readonly view over an existing array.
 *
 * @param <T> Type of element stored into the view.
 */
public final class ArrayView<T> implements View<T> {
  @NonNull
  private final T[] _array;

  public ArrayView(@NonNull final T[] array) {
    _array = array;
  }

  public ArrayView(@NonNull final ArrayView<T> toCopy) {
    _array = toCopy._array;
  }

  @Override
  public @NonNegative int getSize() {
    return _array.length;
  }

  @Override
  public @Nullable T get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _array[index];
  }

  @Override
  public @NonNull Stream<T> stream() {
    return Arrays.stream(_array);
  }

  @Override
  public T[] toArray() {
    return Arrays.copyOf(_array, _array.length);
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }
}
