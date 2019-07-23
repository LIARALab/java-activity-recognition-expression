package org.liara.data.type.common;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

import java.nio.ByteBuffer;

public class LongDataType implements DataType<@NonNull Long>, ComparableDataType
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
  ) { return Long.compare(leftBuffer.getLong(leftOffset), rightBuffer.getLong(rightOffset)); }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Long> getGeneric () {
    return Generics.LONG;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes () {
    return Long.BYTES;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Mutable<@NonNull Long> output
  ) { output.setValue(buffer.getLong(offset)); }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Long value
  ) { buffer.putLong(offset, value); }
}
