package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.util.LinkedList;
import java.util.List;

public class StaticTableBuilder
  implements DataTableDescriptor
{
  @NonNull
  private final List<@NonNull ColumnBuilder> _columns;

  @NonNull
  private final View<@NonNull ColumnBuilder> _columnsView;

  @Nullable
  private String _name;

  public StaticTableBuilder () {
    _columns = new LinkedList<>();
    _columnsView = View.readonly(ColumnBuilder.class, _columns);
    _name = null;
  }


  public @Nullable String getName () {
    return _name;
  }

  public void setName (@Nullable final String name) {
    _name = name;
  }

  public @NonNull List<@NonNull ColumnBuilder> updateColumns () {
    return _columns;
  }

  @Override
  public @NonNull View<@NonNull ColumnBuilder> getColumns () {
    return _columnsView;
  }
}
