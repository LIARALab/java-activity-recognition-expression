package org.liara.data.table;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * A table of data.
 */
public interface DataTable
{
  /**
   * @return A view over all columns of this table.
   */
  @NonNull View<@NonNull Column> getColumns ();

  /**
   * @return The name of this table.
   */
  @NonNull String getName();
}
