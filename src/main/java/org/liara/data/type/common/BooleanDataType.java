package org.liara.data.type.common;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

import java.nio.ByteBuffer;

public class BooleanDataType implements DataType<@NonNull Boolean>, ComparableDataType
{
  /**
   * @see ComparableDataType#compare(ByteBuffer, int, ByteBuffer, int)
   */
  @Override
  public int compare (
    @NonNull final ByteBuffer leftBuffer,
    @NonNegative final int leftOffset,
    @NonNull final ByteBuffer rightBuffer,
    @NonNegative final int rightOffset
  ) {
    return Boolean.compare(leftBuffer.get(leftOffset) > 0,  rightBuffer.get(rightOffset) > 0);
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Boolean> getGeneric () {
    return Generics.BOOLEAN;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes () {
    return 1;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Mutable<@NonNull Boolean> output
  ) {  output.setValue(buffer.get(offset) > 0); }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write (
    final @NonNull ByteBuffer buffer,
    @NonNegative final int offset,
    final @NonNull Boolean value
  ) { buffer.put(offset, (byte) (value ? 1 : 0)); }
}
