package org.liara.data.mapping;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.bean.Bean;

public interface Mapping<T>
{
  static @NonNull Mapping<String> forward (@NonNull final Bean bean) {
    return BeanMapping.forward(bean);
  }

  static @NonNull Mapping<String> backward (@NonNull final Bean bean) {
    return BeanMapping.backward(bean);
  }

  /**
   * Return a field index from an object.
   *
   * @param name An object.
   *
   * @return The field index related to the given object.
   */
  @NonNegative int getField (@NonNull final T name);

  /**
   * Return an object from a field index.
   *
   * @param field A field index.
   *
   * @return The object related to the given field index.
   */
  @NonNull String getValue (@NonNegative final int field);

  /**
   * Return true if the given object is mapped to a field.
   *
   * @param value Name of the object to search.
   *
   * @return True if the given object is mapped to a field.
   */
  boolean isValueMapped (@NonNull final T value);

  /**
   * Return true if the given field is mapped to an object.
   *
   * @param field A field index to search.
   *
   * @return True if the given field index is mapped to an object.
   */
  boolean isFieldMapped (@NonNegative final int field);

  /**
   * @return The number of objects that are currently mapped.
   */
  @NonNegative int getSize ();

  @NonNull Class<T> getMappedClass ();
}
