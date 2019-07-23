package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Column;
import org.liara.data.primitive.Primitive;

/**
 * An object that describe a data table's column.
 */
public interface ColumnBuilder extends GraphElementBuilder
{
  /**
   * Build the column in a given context.
   *
   * @param context Building context of the column to instantiate.
   *
   * @return A new column instance.
   */
  @NonNull Column build (@NonNull final GraphBuildingContext context);

  /**
   * @return The name of this column.
   */
  @Nullable String getName();

  /**
   * @return Primitive type of value stored into this column.
   */
  @Nullable Primitive<?> getType();
}
