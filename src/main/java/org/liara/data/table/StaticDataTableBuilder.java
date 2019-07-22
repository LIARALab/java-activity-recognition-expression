package org.liara.data.table;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaticDataTableBuilder
{
  @Nullable
  private DataTableDescriptor _descriptor;

  @NonNull
  private final StaticColumnBuilder _columnBuilder;

  public StaticDataTableBuilder () {
    _columnBuilder = new StaticColumnBuilder();
  }

  public @NonNull DataTable build () {
    if (_descriptor == null) {
      throw new IllegalStateException(
        "Unable to build an instance of data table because no descriptor was given."
      );
    }

    @NonNull final List<@NonNull Column> columns         = new ArrayList<>();
    @NonNull final StaticDataTable       staticDataTable = new StaticDataTable(
      Objects.requireNonNull(_descriptor.getName()),
      View.readonly(Column.class, columns)
    );

    for (int index = 0, size = _descriptor.getColumns().getSize(); index < size; ++index) {
      _columnBuilder.setIdentifier(index);
      _columnBuilder.setDataTable(staticDataTable);
      _columnBuilder.setDescriptor(_descriptor.getColumns().get(index));
      columns.add(_columnBuilder.build());
    }

    _columnBuilder.clear();

    return staticDataTable;
  }

  public @Nullable DataTableDescriptor getDescriptor () {
    return _descriptor;
  }

  public void setDescriptor (@Nullable final DataTableDescriptor descriptor) {
    _descriptor = descriptor;
  }
}
