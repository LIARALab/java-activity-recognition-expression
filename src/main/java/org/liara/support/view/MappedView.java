package org.liara.support.view;

import java.util.function.Function;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappedView<From, To> implements View<To> {
  @NonNull
  private final View<? extends From> _source;

  @NonNull
  private final Function<? super From, ? extends To> _mapper;

  public MappedView(
      @NonNull final View<? extends From> source,
      @NonNull final Function<? super From, ? extends To> mapper
  ) {
    _source = source;
    _mapper = mapper;
  }

  public MappedView(@NonNull final MappedView<From, To> toCopy) {
    _source = toCopy._source;
    _mapper = toCopy._mapper;
  }

  @Override
  public @NonNegative int getSize() {
    return _source.getSize();
  }

  @Override
  public To get(@NonNegative final int index) throws IndexOutOfBoundsException {
    return _mapper.apply(_source.get(index));
  }

  @Override
  public @NonNull Stream<To> stream() {
    return _source.stream().map(_mapper);
  }

  @Override
  public Object[] toArray() {
    return stream().toArray();
  }

  public @NonNull View<? extends From> getSource() {
    return _source;
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
