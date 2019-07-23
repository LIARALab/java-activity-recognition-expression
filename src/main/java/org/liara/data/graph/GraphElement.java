package org.liara.data.graph;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface GraphElement
{
  /**
   * @return The identifier of this table in the parent graph.
   */
  @NonNegative int getIdentifier ();

  /**
   * @return The parent graph of this element.
   */
  @NonNull Graph getGraph ();
}
