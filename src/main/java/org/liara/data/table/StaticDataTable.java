package org.liara.data.table;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public class StaticDataTable implements DataTable
{
  @NonNull
  private final String _name;

  @NonNull
  private final View<@NonNull Column> _columns;

  public StaticDataTable (
    @NonNull final String name,
    @NonNull final View<@NonNull Column> columns
  ) {
    _name = name;
    _columns = columns;
  }

  @Override
  public @NonNull View<@NonNull Column> getColumns () {
    return _columns;
  }

  @Override
  public @NonNull String getName () {
    return _name;
  }
}
