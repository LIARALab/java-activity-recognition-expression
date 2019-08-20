package org.liara.data.graph.implementation;

import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Graph;
import org.liara.data.graph.GraphElement;
import org.liara.data.graph.builder.GraphBuildingContext;
import org.liara.data.graph.builder.GraphElementBuilder;

public class StaticGraphElement implements GraphElement {

  @NonNull
  private final Graph _graph;

  @NonNegative
  private final int _identifier;

  public StaticGraphElement(
      @NonNull final GraphBuildingContext context,
      @NonNull final GraphElementBuilder builder
  ) {
    _graph = Objects.requireNonNull(context.getGraph());
    _identifier = context.getIdentifier(builder);
  }

  /**
   * @see GraphElement#getIdentifier()
   */
  @Override
  public @NonNegative int getIdentifier() {
    return _identifier;
  }

  /**
   * @see GraphElement#getGraph()
   */
  @Override
  public @NonNull Graph getGraph() {
    return _graph;
  }
}
