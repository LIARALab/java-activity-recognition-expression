package org.liara.data.table;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A table column.
 */
public interface Column {
  /**
   * @return The identifier of this column into the parent table.
   */
  @NonNegative int getIdentifier();

  /**
   * @return The table that contains this column.
   */
  @NonNull DataTable getDataTable();

  /**
   * @return The name of this column.
   */
  @NonNull String getName();

  /**
   * @return Primitive type of value stored into this column.
   */
  @NonNull Primitive<?> getType();
}
