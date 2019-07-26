package org.liara.data.primitive;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.AnnotatedElement;

public class Primitive<Type> {
  @NonNegative
  private final int _identifier;

  @NonNull
  private final Class<Type> _javaClass;

  @NonNull
  private final String _name;

  /**
   * Instantiate a new primitive type and assign an identifier to it.
   *
   * @param javaClass A Java type used to store values of this primitive type.
   */
  public Primitive (@NonNull final Class<Type> javaClass, @NonNull final String name) {
    _javaClass = javaClass;
    _identifier = Primitives.register(this);
    _name = name;
  }

  /**
   * Copy an existing primitive type instance.
   *
   * @param toCopy A primitive type instance to copy.
   */
  public Primitive (@NonNull final Primitive<Type> toCopy) {
    _javaClass = toCopy.getJavaClass();
    _identifier = toCopy.getIdentifier();
    _name = toCopy.getName();
  }

  /**
   * @return The Java type used to store values of this primitive type.
   */
  public @NonNull Class<Type> getJavaClass () {
    return _javaClass;
  }

  /**
   * @return A non-negative integer that fully represent this primitive type.
   */
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  public @NonNull String getName () {
    return _name;
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof Primitive) {
      return _identifier == ((Primitive) other).getIdentifier();
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode () {
    return _identifier;
  }

  @Override
  public @NonNull String toString () {
    return _name;
  }
}
