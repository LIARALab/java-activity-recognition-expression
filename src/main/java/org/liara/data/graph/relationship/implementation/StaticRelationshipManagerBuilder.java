package org.liara.data.graph.relationship.implementation;

import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Graph;

public class StaticRelationshipManagerBuilder {
  @Nullable
  private Graph _graph;

  @NonNull
  private final List<@NonNull StaticRelationshipBuilder> _relationships;

  public StaticRelationshipManagerBuilder () {
    _graph = null;
    _relationships = new ArrayList<>(32);
  }

  public StaticRelationshipManagerBuilder (@NonNull final StaticRelationshipManagerBuilder toCopy) {
    _graph = toCopy.getGraph();
    _relationships = new ArrayList<>(toCopy.getRelationships());
  }

  public @Nullable Graph getGraph() {
    return _graph;
  }

  public void setGraph(@Nullable final Graph graph) {
    _graph = graph;
  }

  public @NonNull List<@NonNull StaticRelationshipBuilder> getRelationships() {
    return _relationships;
  }

  public void setRelationships (
      @Nullable final List<@NonNull StaticRelationshipBuilder> relationships
  ) {
    _relationships.clear();

    if (relationships != null) {
      _relationships.addAll(relationships);
    }
  }
}
