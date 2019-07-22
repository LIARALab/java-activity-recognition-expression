package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A placeholder for a primitive of an expected type.
 */
public interface ValueModel extends ModelElement
{
  /**
   * @return The type of primitive expected at this location.
   */
  @NonNull Primitive getType();
}
