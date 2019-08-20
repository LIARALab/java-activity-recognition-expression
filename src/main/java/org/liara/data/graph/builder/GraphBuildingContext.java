package org.liara.data.graph.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Graph;

public interface GraphBuildingContext {

  @NonNegative int getIdentifier(@NonNull final GraphElementBuilder builder);

  @NonNegative int getTableIdentifier(@NonNull final ColumnBuilder builder);

  @Nullable Graph getGraph();
}
