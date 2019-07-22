package org.liara.data.primitive;

import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.util.Arrays;

public class PrimitiveView implements View<@NonNull Primitive>
{
  @NonNull
  private final int[] _wrapped;

  public PrimitiveView (@NonNull final int[] wrapped) {
    _wrapped = wrapped;
  }

  @Override
  public @NonNegative int getSize () {
    return _wrapped.length;
  }

  @Override
  public @NonNull Primitive get (
    @NonNegative @LessThan("getBytes()") final int index
  ) throws IndexOutOfBoundsException {
    return Primitives.getPrimitives().get(_wrapped[index]);
  }

  @Override
  public @NonNull Class<Primitive> getValueClass () {
    return Primitive.class;
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof PrimitiveView) {
      @NonNull final PrimitiveView otherPrimitiveView = (PrimitiveView) other;

      return Arrays.equals(_wrapped, otherPrimitiveView._wrapped);
    }

    return false;
  }

  @Override
  public int hashCode () {
    return Arrays.hashCode(_wrapped);
  }
}
