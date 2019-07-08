package org.liara.support.view;

import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public interface View<T> extends Iterable<T>
{
  /**
   * Wrap a view into a readonly instance.
   *
   * @param view A view to wrap.
   * @param <T> Type of value stored into the view to wrap.
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly (@NonNull final View<T> view) {
    return view instanceof StaticView ? (StaticView<T>) view : new StaticView<>(view);
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param valueClass Type of value stored into the given array.
   * @param array An array to wrap.
   *
   * @param <T> Type of value stored into the array to wrap.
   *
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly (
    @NonNull final Class<T> valueClass,
    @NonNull final T[] array
  ) {
    return new ArrayView<>(valueClass, array);
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param array An array to wrap.
   *
   * @return A readonly view instance.
   */
  static @NonNull View<Integer> readonly (final int[] array) {
    return new IntegerArrayView(array);
  }

  /**
   * @return The total number of values that this view currently store.
   */
  @NonNegative int getSize ();
  /**
   * Read a value of this view.
   *
   * @param index Index of the value to get.
   *
   * @return The value at the given index.
   *
   * @throws IndexOutOfBoundsException If the given index is not between 0 and the size of the view.
   */
  T get (@NonNegative @LessThan("getSize()") final int index)
  throws IndexOutOfBoundsException;

  /**
   * @return True if this view does not have any element in it.
   */
  default boolean isEmpty () {
    return getSize() <= 0;
  }

  /**
   * @return The type of value that this view currently store.
   */
  @NonNull Class<T> getValueClass ();

  /**
   * @return This view as a java collection.
   */
  default @NonNull Collection<T> toCollection () {
    @NonNull final List<T> list = new ArrayList<>(getSize());

    for (int index = 0, size = getSize(); index < size; ++index) {
      list.add(get(index));
    }

    return list;
  }

  @Override
  default @NonNull Iterator<T> iterator () {
    return new ViewIterator<>(this);
  }
}
