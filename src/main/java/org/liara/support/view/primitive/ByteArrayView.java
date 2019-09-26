package org.liara.support.view.primitive;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public final class ByteArrayView implements PrimitiveView<@NonNull Byte> {
  private final byte[] _array;

  public ByteArrayView(final byte[] array) {
    _array = array;
  }

  public ByteArrayView(final ByteArrayView toCopy) {
    _array = toCopy._array;
  }

  @Override
  public @NonNegative int getSize() {
    return _array.length;
  }

  @Override
  public @NonNull Byte get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _array[index];
  }

  @Override
  public @NonNull Stream<@NonNull Byte> stream() {
    return IntStream.range(0, _array.length).mapToObj(this::get);
  }

  @Override
  public @NonNull Byte[] toArray() {
    return (Byte[]) stream().toArray();
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }
}
