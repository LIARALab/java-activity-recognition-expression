package org.liara.support.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface View<T> extends Iterable<T> {

  @SuppressWarnings("unchecked")
  static <T> @NonNull View<T> readonly(@NonNull final Class<T> valueClass) {
    return readonly(valueClass, (T[]) Array.newInstance(valueClass, 0));
  }

  /**
   * Wrap a view into a readonly instance.
   *
   * @param view A view to wrap.
   * @param <T> DataType of value stored into the view to wrap.
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly(@NonNull final View<T> view) {
    return view instanceof StaticView ? (StaticView<T>) view : new StaticView<>(view);
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param valueClass DataType of value stored into the given array.
   * @param array An array to wrap.
   * @param <T> DataType of value stored into the array to wrap.
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly(
      @NonNull final Class<T> valueClass,
      @NonNull final T[] array
  ) {
    return new ArrayView<>(valueClass, array);
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param valueClass DataType of value stored into the given list.
   * @param list A list to wrap.
   * @param <T> DataType of value stored into the array to wrap.
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly(
      @NonNull final Class<T> valueClass,
      @NonNull final List<? extends T> list
  ) {
    return new ListView<>(valueClass, list);
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param array An array to wrap.
   * @return A readonly view instance.
   */
  static @NonNull View<Integer> readonly(final int[] array) {
    return new IntegerArrayView(array);
  }

  /**
   * @return The total number of values that this view currently store.
   */
  @NonNegative int getSize();

  /**
   * Read a value of this view.
   *
   * @param index Index of the value to get.
   * @return The value at the given index.
   * @throws IndexOutOfBoundsException If the given index is not between 0 and the size of the
   * view.
   */
  T get(@NonNegative @LessThan("getSize()") final int index)
      throws IndexOutOfBoundsException;

  /**
   * @return True if this view does not have any element in it.
   */
  default boolean isEmpty() {
    return getSize() <= 0;
  }

  /**
   * @return The type of value that this view currently store.
   */
  @NonNull Class<T> getValueClass();

  /**
   * @return This view as a java collection.
   */
  default @NonNull Collection<T> toCollection() {
    @NonNull final List<T> list = new ArrayList<>(getSize());

    for (int index = 0, size = getSize(); index < size; ++index) {
      list.add(get(index));
    }

    return list;
  }

  default @NonNull T[] toArray() {
    @SuppressWarnings("unchecked")
    @NonNull final T[] result = (T[]) Array.newInstance(getValueClass(), getSize());

    for (int index = 0, size = getSize(); index < size; ++index) {
      result[index] = get(index);
    }

    return result;
  }

  @Override
  default @NonNull Iterator<T> iterator() {
    return new ViewIterator<>(this);
  }

  default <To> @NonNull View<To> map(
      @NonNull final Class<To> valueClass,
      @NonNull final Function<T, To> mapper
  ) {
    return new MappedView<>(valueClass, this, mapper);
  }
}
