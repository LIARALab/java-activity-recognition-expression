package org.liara.data.table.descriptor;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;

/**
 * An object that describe a data table's column.
 */
public interface ColumnDescriptor
{
  /**
   * @return The name of this column.
   */
  @Nullable String getName();

  /**
   * @return Primitive type of value stored into this column.
   */
  @Nullable Primitive<?> getType();
}
