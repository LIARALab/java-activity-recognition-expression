package org.liara.support.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A read-only finished and sequential collection of elements.
 *
 * @param <T> Elements stored into the collection.
 */
public interface View<T> extends Iterable<T> {
  /**
   * Create a string that describe an existing view instance.
   *
   * @param view A view instance to describe.
   * @param <T> Type of element stored into the view to describe.
   *
   * @return A string that describe an existing view instance.
   */
  static <T> @NonNull String toString (@NonNull final View<T> view) {
      @NonNull final StringBuilder builder = new StringBuilder();
      builder.append(view.getClass().getName());
      builder.append('@');
      builder.append(Integer.toHexString(System.identityHashCode(view)));
      builder.append('[');

      for (int index = 0, size = view.getSize(); index < size; ++index) {
        builder.append(view.get(index).toString());
        if (index < size - 1) {
          builder.append(", ");
        }
      }

      builder.append(']');

      return builder.toString();
  }

  static boolean equals (@NonNull final View<?> left, @NonNull final View<?> right) {
    if (left.getSize() != right.getSize()) {
      return false;
    }

    for (@NonNegative int index = 0; index < left.getSize(); ++index) {
      if (!Objects.equals(left.get(index), right.get(index))) {
        return false;
      }
    }

    return true;
  }

  /**
   * @param <T> Type of element stored into the empty view.
   *
   * @return An empty view.
   */
  static <T> @NonNull View<T> empty() {
    return new EmptyView<>();
  }

  /**
   * Wrap an array into a readonly instance.
   *
   * @param array An array to wrap.
   *
   * @param <T> Type of value stored into the array to wrap.
   *
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly (final T[] array) {
    return new ArrayView<>(array);
  }

  /**
   * Concatenate views.
   *
   * @param views An array of views to concatenate.
   *
   * @param <T> Type of value stored into the views to concatenate.
   *
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> concatenate (@NonNull final View<T> ...views) {
    return new ConcatenatedView<>(views);
  }

  /**
   * Concatenate views.
   *
   * @param views An array of views to concatenate.
   *
   * @param <T> Type of value stored into the views to concatenate.
   *
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> concatenate (@NonNull final Collection<? extends View<T>> views) {
    return new ConcatenatedView<>(views);
  }

  /**
   * Wrap a list into a view instance.
   *
   * @param list A list to wrap.
   *
   * @param <T> DataType of value stored into the list to wrap.
   *
   * @return A readonly view instance.
   */
  static <T> @NonNull View<T> readonly(@NonNull final List<? extends T> list) {
    return new ListView<>(list);
  }

  static <T> @NonNull View<T> readonly(
      @NonNull final IntFunction<T> mapper,
      @NonNull final Supplier<@NonNegative Integer> size
  ) {
    return new ComputedView<>(mapper, size);
  }

  /**
   * @return The number of elements stored into the view.
   */
  @NonNegative int getSize();

  /**
   * Return an element stored into this view.
   *
   * @param index Index of the element to get from 0 (included) to the size of the view (excluded).
   *
   * @return The value at the given index.
   *
   * @throws IndexOutOfBoundsException If the given index is not between 0 (included) and the size of the view (excluded).
   */
  T get(@NonNegative @LessThan("getSize()") final int index) throws IndexOutOfBoundsException;

  /**
   * @return True if no element are stored into this view.
   */
  default boolean isEmpty() {
    return getSize() <= 0;
  }

  /**
   * @return A stream of each elements stored into this view.
   */
  @NonNull Stream<T> stream();

  /**
   * @return The content of this view as an array of elements.
   */
  Object[] toArray();

  /**
   * @see Iterable#iterator()
   */
  @Override
  default @NonNull Iterator<T> iterator() {
    return new ViewIterator<>(this);
  }

  /**
   * Return a view that is a mapping of the elements of this one.
   *
   * @param mapper A mapping function.
   *
   * @param <To> Type of elements stored into the resulting view.
   *
   * @return A view that is a mapping of the elements of this one.
   */
  default <To> @NonNull View<To> map(@NonNull final Function<? super T, To> mapper) {
    return new MappedView<>(this, mapper);
  }
}
