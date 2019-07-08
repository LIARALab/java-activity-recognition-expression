package org.liara.data.bean;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class BeanPropertyType
  implements Type, AnnotatedType
{
  @NonNull
  private final AnnotatedType _annotatedType;

  public BeanPropertyType (@NonNull final AnnotatedType annotatedType) {
    _annotatedType = annotatedType;
  }

  @Override
  public AnnotatedType getAnnotatedOwnerType () {
    return _annotatedType.getAnnotatedOwnerType();
  }

  @Override
  public Type getType () {
    return _annotatedType.getType();
  }

  @Override
  public boolean isAnnotationPresent (final Class<? extends Annotation> annotationClass) {
    return _annotatedType.isAnnotationPresent(annotationClass);
  }

  @Override
  public <T extends Annotation> T getAnnotation (final Class<T> annotationClass) {
    return _annotatedType.getAnnotation(annotationClass);
  }

  @Override
  public Annotation[] getAnnotations () {
    return _annotatedType.getAnnotations();
  }

  @Override
  public <T extends Annotation> T[] getAnnotationsByType (final Class<T> annotationClass) {
    return _annotatedType.getAnnotationsByType(annotationClass);
  }

  @Override
  public <T extends Annotation> T getDeclaredAnnotation (final Class<T> annotationClass) {
    return _annotatedType.getDeclaredAnnotation(annotationClass);
  }

  @Override
  public <T extends Annotation> T[] getDeclaredAnnotationsByType (final Class<T> annotationClass) {
    return _annotatedType.getDeclaredAnnotationsByType(annotationClass);
  }

  @Override
  public Annotation[] getDeclaredAnnotations () {
    return _annotatedType.getDeclaredAnnotations();
  }

  @Override
  public String getTypeName () {
    return _annotatedType.getType().getTypeName();
  }

  public @NonNull AnnotatedType getAnnotatedType () {
    return _annotatedType;
  }

  private boolean hasSameAnotationAs (@NonNull final BeanPropertyType other) {
    if (other.getAnnotations().length != _annotatedType.getAnnotations().length) return false;

    @NonNull final Annotation[] annotations = other.getAnnotations();

    for (@NonNull final Annotation annotation : annotations) {
      if (
        !_annotatedType.isAnnotationPresent(annotation.annotationType()) ||
        !_annotatedType.getAnnotation(annotation.annotationType()).equals(annotation)
      ) { return false; }
    }

    return true;
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof BeanPropertyType) {
      @NonNull final BeanPropertyType otherBeanType = (BeanPropertyType) other;

      return (
        Objects.equals(
          _annotatedType.getType(),
          otherBeanType.getAnnotatedType().getType()
        ) && hasSameAnotationAs(otherBeanType)
      );
    }

    return false;
  }

  @Override
  public int hashCode () {
    return Objects.hash(_annotatedType);
  }
}
