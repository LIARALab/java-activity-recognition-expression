package org.liara.data.primitive;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class NullablePrimitive<Type> extends Primitive<@Nullable Type>
{
  @NonNull
  private final Primitive<@NonNull Type> _nonNullType;

  public NullablePrimitive (@NonNull final Primitive<@NonNull Type> primitive) {
    super(primitive.getJavaClass(), "@Nullable " + primitive.getName());
    _nonNullType = primitive;
  }

  public NullablePrimitive (@NonNull final NullablePrimitive<Type> toCopy) {
    super(toCopy);
    _nonNullType = toCopy.getNonNullType();
  }

  /**
   * @return The non-null version of this primitive type.
   */
  public @NonNull Primitive<@NonNull Type> getNonNullType () {
    return _nonNullType;
  }
}
