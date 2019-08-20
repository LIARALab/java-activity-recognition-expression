package org.liara.data.type.common;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

public class ShortDataType
    implements DataType<@NonNull Short>, ComparableDataType {

  /**
   * @see ComparableDataType#compare(ByteBuffer, int, ByteBuffer, int)
   */
  @Override
  public int compare(
      @NonNull final ByteBuffer leftBuffer,
      @NonNegative final int leftOffset,
      @NonNull final ByteBuffer rightBuffer,
      @NonNegative final int rightOffset
  ) {
    return Short.compare(leftBuffer.getShort(leftOffset), rightBuffer.getShort(rightOffset));
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Short> getGeneric() {
    return Generics.SHORT;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes() {
    return Short.BYTES;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final Mutable<@NonNull Short> output
  ) {
    output.setValue(buffer.getShort(offset));
  }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final Short value
  ) {
    buffer.putShort(offset, value);
  }
}
