package org.liara.support;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class Generics
{
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
