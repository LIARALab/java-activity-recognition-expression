package org.liara.data.graph.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Column;
import org.liara.data.graph.Graph;
import org.liara.data.graph.Table;
import org.liara.support.view.View;

public class StaticTable
  implements Table
{
  @NonNull
  private final String _name;

  @NonNull
  private final View<@NonNull Column> _columns;

  public StaticTable (
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
  public @NonNull Column getColumn (final @NonNull String name) {
    return null;
  }

  @Override
  public boolean hasColumn (final @NonNull String name) {
    return false;
  }

  @Override
  public @NonNull String getName () {
    return _name;
  }

  @Override
  public @NonNegative int getIdentifier () {
    return 0;
  }

  @Override
  public @NonNull Graph getGraph () {
    return null;
  }
}
