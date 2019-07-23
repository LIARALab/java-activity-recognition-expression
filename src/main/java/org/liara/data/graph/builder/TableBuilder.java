package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Table;

public interface TableBuilder extends GraphElementBuilder
{
  @NonNull Table build (@NonNull final GraphBuildingContext context);
}
