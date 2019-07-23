package org.liara.data.type.common;

import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;

import java.nio.ByteBuffer;

public class NullableDataType<T> implements DataType<@Nullable T>, ComparableDataType
{
  @NonNull
  private final DataType<T> _wrapped;

  @NonNull
  private final Generic<@Nullable T> _generic;

  public NullableDataType (
    @NonNull final Generic<@Nullable T> generic,
    @NonNull final DataType<T> wrapped
  ) {
    _generic = generic;
    _wrapped = wrapped;
  }

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
    final int comparison = Boolean.compare(
      isDefined(leftBuffer, leftOffset),
      isDefined(rightBuffer, rightOffset)
    );

    if (comparison == 0 && _wrapped instanceof ComparableDataType) {
      return ((ComparableDataType) _wrapped).compare(
        leftBuffer, leftOffset,
        rightBuffer, rightOffset
      );
    } else {
      return comparison;
    }
  }

  /**
   * Return true if the value stored at the given location is not null.
   *
   * @param buffer A buffer to read.
   * @param offset A number of bytes to skip.
   *
   * @return True if the value stored at the given location is not null.
   */
  public boolean isDefined (@NonNull final ByteBuffer buffer, @NonNegative final int offset) {
    return buffer.get(offset) > 0;
  }

  /**
   * Return true if the value stored at the given location is null.
   *
   * @param buffer A buffer to read.
   * @param offset A number of bytes to skip.
   *
   * @return True if the value stored at the given location is null.
   */
  public boolean isNull (@NonNull final ByteBuffer buffer, @NonNegative final int offset) {
    return buffer.get(offset) <= 0;
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@Nullable T> getGeneric () {
    return _generic;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes () {
    return _wrapped.getBytes() + 1;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @NonNull final Mutable<@Nullable T> output
  ) {
    if (isNull(buffer, offset)) {
      output.setValue(null);
    } else {
      _wrapped.read(buffer, offset + 1, output);
    }
  }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write (
    @NonNull final ByteBuffer buffer,
    @NonNegative final int offset,
    @Nullable final T value
  ) {
    buffer.put(offset, (byte) (value == null ? 0 : 1));

    if (value != null) {
      _wrapped.write(buffer, offset + 1, value);
    }
  }
}
