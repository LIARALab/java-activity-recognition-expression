package org.liara.data.graph;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A table column.
 */
public interface Column extends GraphElement {
  /**
   * @return The table that contains this column.
   */
  @NonNull Table getTable ();

  /**
   * @return The name of this column.
   */
  @NonNull String getName();

  /**
   * @return Primitive type of value stored into this column.
   */
  @NonNull Primitive<?> getType();
}
