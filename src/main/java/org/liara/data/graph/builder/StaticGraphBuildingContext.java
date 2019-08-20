package org.liara.data.graph.builder;

import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Graph;

public class StaticGraphBuildingContext implements GraphBuildingContext {

  @NonNull
  private final Map<@NonNull GraphElementBuilder, @NonNull Integer> _identifiers;

  @NonNull
  private final Map<@NonNull ColumnBuilder, @NonNull Integer> _tableIdentifiers;

  @Nullable
  private Graph _graph;

  public StaticGraphBuildingContext() {
    _identifiers = new HashMap<>();
    _tableIdentifiers = new HashMap<>();
    _graph = null;
  }

  public void setIdentifier(
      @NonNull final GraphElementBuilder builder,
      @NonNull final Integer identifier
  ) {
    _identifiers.put(builder, identifier);
  }

  public void setTableIdentifier(
      @NonNull final ColumnBuilder builder,
      @NonNull final Integer identifier
  ) {
    _tableIdentifiers.put(builder, identifier);
  }

  @Override
  public @NonNegative int getIdentifier(final @NonNull GraphElementBuilder builder) {
    return _identifiers.get(builder);
  }

  @Override
  public @NonNegative int getTableIdentifier(final @NonNull ColumnBuilder builder) {
    return _tableIdentifiers.get(builder);
  }

  @Override
  public @Nullable Graph getGraph() {
    return _graph;
  }

  public void setGraph(@Nullable final Graph graph) {
    _graph = graph;
  }

  public void clear() {
    _graph = null;
    _identifiers.clear();
    _tableIdentifiers.clear();
  }
}
