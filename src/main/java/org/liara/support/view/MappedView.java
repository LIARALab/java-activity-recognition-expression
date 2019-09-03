package org.liara.support.view;

import java.util.function.Function;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MappedView<From, To> extends BaseView<To> {

  @NonNull
  private final Class<To> _valueClass;

  @NonNull
  private final View<From> _source;

  @NonNull
  private final Function<From, To> _mapper;

  public MappedView(
      @NonNull final Class<To> valueClass,
      @NonNull final View<From> source,
      @NonNull final Function<From, To> mapper
  ) {
    _source = source;
    _mapper = mapper;
    _valueClass = valueClass;
  }

  @Override
  public @NonNegative int getSize() {
    return _source.getSize();
  }

  @Override
  public To get(
      @NonNegative @LessThan("getSize()") final int index
  )
      throws IndexOutOfBoundsException {
    return _mapper.apply(_source.get(index));
  }

  @Override
  public @NonNull Class<To> getValueClass() {
    return _valueClass;
  }

  public @NonNull View<From> getSource() {
    return _source;
  }
}
