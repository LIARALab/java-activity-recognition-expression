package org.liara.data.type.common;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

import java.nio.ByteBuffer;

public class CharacterDataType implements DataType<@NonNull Character>, ComparableDataType
{
  /**
   * @see ComparableDataType#compare(ByteBuffer, int, ByteBuffer, int)
   */
  @Override
  public int compare (
    final @NonNull ByteBuffer leftBuffer,
    @NonNegative final int leftOffset,
    final @NonNull ByteBuffer rightBuffer,
    @NonNegative final int rightOffset
  ) {
    return Character.compare(leftBuffer.getChar(leftOffset), rightBuffer.getChar(rightOffset));
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull Character> getGeneric () {
    return Generics.CHARACTER;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes () {
    return Character.BYTES;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Mutable<@NonNull Character> output
  ) { output.setValue(buffer.getChar(offset)); }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Character value
  ) {
    buffer.putChar(offset, value);
  }
}
