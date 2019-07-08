package org.liara.data;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class Data
{
  /**
   * Return true if both data objects have same content.
   *
   * @param first
   * @param second
   * @return
   */
  static boolean equals (@Nullable final Data first, @Nullable final Data second) {
    if (Objects.isNull(first) != Objects.isNull(second)) return false;
    if (Objects.isNull(first)) return true;
    if (first == second) return true;

    return (
      Objects.equals(first.getSize(), second.getSize()) && Arrays.equals(
        first._memory, 0, first.getSize(),
        second._memory, 0, first.getSize()
      )
    );
  }

  private byte[] _memory;

  private int _size;

  /**
   * Create a new empty data object with a given capacity.
   *
   * @param capacity Capacity to allocate in bytes.
   */
  public Data (@NonNegative final int capacity) {
    _memory = new byte[capacity];
    _size = 0;
  }

  /**
   * Create a copy of an existing data object.
   *
   * @param toCopy An existing data object to copy.
   */
  public Data (@NonNull final Data toCopy) {
    _memory = new byte[toCopy.getCapacity()];
    _size = toCopy.getSize();

    toCopy.read(0, _memory);
  }

  /**
   * Read some content of this data object.
   *
   * @param buffer A buffer to fill.
   *
   * @return The number of bytes that was read.
   */
  public @NonNegative int read (final byte[] buffer) {
    return read(0, buffer);
  }

  /**
   * Read some content of this data object.
   *
   * @param offset Offset to apply in bytes.
   * @param buffer A buffer to fill.
   *
   * @return The number of bytes that was read.
   */
  public @NonNegative int read (@NonNegative final int offset, final byte[] buffer) {
    if (offset >= _size) throw new IllegalArgumentException(
      "Unable to read the requested content from this data object : the given offset (" + offset +
      ") is greater than or equal to the current size (" + _size + ") of this data object."
    );

    @NonNegative final int readable = _size - offset;
    @NonNegative final int read = readable > buffer.length ? buffer.length : readable;

    System.arraycopy(_memory, offset, buffer, 0, read);

    return read;
  }

  /**
   * Write some content into this data object.
   *
   * @param offset Number of bytes to skip from the moveToStart of this data object before starting to
   *               write the given content.
   * @param buffer The bytes to write into this data object.
   *
   * @return The number of bytes effectively wrote into this data object.
   */
  public @NonNegative int write (@NonNegative final int offset, final byte[] buffer) {
     if (offset >= _memory.length) throw new IllegalArgumentException(
       "Unable to write the given content into this data object : the given offset (" + offset +
       ") is greater than or equal to the current capacity (" + _memory.length + ") of this data " +
       "object."
     );

     @NonNegative final int writable = _memory.length - offset;
     @NonNegative final int wrote = writable > buffer.length ? buffer.length : writable;
     @NonNegative final int end = offset + wrote;

     if (offset > _size) Arrays.fill(_memory, _size, offset, (byte) 0);
     System.arraycopy(buffer, 0, _memory, offset, wrote);

     _size = end > _size ? end : _size;

     return wrote;
  }

  /**
   * Write some content into this data object.
   *
   * @param buffer The bytes to write into this data object.
   *
   * @return The number of bytes effectively wrote into this data object.
   */
  public @NonNegative int write (final byte[] buffer) {
    return write(0, buffer);
  }

  /**
   * Set a byte of this data object.
   *
   * @param offset Number of bytes to skip from the moveToStart of this data object before starting to
   *               write the given content.
   * @param value  The byte to write into this data object.
   *
   * @return The number of bytes effectively wrote into this data object.
   */
  public @NonNegative int write (@NonNegative final int offset, final byte value) {
    if (offset > _memory.length) throw new IllegalArgumentException(
      "Unable to write the given content into this data object : the given offset (" + offset +
      ") is greater than or equal to the current capacity (" + _memory.length + ") of this data " +
      "object."
    );

    if (offset > _size) Arrays.fill(_memory, _size, offset, (byte) 0);

    _memory[offset] = value;
    _size = offset < _size ? _size : offset + 1;
    return 1;
  }

  /**
   * Reallocate this data object.
   *
   * This operation may truncate existing bytes if necessary.
   *
   * @param capacity The new capacity to allocate for this data object.
   */
  public void reallocate (@NonNegative final int capacity) {
    final byte[] newMemory = new byte[capacity];

    @NonNegative final int minSize = capacity > _size ? _size : capacity;

    System.arraycopy(_memory, 0, newMemory, 0, minSize);

    _memory = newMemory;
    _size = minSize;
  }

  /**
   * Copy some content of this data into this data object.
   *
   * @param source The index of the byte from which the copy operation read.
   * @param destination The index of the byte from which the copy operation write.
   * @param length The number of bytes to copy.
   *
   * @return The number of bytes effectively copied.
   */
  public @NonNegative int copy (
    @NonNegative final int source,
    @NonNegative final int destination,
    @NonNegative final int length
  ) {
    if (source >= _size) throw new IllegalArgumentException(
      "Unable to write the given content into this data object : the given source index (" +
      source + ") is greater or equal to the current size (" + _size + ") of this data object."
    );

    if (destination >= _memory.length) throw new IllegalArgumentException(
      "Unable to write the given content into this data object : the given destination index (" +
      destination + ") is greater or equal to the current capacity (" + _memory.length +
      ") of this data object."
    );

    @NonNegative final int copiable = Math.min(_size - source, length);
    @NonNegative final int copied = Math.min(copiable, _memory.length - destination);

    if (destination + copied > _size) {
      Arrays.fill(_memory, _size, destination + copied, (byte) 0);
      _size = destination + copied;
    }

    System.arraycopy(_memory, source, _memory, destination, copied);

    return copied;
  }

  /**
   * Fit the capacity of this data object to its inner content.
   */
  public void fit () {
    reallocate(_size);
  }

  /**
   * Clear this data object of its content.
   */
  public void clear () {
    _size = 0;
  }

  /**
   * @return The size of this data in bytes.
   */
  public @NonNegative int getSize() {
    return _size;
  }

  /**
   * Update the size of this data object.
   *
   * @param size The new size of this data object in bytes.
   */
  public void setSize(@NonNegative final int size) {
    if (size > _memory.length) {
      throw new Error(
        "Unable to set the size of this data object to " + size + " because the given size exceed" +
        " the maximum capacity of this data object in bytes that is " + _memory.length + "."
      );
    }

    if (size > _size) Arrays.fill(_memory, _size, size, (byte) 0);

    _size = size;
  }

  /**
   * Return the capacity of this data object.
   *
   * @return The capacity of this data object.
   */
  public @NonNegative int getCapacity () {
    return _memory.length;
  }
}
