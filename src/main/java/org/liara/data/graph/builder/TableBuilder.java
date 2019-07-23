package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Table;
import org.liara.support.view.View;

public interface TableBuilder extends GraphElementBuilder
{
  @NonNull Table build (@NonNull final GraphBuildingContext context);

  void putColumn (@NonNull final String name, @NonNull final ColumnBuilder builder);

  void removeColumn (@NonNull final String name);

  void renameColumn (@NonNull final String oldName, @NonNull final String newName);

  @NonNull ColumnBuilder getColumn (@NonNull final String name);

  boolean containsColumn (@NonNull final String name);

  @NonNull View<@NonNull String> getColumnNames ();

  @NonNull View<@NonNull ColumnBuilder> getColumns ();
}
