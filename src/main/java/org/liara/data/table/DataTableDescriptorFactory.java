package org.liara.data.table;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

import java.util.Arrays;

public class DataTableDescriptorFactory
{
  public @NonNull ColumnDescriptor column (
    @NonNull final String name,
    @NonNull final Primitive<?> type
  ) {
    @NonNull final StaticColumnDescriptor descriptor = new StaticColumnDescriptor();

    descriptor.setName(name);
    descriptor.setType(type);

    return descriptor;
  }

  public @NonNull DataTableDescriptor table (
    @NonNull final String name,
    @NonNull final ColumnDescriptor ...columns
  ) {
    @NonNull final StaticDataTableDescriptor descriptor = new StaticDataTableDescriptor();

    descriptor.setName(name);
    descriptor.updateColumns().addAll(Arrays.asList(columns));

    return descriptor;
  }
}
