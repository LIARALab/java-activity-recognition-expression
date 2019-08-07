package org.liara.data.graph.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Graph;
import org.liara.data.graph.implementation.StaticGraph;
import org.liara.data.graph.implementation.StaticGraphContent;
import org.liara.support.ListIndex;
import org.liara.support.view.View;

import java.util.Comparator;

public class StaticGraphBuilder implements GraphBuilder
{
  @NonNull
  private final ListIndex<@NonNull String, @NonNull TableBuilder> _tables;

  @NonNull
  private final View<@NonNull String> _tableNamesView;

  @NonNull
  private final View<@NonNull TableBuilder> _tablesView;

  @NonNull
  private final StaticGraphBuildingContext _context;

  @Nullable
  private String _name;

  public StaticGraphBuilder () {
    this(16);
  }

  public StaticGraphBuilder (@NonNegative final int capacity) {
    _tables = new ListIndex<>(Comparator.comparing((@NonNull String x) -> x));
    _tableNamesView = View.readonly(String.class, _tables.getKeys());
    _tablesView = View.readonly(TableBuilder.class, _tables.getValues());
    _context = new StaticGraphBuildingContext();
    _name = null;
  }

  @Override
  public @NonNull Graph build () {
    @NonNull final StaticGraphContent content = new StaticGraphContent();
    @NonNull final Graph graph = new StaticGraph(content);

    content.setName(_name);

    _context.setGraph(graph);

    assignIdentifiers();

    for (int table = 0, tables = _tables.getSize(); table < tables; ++table) {
      content.getTables().put(_tables.getKey(table), _tables.getValue(table).build(_context));

      @NonNull final View<? extends @NonNull ColumnBuilder> columnBuilders = (
        _tables.getValue(table).getColumns()
      );

      for (int column = 0, columns = columnBuilders.getSize(); column < columns; ++column) {
        content.getColumns().add(columnBuilders.get(column).build(_context));
      }
    }

    _context.clear();

    return graph;
  }

  private void assignIdentifiers () {
    @NonNegative int nextColumnIdentifier = 0;

    for (int table = 0, tables = _tables.getSize(); table < tables; ++table) {
      @NonNull final View<? extends @NonNull ColumnBuilder> columnBuilders = (
        _tables.getValue(table).getColumns()
      );

      _context.setIdentifier(_tables.getValue(table), table);

      for (int column = 0, columns = columnBuilders.getSize(); column < columns; ++column) {
        _context.setIdentifier(columnBuilders.get(column), nextColumnIdentifier);
        _context.setTableIdentifier(columnBuilders.get(column), table);
        nextColumnIdentifier += 1;
      }
    }
  }

  /**
   * Create a new table builder and return it for editing this graph.
   *
   * @param name Name of the new table to create.
   *
   * @return A builder that allows to editing the created table.
   */
  public @NonNull ChainedStaticTableBuilder<StaticGraphBuilder> table (
    @NonNull final String name
  ) {
    @NonNull final ChainedStaticTableBuilder<StaticGraphBuilder> builder = (
      new ChainedStaticTableBuilder<>(this)
    );

    putTable(name, builder);

    return builder;
  }

  /**
   * Add or replace a table of this graph.
   *
   * @param name Name of the table to add or replace.
   * @param builder The builder to use for instantiating the given table.
   */
  public void putTable (
    @NonNull final String name, 
    @NonNull final TableBuilder builder
  ) {
    _tables.put(name, builder);
  }

  /**
   * Remove a table from this graph.
   *
   * @param name Name of the table to remove.
   */
  public void removeTable (@NonNull final String name) {
    _tables.remove(name);
  }

  /**
   * Rename a table of this graph.
   *
   * @param oldName Name of the table to rename.
   * @param newName New name to set for the given table.
   */
  public void renameTable (
    @NonNull final String oldName,
    @NonNull final String newName
  ) {
    _tables.setKey(oldName, newName);
  }

  /**
   * @see GraphBuilder#getTable(String)
   */
  @Override
  public @NonNull TableBuilder getTable (@NonNull final String name) {
    return _tables.getValue(name);
  }

  /**
   * @see GraphBuilder#containsTable(String)
   */
  @Override
  public boolean containsTable (@NonNull final String name) {
    return _tables.containsKey(name);
  }

  /**
   * @see GraphBuilder#getTableNames()
   */
  @Override
  public @NonNull View<@NonNull String> getTableNames () {
    return _tableNamesView;
  }

  /**
   * @see GraphBuilder#getTables()
   */
  @Override
  public @NonNull View<@NonNull TableBuilder> getTables () {
    return _tablesView;
  }

  public @Nullable String getName () {
    return _name;
  }

  public void setName (@Nullable final String name) {
    _name = name;
  }
}
