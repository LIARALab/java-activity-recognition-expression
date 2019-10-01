package org.liara.support.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ConcatenatedView<T> implements View<T> {
  @NonNull
  private final List<@NonNull View<T>> _views;

  public ConcatenatedView(@NonNull final View<T> ...views) {
    _views = new ArrayList<>(views.length);
    _views.addAll(Arrays.asList(views));
  }

  public ConcatenatedView(@NonNull final Collection<? extends @NonNull View<T>> views) {
    _views = new ArrayList<>(views.size());
    _views.addAll(views);
  }

  @Override
  public @NonNegative int getSize() {
    @NonNegative int size = 0;

    for (@NonNull final View<T> view : _views) {
      size += view.getSize();
    }

    return size;
  }

  @Override
  public @Nullable T get(@NonNegative final int index) throws IndexOutOfBoundsException {
    @NonNegative int offset = 0;

    for (@NonNull final View<T> view : _views) {
      if (index - offset < view.getSize()) {
        return view.get(index - offset);
      } else {
        offset += view.getSize();
      }
    }

    throw new IndexOutOfBoundsException(
        "Unable to get the value at the index #" + index + " because the given index is out of " +
            "bounds [0, " + offset + "[."
    );
  }

  @Override
  public @NonNull Stream<T> stream() {
    if (_views.size() == 0) {
      return Stream.empty();
    } else {
      @NonNull Stream<T> result = _views.get(0).stream();

      for (@NonNegative int index = 1; index < _views.size(); ++index) {
        result = Stream.concat(result, _views.get(index).stream());
      }

      return result;
    }
  }

  @Override
  public Object[] toArray() {
    return stream().toArray();
  }

  @Override
  public @NonNull String toString() {
    return View.toString(this);
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) {
      return false;
    }

    if (other == this) {
      return true;
    }

    if (other instanceof View<?>) {
      return View.equals(this, (View<?>) other);
    }

    return false;
  }
}
