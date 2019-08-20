package org.liara.data.graph.implementation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Column;
import org.liara.data.graph.Graph;
import org.liara.data.graph.Table;
import org.liara.support.view.View;

public class StaticGraph implements Graph {

  @NonNull
  private final StaticGraphContent _content;

  @NonNull
  private final View<@NonNull Table> _tables;

  @NonNull
  private final View<@NonNull Column> _columns;

  public StaticGraph(@NonNull final StaticGraphContent content) {
    _content = content;
    _tables = View.readonly(Table.class, _content.getTables().getValues());
    _columns = View.readonly(Column.class, _content.getColumns());
  }

  @Override
  public @NonNull String getName() {
    return Objects.requireNonNull(_content.getName());
  }

  @Override
  public @NonNull View<@NonNull Table> getTables() {
    return _tables;
  }

  @Override
  public @NonNull View<@NonNull Column> getColumns() {
    return _columns;
  }

  @Override
  public @NonNull Table getTable(@NonNull final String name) {
    return _content.getTables().getValue(name);
  }

  @Override
  public @NonNull String getNameOf(@NonNull final Table table) {
    return _content.getTables().getKey(table.getIdentifier());
  }

  @Override
  public boolean hasTable(@NonNull final String name) {
    return _content.getTables().containsKey(name);
  }
}
