package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Graph;
import org.liara.support.view.View;

public interface GraphBuilder
{
  /**
   * Build the described graph.
   *
   * @return A new graph instance based upon this builder configuration.
   */
  @NonNull Graph build ();

  /**
   * Add or replace a table of this graph.
   *
   * @param name Name of the table to add or replace.
   * @param builder The builder to use for instantiating the given table.
   */
  void putTable (@NonNull final String name, @NonNull final TableBuilder builder);

  /**
   * Remove a table from this graph.
   *
   * @param name Name of the table to remove.
   */
  void removeTable (@NonNull final String name);

  /**
   * Rename a table of this graph.
   *
   * @param oldName Name of the table to rename.
   * @param newName New name to set for the given table.
   */
  void renameTable (@NonNull final String oldName, @NonNull final String newName);

  /**
   * Return a table of this graph.
   *
   * @param name Name of the table to get.
   *
   * @return The builder that instantiate the given table.
   */
  @NonNull TableBuilder getTable (@NonNull final String name);

  /**
   * Return true if a table with the given name exists into this graph.
   *
   * @param name Name of the table to find.
   *
   * @return True f a table with the given name exists into this graph.
   */
  boolean containsTable (@NonNull final String name);

  /**
   * @return A view over all tables names of this graph.
   */
  @NonNull View<@NonNull String> getTableNames();

  /**
   * @return A view over all tables of this graph.
   */
  @NonNull View<@NonNull TableBuilder> getTables();
}