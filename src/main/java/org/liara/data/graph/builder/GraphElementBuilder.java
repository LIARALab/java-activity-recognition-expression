package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.GraphElement;

public interface GraphElementBuilder
{
  @NonNull GraphElement build (@NonNull final GraphBuildingContext context);
}
