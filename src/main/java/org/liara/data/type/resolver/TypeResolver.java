package org.liara.data.type.resolver;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.Type;

@FunctionalInterface
public interface TypeResolver
{
  /**
   * @return The common type resolver.
   */
  static @NonNull TypeResolver common () {
    return CommonTypeResolver.INSTANCE;
  }

  @Nullable Type resolve (final java.lang.reflect.@NonNull Type type);

  default @NonNull Type resolveOrThrow (final java.lang.reflect.@NonNull Type type) {
    @Nullable final Type result = resolve(type);

    if (result == null) {
      throw new Error("Unable to resolve the type " + type.getTypeName() + ".");
    } else {
      return result;
    }
  }
}
