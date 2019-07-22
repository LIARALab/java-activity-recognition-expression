package org.liara.data.table;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.util.LinkedList;
import java.util.List;

public class StaticDataTableDescriptor
  implements DataTableDescriptor
{
  @NonNull
  private final List<@NonNull ColumnDescriptor> _columns;

  @NonNull
  private final View<@NonNull ColumnDescriptor> _columnsView;

  @Nullable
  private String _name;

  public StaticDataTableDescriptor () {
    _columns = new LinkedList<>();
    _columnsView = View.readonly(ColumnDescriptor.class, _columns);
    _name = null;
  }


  public @Nullable String getName () {
    return _name;
  }

  public void setName (@Nullable final String name) {
    _name = name;
  }

  public @NonNull List<@NonNull ColumnDescriptor> updateColumns () {
    return _columns;
  }

  @Override
  public @NonNull View<@NonNull ColumnDescriptor> getColumns () {
    return _columnsView;
  }
}
