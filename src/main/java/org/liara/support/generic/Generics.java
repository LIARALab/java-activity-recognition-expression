package org.liara.support.generic;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class Generics
{
  @NonNull
  public static final Generic<@NonNull Boolean> BOOLEAN = new StaticGeneric<@NonNull Boolean>() {};

  @NonNull
  public static final Generic<@NonNull Byte> BYTE = new StaticGeneric<@NonNull Byte>() {};

  @NonNull
  public static final Generic<@NonNull Character> CHARACTER = (
    new StaticGeneric<@NonNull Character>() {}
  );

  @NonNull
  public static final Generic<@NonNull Double> DOUBLE = new StaticGeneric<@NonNull Double>() {};

  @NonNull
  public static final Generic<@NonNull Float> FLOAT = new StaticGeneric<@NonNull Float>() {};

  @NonNull
  public static final Generic<@NonNull Integer> INTEGER = new StaticGeneric<@NonNull Integer>() {};

  @NonNull
  public static final Generic<@NonNull Long> LONG = new StaticGeneric<@NonNull Long>() {};

  @NonNull
  public static final Generic<@NonNull Short> SHORT = new StaticGeneric<@NonNull Short>() {};

  @NonNull
  public static final Generic<@NonNull String> STRING = new StaticGeneric<@NonNull String>() {};

  @NonNull
  public static final Generic<@Nullable Boolean> NULLABLE_BOOLEAN = (
    new StaticGeneric<@Nullable Boolean>() {}
  );

  @NonNull
  public static final Generic<@Nullable Byte> NULLABLE_BYTE = (
    new StaticGeneric<@Nullable Byte>() {}
  );

  @NonNull
  public static final Generic<@Nullable Character> NULLABLE_CHARACTER = (
    new StaticGeneric<@Nullable Character>() {}
  );

  @NonNull
  public static final Generic<@Nullable Double> NULLABLE_DOUBLE = (
    new StaticGeneric<@Nullable Double>() {}
  );

  @NonNull
  public static final Generic<@Nullable Float> NULLABLE_FLOAT = (
    new StaticGeneric<@Nullable Float>() {}
  );

  @NonNull
  public static final Generic<@Nullable Integer> NULLABLE_INTEGER = (
    new StaticGeneric<@Nullable Integer>() {}
  );

  @NonNull
  public static final Generic<@Nullable Long> NULLABLE_LONG = (
    new StaticGeneric<@Nullable Long>() {}
  );

  @NonNull
  public static final Generic<@Nullable Short> NULLABLE_SHORT = (
    new StaticGeneric<@Nullable Short>() {}
  );

  @NonNull
  public static final Generic<@Nullable String> NULLABLE_STRING = (
    new StaticGeneric<@Nullable String>() {}
  );

  /**
   * Capture the given Java type as a generic.
   *
   * @param type The type to capture.
   *
   * @return A generic that represent the given Java type.
   */
  public static @NonNull Generic<?> capture (@NonNull final Type type) {
    return new RawGeneric<>(type);
  }

  /**
   * Capture the given annotated Java type as a generic.
   *
   * @param type The type to capture.
   *
   * @return A generic that represent the given annotated Java type.
   */
  public static @NonNull Generic<?> capture (@NonNull final AnnotatedType type) {
    return new AnnotatedGeneric<>(type);
  }

  /**
   * Capture the given Java class as a generic.
   *
   * @param clazz A Java class to capture.
   *
   * @return A generic that represent the given Java class.
   */
  public static <T> @NonNull Generic<T> capture (@NonNull final Class<T> clazz) {
    return new RawGeneric<>(clazz);
  }

  /**
   * Capture the given method return type as a generic.
   *
   * @param method A method from which capturing the return type.
   *
   * @return A generic that represent the given method return type.
   */
  public static @NonNull Generic<?> capture (@NonNull final Method method) {
    if (method.getAnnotatedReturnType().getAnnotations().length > 0) {
      return new AnnotatedGeneric<>(method.getAnnotatedReturnType());
    } else {
      return new RawGeneric<>(method.getGenericReturnType());
    }
  }

  /**
   * Capture the given field type as a generic.
   *
   * @param field A field from which capturing the type.
   *
   * @return A generic that represent the given field type.
   */
  public static @NonNull Generic<?> capture (@NonNull final Field field) {
    if (field.getAnnotatedType().getAnnotations().length > 0) {
      return new AnnotatedGeneric<>(field.getAnnotatedType());
    } else {
      return new RawGeneric<>(field.getGenericType());
    }
  }

  /**
   * Capture the given method parameter type as a generic.
   *
   * @param method The method from which capturing a parameter type.
   * @param parameter The index of the parameter to capture.
   *
   * @return A generic type that represent the given parameter type.
   */
  public static @NonNull Generic<?> capture (
    @NonNull final Method method,
    @NonNegative final int parameter
  ) {
    @NonNull final AnnotatedType type = method.getAnnotatedParameterTypes()[parameter];

    if (type.getAnnotations().length > 0) {
      return new AnnotatedGeneric<>(type);
    } else {
      return new RawGeneric<>(type.getType());
    }
  }
}
