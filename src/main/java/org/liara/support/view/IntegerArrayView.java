package org.liara.support.view;

import java.util.Arrays;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class IntegerArrayView extends BaseView<Integer> {

  private final int[] _wrapped;

  public IntegerArrayView(@NonNull final int[] wrapped) {
    _wrapped = wrapped;
  }

  public IntegerArrayView(@NonNull final IntegerArrayView toCopy) {
    _wrapped = toCopy._wrapped;
  }

  @Override
  public @NonNegative int getSize() {
    return _wrapped.length;
  }

  @Override
  public @Nullable Integer get(
      @NonNegative @LessThan("getBytes()") final int index
  )
      throws IndexOutOfBoundsException {
    return _wrapped[index];
  }

  @Override
  public @NonNull Class<Integer> getValueClass() {
    return int.class;
  }

  @Override
  public @NonNull Stream<Integer> stream() {
    return Arrays.stream(_wrapped).boxed();
  }
}
