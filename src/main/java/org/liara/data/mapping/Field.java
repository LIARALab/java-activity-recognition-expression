package org.liara.data.mapping;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A table column.
 */
public interface Field {
  /**
   * @see Object#hashCode()
   */
  static @NonNegative int hash (@NonNull final Field element) {
    return element.getIdentifier();
  }

  /**
   * @return The parent mapping of this field.
   */
  @NonNull Mapping getMapping();

  /**
   * @return The name of this column.
   */
  @NonNull String getName();

  /**
   * @return A non-negative integer that fully identify this field into its parent mapping.
   */
  @NonNegative int getIdentifier();

  /**
   * @return The structure that contains this field.
   */
  @NonNull Structure getStructure();

  /**
   * @return Primitive type of value stored into this column.
   */
  @NonNull Primitive<?> getType();
}
