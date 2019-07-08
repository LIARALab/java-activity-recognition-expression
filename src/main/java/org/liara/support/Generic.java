package org.liara.support;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public interface Generic<Value>
{
  /**
   * @return Return the underlying type represented by this generic wrapper.
   */
  @NonNull Type getType ();

  /**
   * @return A view over each annotation of this generic.
   */
  @NonNull View<@NonNull Annotation> getAnnotations ();

  /**
   * @see AnnotatedElement#getAnnotation(Class)
   */
  @Nullable <T extends Annotation> T getAnnotation (@NonNull final Class<T> annotation);

  /**
   * @see AnnotatedElement#isAnnotationPresent(Class)
   */
  <T extends Annotation> boolean isAnnotationPresent (@NonNull final Class<T> annotation);

  /**
   * Compare two generic types annotations and return true if both of them are equals.
   *
   * @param otherGeneric Other generic type to compare to this one.
   *
   * @return True if both generic type have equals annotations.
   */
  default boolean hasSameAnnotationsAs (@NonNull final Generic<?> otherGeneric) {
    @NonNull final View<@NonNull Annotation> annotations = getAnnotations();

    if (otherGeneric.getAnnotations().getSize() != annotations.getSize()) {
      return false;
    }

    for (int index = 0, size = annotations.getSize(); index < size; ++index) {
      @NonNull final Annotation annotation = annotations.get(index);

      if (!annotation.equals(otherGeneric.getAnnotation(annotation.annotationType()))) {
        return false;
      }
    }

    return true;
  }
}
