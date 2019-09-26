package org.liara.support.view.primitive;

import java.nio.IntBuffer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public class IntegerBufferView implements PrimitiveView<Integer> {
  @NonNull
  private final IntBuffer _buffer;

  @NonNull
  private final Supplier<@NonNegative Integer> _size;

  public IntegerBufferView (
      @NonNull final IntBuffer buffer,
      @NonNull final Supplier<@NonNegative Integer> size
  ) {
    _buffer = buffer;
    _size = size;
  }

  @Override
  public @NonNegative int getSize() {
    return _size.get();
  }

  @Override
  public Integer get(
      @NonNegative @LessThan("getSize()") final int index
  ) throws IndexOutOfBoundsException {
    if (index < _size.get()) {
      return _buffer.get(index);
    } else {
      throw new IndexOutOfBoundsException(
          "Unable to get the value at the index #" + index + " because the given index is out of " +
              "bounds [0, " + _size.get() + "[."
      );
    }
  }

  @Override
  public @NonNull Stream<Integer> stream() {
    return IntStream.range(0, getSize()).mapToObj(this::get);
  }

  @Override
  public @NonNull Object[] toArray() {
    return stream().toArray();
  }
}
