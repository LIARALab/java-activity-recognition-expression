package org.liara.support.view;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ComputedView<T> implements View<T> {
  @NonNull
  private final IntFunction<T> _mapper;

  @NonNull
  private final Supplier<@NonNegative Integer> _size;

  public ComputedView(
      @NonNull final IntFunction<T> mapper,
      @NonNull final Supplier<@NonNegative Integer> size
  ) {
    _mapper = mapper;
    _size = size;
  }

  @Override
  public @NonNegative int getSize() {
    return _size.get();
  }

  @Override
  public @Nullable T get(@NonNegative final int index) throws IndexOutOfBoundsException {
    if (index < _size.get()) {
      return _mapper.apply(index);
    } else {
      throw new IndexOutOfBoundsException(
          "Unable to get the value at the index #" + index + " because the given index is out of " +
              "bounds [0, " + _size.get() + "[."
      );
    }
  }

  @Override
  public @NonNull Stream<T> stream() {
    return IntStream.range(0, _size.get()).mapToObj(_mapper);
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
