package org.liara.data.graph;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * A data graph. A data graph is the description of some sort of database with its table,
 * their columns and their relationship. A graph is used for producing request to another
 * data management service.
 *
 * A graph is static, all computations are then done over transformations of this graph.
 */
public interface Graph
{
  /**
   * @return The name of the graph.
   */
  @NonNull String getName ();

  /**
   * @return A view over all tables of the graph.
   */
  @NonNull View<@NonNull Table> getTables ();

  /**
   * @return A view over all columns of the graph.
   */
  @NonNull View<@NonNull Column> getColumns ();

  /**
   * Find and return a table of this graph with the given name.
   *
   * This method throws a NoSuchElementException if no tables exists with the given name.
   *
   * @param name Name of the table to retrieve.
   *
   * @return The table with the given name.
   */
  @NonNull Table getTable (@NonNull final String name);

  /**
   * Return the name of the given table instance.
   *
   * @param table A table instance of this graph.
   *
   * @return The name of the given table instance.
   */
  @NonNull String getNameOf (@NonNull final Table table);

  /**
   * Return true if a table with the given name exists.
   *
   * @param name Name of the table to search.
   *
   * @return True if a table with the given name exists.
   */
  boolean hasTable (@NonNull final String name);
}
