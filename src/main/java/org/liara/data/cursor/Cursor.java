package org.liara.data.cursor;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.Data;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;

public interface Cursor extends BinaryInputStream, BinaryOutputStream
{
  /**
   * @return The location of this cursor in bytes from the moveToStart of its related buffer.
   */
  @NonNegative int getOffset();

  /**
   * Update the location of this cursor.
   *
   * @param offset New number of bytes from the moveToStart of the buffer to the location of this cursor.
   */
  void setOffset (@NonNegative final int offset);

  /**
   * @return The targeted data object.
   */
  @NonNull Data getTarget ();

  /**
   * Update the targeted data object.
   *
   * @param data The new underlying buffer.
   */
  void setTarget (@NonNull final Data data);
}
