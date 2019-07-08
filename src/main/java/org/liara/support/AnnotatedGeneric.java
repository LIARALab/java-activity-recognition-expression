package org.liara.support;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class AnnotatedGeneric<Value> implements Generic<Value> {
  @NonNull
  private static final Set<@NonNull Annotation> SET = new HashSet<>();

  @NonNull
  private final AnnotatedType _type;

  @NonNull
  private final View<@NonNull Annotation> _annotations;

  private final int _hash;

  public AnnotatedGeneric (@NonNull final AnnotatedType type) {
    _type = type;
    _annotations = View.readonly(Annotation.class, type.getAnnotations());

    SET.addAll(Arrays.asList(type.getAnnotations()));
    _hash = Objects.hash(type.getType(), SET.hashCode());
    SET.clear();
  }

  @Override
  public @NonNull Type getType () {
    return _type.getType();
  }

  @Override
  public @NonNull View<@NonNull Annotation> getAnnotations () {
    return _annotations;
  }

  @Override
  public <T extends Annotation> @Nullable T getAnnotation (@NonNull final Class<T> annotation) {
    return _type.getAnnotation(annotation);
  }

  @Override
  public <T extends Annotation> boolean isAnnotationPresent (@NonNull final Class<T> annotation) {
    return _type.isAnnotationPresent(annotation);
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof Generic) {
      @NonNull final Generic otherGeneric = (Generic) other;

      return otherGeneric.getType().equals(_type.getType()) && hasSameAnnotationsAs(otherGeneric);
    }

    return false;
  }

  @Override
  public int hashCode () {
    return _hash;
  }
}
