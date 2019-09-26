package org.liara.support.view.primitive;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public final class ShortArrayView implements PrimitiveView<@NonNull Short> {
  private final short[] _array;

  public ShortArrayView(final short[] array) {
    _array = array;
  }

  public ShortArrayView(final ShortArrayView toCopy) {
    _array = toCopy._array;
  }

  @Override
  public @NonNegative int getSize() {
    return _array.length;
  }

  @Override
  public @NonNull Short get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _array[index];
  }

  @Override
  public @NonNull Stream<@NonNull Short> stream() {
    return IntStream.range(0, _array.length).mapToObj(this::get);
  }

  @Override
  public @NonNull Short[] toArray() {
    return (Short[]) stream().toArray();
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }
}
