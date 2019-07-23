package org.liara.data.type;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.ByteBuffer;

public interface ComparableDataType
{
  /**
   * Compare two values from two buffers.
   *
   * @param leftBuffer Buffer from which extracting the left operand of the comparison.
   * @param leftOffset Offset in bytes to apply to the left buffer.
   * @param rightBuffer Buffer from which extracting the right operand of the comparison.
   * @param rightOffset Offset in bytes to apply to the right buffer.
   *
   * @return a positive integer if the left operand is greater than the right one, a negative
   * integer if the left operand is lower than the right one, 0 if the left operand is equal to
   * the right one.
   */
  int compare (
    @NonNull final ByteBuffer leftBuffer, @NonNegative final int leftOffset,
    @NonNull final ByteBuffer rightBuffer, @NonNegative final int rightOffset
  );
}
