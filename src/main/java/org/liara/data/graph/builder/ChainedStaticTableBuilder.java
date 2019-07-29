package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.implementation.StaticTable;
import org.liara.support.view.View;

public class ChainedStaticTableBuilder<Parent> implements TableBuilder
{
  @NonNull
  private final Parent _parent;

  @NonNull
  private final StaticTableBuilder _builder;

  public ChainedStaticTableBuilder (@NonNull final Parent parent) {
    _parent = parent;
    _builder = new StaticTableBuilder();
  }

  @Override
  public @NonNull StaticTable build (final @NonNull GraphBuildingContext context) {
    return new StaticTable(context, this);
  }

  public <Type> @NonNull ChainedStaticColumnBuilder<Type, ChainedStaticTableBuilder<Parent>> column (
    @NonNull final String name
  ) {
    @NonNull final ChainedStaticColumnBuilder<Type, ChainedStaticTableBuilder<Parent>> builder = (
      new ChainedStaticColumnBuilder<>(this)
    );

    putColumn(name, builder);

    return builder;
  }

  @Override
  public void putColumn (
    final @NonNull String name,
    final @NonNull ColumnBuilder builder
  ) {
    _builder.putColumn(name, builder);
  }

  @Override
  public void removeColumn (final @NonNull String name) {
    _builder.removeColumn(name);
  }

  @Override
  public void renameColumn (
    final @NonNull String oldName,
    final @NonNull String newName
  ) {
    _builder.renameColumn(oldName, newName);
  }

  public @NonNull Parent endTable () {
    return _parent;
  }

  @Override
  public @NonNull ColumnBuilder getColumn (final @NonNull String name) {
    return _builder.getColumn(name);
  }

  @Override
  public boolean containsColumn (final @NonNull String name) {
    return _builder.containsColumn(name);
  }

  @Override
  public @NonNull View<@NonNull String> getColumnNames () {
    return _builder.getColumnNames();
  }

  @Override
  public @NonNull View<@NonNull ColumnBuilder> getColumns () {
    return _builder.getColumns();
  }
}
