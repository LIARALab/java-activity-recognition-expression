package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

import java.util.Arrays;

public class DataTableDescriptorFactory
{
  public @NonNull ColumnBuilder column (
    @NonNull final String name,
    @NonNull final Primitive<?> type
  ) {
    @NonNull final StaticColumnBuilder descriptor = new StaticColumnBuilder();

    descriptor.setName(name);
    descriptor.setType(type);

    return descriptor;
  }

  public @NonNull DataTableDescriptor table (
    @NonNull final String name,
    @NonNull final ColumnBuilder...columns
  ) {
    @NonNull final StaticTableBuilder descriptor = new StaticTableBuilder();

    descriptor.setName(name);
    descriptor.updateColumns().addAll(Arrays.asList(columns));

    return descriptor;
  }
}
