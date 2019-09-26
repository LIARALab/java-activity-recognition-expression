package org.liara.support.view.primitive;

import java.nio.IntBuffer;
import java.util.function.Supplier;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public interface PrimitiveView<T> extends View<T> {
  static @NonNull PrimitiveView<@NonNull Byte> readonly (final byte[] array) {
    return new ByteArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Short> readonly (final short[] array) {
    return new ShortArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Integer> readonly (final int[] array) {
    return new IntegerArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Long> readonly (final long[] array) {
    return new LongArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Float> readonly (final float[] array) {
    return new FloatArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Double> readonly (final double[] array) {
    return new DoubleArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Character> readonly (final char[] array) {
    return new CharArrayView(array);
  }

  static @NonNull PrimitiveView<@NonNull Integer> readonly (
      @NonNull final IntBuffer buffer,
      @NonNull final Supplier<@NonNegative Integer> size
  ) {
    return new IntegerBufferView(buffer, size);
  }
}
