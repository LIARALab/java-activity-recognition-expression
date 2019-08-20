package org.liara.support.generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

class RawGeneric<Value> implements Generic<Value> {

  @NonNull
  private static final View<@NonNull Annotation> EMPTY_VIEW = (
      View.readonly(Annotation.class, new Annotation[0])
  );

  @NonNull
  private static final Set<@NonNull Annotation> EMPTY_SET = new HashSet<>();

  @NonNull
  private final Type _type;

  private final int _hash;

  public RawGeneric(@NonNull final Type type) {
    _type = type;
    _hash = Objects.hash(type, new HashSet<>());
  }

  @Override
  public @NonNull Type getType() {
    return _type;
  }

  @Override
  public @NonNull View<@NonNull Annotation> getAnnotations() {
    return EMPTY_VIEW;
  }

  @Override
  public <T extends Annotation> @Nullable T getAnnotation(@NonNull final Class<T> annotation) {
    return null;
  }

  @Override
  public <T extends Annotation> boolean isAnnotationPresent(@NonNull final Class<T> annotation) {
    return false;
  }

  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof Generic) {
      @NonNull final Generic otherGeneric = (Generic) other;

      return otherGeneric.getType().equals(_type) && otherGeneric.getAnnotations().isEmpty();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return _hash;
  }
}
