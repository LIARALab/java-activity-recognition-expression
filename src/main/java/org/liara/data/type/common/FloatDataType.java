package org.liara.data.type.common;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

import java.nio.ByteBuffer;

public class FloatDataType implements DataType<@NonNull Float>, ComparableDataType
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
  ) { return Float.compare(leftBuffer.getFloat(leftOffset), rightBuffer.getFloat(rightOffset)); }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Float> getGeneric () {
    return Generics.FLOAT;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes () {
    return Float.BYTES;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Mutable<@NonNull Float> output
  ) { output.setValue(buffer.getFloat(offset)); }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Float value
  ) { buffer.putFloat(offset, value); }
}