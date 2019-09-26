package org.liara.support.index;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SparseSet implements Iterable<@NonNegative Integer> {
  @NonNegative
  private int[] _sparse;

  @NonNegative
  private int[] _dense;

  @NonNegative
  private int _size;

  /**
   * Instantiate a new sparse set with the given capacity.
   *
   * @param capacity Capacity of the sparse set to instantiate.
   *
   * @see #getCapacity()
   */
  public SparseSet (@NonNegative final int capacity) {
    _sparse = new int[capacity];
    _dense = new int[capacity];
    _size = 0;
  }

  /**
   * @return The current number of elements into this set.
   */
  public @NonNegative int getSize() {
    return _size;
  }

  /**
   * @return The maximum number of elements that this set can store.
   */
  public @NonNegative int getCapacity () {
    return _dense.length;
  }

  /**
   * @return True if this set is empty.
   */
  public boolean isEmpty() {
    return _size == 0;
  }

  /**
   * Search for a value in this set and return true if this set contains it.
   *
   * @param value A value to search for.
   *
   * @return True if this set contains the given value, false otherwise.
   */
  public boolean contains (@NonNegative final int value) {
    return  value < getCapacity() && _size > 0 && _dense[_sparse[value]] == value;
  }

  /**
   * @return An iterator over each value of this set.
   */
  public @NonNull Iterator<@NonNull Integer> iterator() {
    return IntStream.range(0, _size).map(this::get).iterator();
  }

  /**
   * Return the index of the given value or -1 if the value is not in this set.
   *
   * @param value The value to search.
   *
   * @return The index of the given value if the value is in this set, -1 otherwise.
   */
  public int indexOf(@NonNegative final int value) {
    if (value < getCapacity()) {
      @NonNegative final int index = _sparse[value];

      if (_size > 0 && _dense[index] == value) {
        return index;
      }
    }

    return -1;
  }

  /**
   * Return the value of this set stored at the given index.
   *
   * @param index Index of the value to get.
   *
   * @return The value at the given index.
   */
  public @NonNegative int get (@NonNegative final int index) {
    if (index < _size) {
      return _dense[index];
    } else {
      throw new IndexOutOfBoundsException(
          "Unable to get the element of this set at index #" + index + ", the given is not " +
              "in boundary [0, " + _size + "[."
      );
    }
  }

  /**
   * @return The content of this sparse set as an array.
   */
  public @NonNegative int[] toArray() {
    return Arrays.copyOf(_dense, _size);
  }

  /**
   * Add a value to this sparse set.
   *
   * @param value The value to add.
   */
  public void add (@NonNegative final int value) {
    if (value < getCapacity()) {
      @NonNegative final int index = _sparse[value];

      if (_size == 0 || _dense[index] != value) {
        _sparse[value] = _size;
        _dense[_size] = value;
        _size += 1;
      }
    } else {
      throw new IllegalArgumentException(
          "Unable to add the given value #" + value + " because the given value is greater " +
              "than or equal to this sparse set capacity that is of " + getCapacity() +
              " number(s)."
      );
    }
  }

  /**
   * Remove a value from this sparse set.
   *
   * @param value The value to remove.
   */
  public void remove(@NonNegative final int value) {
    if (value < getCapacity()) {
      @NonNegative final int index = _sparse[value];

      if (_size > 0 && _dense[index] == value) {
        @NonNegative final int last = _dense[_size - 1];

        _dense[index] = last;
        _sparse[last] = index;
        _size -= 1;
      }
    } else {
      throw new IllegalArgumentException(
          "Unable to remove the given value #" + value + " because the given value is greater " +
              "than or equal to this sparse set capacity that is of " + getCapacity() +
              " number(s)."
      );
    }
  }

  /**
   * Reallocate instance's buffers.
   *
   * @param capacity New capacity of the sparse set.
   */
  public void reallocate (@NonNegative final int capacity) {
    @NonNegative final int[] oldDense = _dense;
    @NonNegative final int[] oldSparse = _sparse;
    @NonNegative final int oldSize = _size;

    _dense = new int[capacity];
    _sparse = new int[capacity];
    _size = 0;

    for (@NonNegative int index = 0; index < oldSize; ++index) {
      if (oldDense[index] < capacity) {
        _sparse[oldDense[index]] = _size;
        _dense[_size] = oldDense[index];
        _size += 1;
      }
    }
  }

  /**
   * Clear this set.
   */
  public void clear() {
    _size = 0;
  }
}
