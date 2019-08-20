package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Table;
import org.liara.support.view.View;

public interface TableBuilder extends GraphElementBuilder {

  @NonNull Table build(@NonNull final GraphBuildingContext context);

  @NonNull ColumnBuilder getColumn(@NonNull final String name);

  boolean containsColumn(@NonNull final String name);

  @NonNull View<@NonNull String> getColumnNames();

  @NonNull View<? extends @NonNull ColumnBuilder> getColumns();
}
