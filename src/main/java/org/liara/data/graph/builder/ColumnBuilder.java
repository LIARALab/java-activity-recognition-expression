package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Column;
import org.liara.data.primitive.Primitive;

/**
 * An object that describe a data table's column.
 */
public interface ColumnBuilder<Type> extends GraphElementBuilder {

  /**
   * Build the column in a given context.
   *
   * @param context Building context of the column to instantiate.
   * @return A new column instance.
   */
  @NonNull Column<Type> build(@NonNull final GraphBuildingContext context);

  /**
   * @return Primitive type of value stored into this column.
   */
  @Nullable Primitive<Type> getType();
}
