package org.liara.data.type;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.generic.Generic;

public interface DataType<T> {

  /**
   * @return The Java type represented by this data type.
   */
  @NonNull Generic<T> getGeneric();

  /**
   * @return The number of bytes required to store this data type.
   */
  @NonNegative int getBytes();

  /**
   * Read an instance of the given type from the given buffer.
   *
   * @param buffer A byte buffer to read.
   * @param offset Number of byte to ignore from the start of the given buffer.
   * @param output Output object.
   */
  void read(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final Mutable<T> output
  );

  /**
   * Write an instance of the given type into the given blob.
   *
   * @param value The value to write.
   * @param buffer A byte buffer to mutate.
   * @param offset Number of byte to ignore from the start of the given buffer.
   */
  void write(@NonNull final ByteBuffer buffer, @NonNegative final int offset, final T value);
}
