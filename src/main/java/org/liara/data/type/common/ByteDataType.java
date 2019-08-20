package org.liara.data.type.common;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

public class ByteDataType implements DataType<@NonNull Byte>, ComparableDataType {

  /**
   * @see ComparableDataType#compare(ByteBuffer, int, ByteBuffer, int)
   */
  @Override
  public int compare(
      final @NonNull ByteBuffer leftBuffer,
      @NonNegative final int leftOffset,
      final @NonNull ByteBuffer rightBuffer,
      @NonNegative final int rightOffset
  ) {
    return Byte.compare(leftBuffer.get(leftOffset), rightBuffer.get(rightOffset));
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Byte> getGeneric() {
    return Generics.BYTE;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes() {
    return 1;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final Mutable<@NonNull Byte> output
  ) {
    output.setValue(buffer.get(offset));
  }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write(
      final @NonNull ByteBuffer buffer,
      @NonNegative final int offset,
      final @NonNull Byte value
  ) {
    buffer.put(offset, value);
  }
}
