package org.liara.data.graph.builder;

import java.util.Comparator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.implementation.StaticTable;
import org.liara.support.ListIndex;
import org.liara.support.view.View;

public class StaticTableBuilder
    implements TableBuilder {

  @NonNull
  private final ListIndex<@NonNull String, @NonNull ColumnBuilder> _columns;

  @NonNull
  private final View<@NonNull String> _columnNamesView;

  @NonNull
  private final View<@NonNull ColumnBuilder> _columnsView;

  public StaticTableBuilder() {
    _columns = new ListIndex<>(Comparator.comparing((@NonNull final String x) -> x));
    _columnNamesView = View.readonly(String.class, _columns.getKeys());
    _columnsView = View.readonly(ColumnBuilder.class, _columns.getValues());
  }

  public <Type> @NonNull ChainedStaticColumnBuilder<Type, StaticTableBuilder> column(
      @NonNull final String name
  ) {
    @NonNull final ChainedStaticColumnBuilder<Type, StaticTableBuilder> builder = (
        new ChainedStaticColumnBuilder<>(this)
    );

    putColumn(name, builder);

    return builder;
  }

  @Override
  public @NonNull StaticTable build(@NonNull final GraphBuildingContext context) {
    return new StaticTable(context, this);
  }

  public void putColumn(
      @NonNull final String name,
      @NonNull final ColumnBuilder builder
  ) {
    _columns.put(name, builder);
  }

  public void removeColumn(@NonNull final String name) {
    _columns.remove(name);
  }

  public void renameColumn(
      @NonNull final String oldName,
      @NonNull final String newName
  ) {
    _columns.setKey(oldName, newName);
  }

  @Override
  public @NonNull ColumnBuilder getColumn(@NonNull final String name) {
    return _columns.getValue(name);
  }

  @Override
  public boolean containsColumn(@NonNull final String name) {
    return _columns.containsKey(name);
  }

  @Override
  public @NonNull View<@NonNull String> getColumnNames() {
    return _columnNamesView;
  }

  @Override
  public @NonNull View<@NonNull ColumnBuilder> getColumns() {
    return _columnsView;
  }
}
