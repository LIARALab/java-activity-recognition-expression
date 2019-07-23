package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

public interface DataTableDescriptor
{
  @Nullable String getName ();

  @NonNull View<@NonNull ColumnBuilder> getColumns ();
}
