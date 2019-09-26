package org.liara.data.type.common;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.mutable.Mutable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;

public class NullableDataType<T> implements DataType<@Nullable T>, ComparableDataType {

  @NonNull
  private final DataType<T> _wrapped;


  public NullableDataType(@NonNull final DataType<T> wrapped) {
    _wrapped = wrapped;
  }

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
   * @return True if the value stored at the given location is not null.
   */
  public boolean isDefined(@NonNull final ByteBuffer buffer, @NonNegative final int offset) {
    return buffer.get(offset) > 0;
  }

  /**
   * Return true if the value stored at the given location is null.
   *
   * @param buffer A buffer to read.
   * @param offset A number of bytes to skip.
   * @return True if the value stored at the given location is null.
   */
  public boolean isNull(@NonNull final ByteBuffer buffer, @NonNegative final int offset) {
    return buffer.get(offset) <= 0;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes() {
    return _wrapped.getBytes() + 1;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read(
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
  public void write(
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
