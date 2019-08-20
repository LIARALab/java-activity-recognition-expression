package org.liara.data.blueprint;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A finite list of static elements.
 */
public interface TupleBlueprint
    extends BlueprintElement {

  /**
   * Return the index of the given child in this tuple if exists, -1 otherwise.
   *
   * @param value A child element to check.
   * @return The index of the given child in this tuple if exists, -1 otherwise.
   */
  @NonNegative int getIndexOf(@NonNull final BlueprintElement value);
}
