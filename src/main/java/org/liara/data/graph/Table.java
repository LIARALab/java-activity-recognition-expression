package org.liara.data.graph;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * A table of data.
 */
public interface Table extends GraphElement
{
  /**
   * @return The name of this table.
   */
  @NonNull String getName ();

  /**
   * @return A view over all columns of this table.
   */
  @NonNull View<@NonNull Column> getColumns ();

  /**
   * Retrieve a column of this table by using its name.
   *
   * This method will throw a NoSuchElementException if no columns exists into this table with
   * the given name.
   *
   * @param name The name of the column to retrieve.
   *
   * @return The column with the given name.
   */
  @NonNull Column getColumn (@NonNull final String name);

  /**
   * Returns true if a column with the given name exists into this table.
   *
   * @param name The name of the column to retrieve.
   *
   * @return True if a column with the given name exists into this table.
   */
  boolean hasColumn (@NonNull final String name);
}
