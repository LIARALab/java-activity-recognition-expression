package org.liara.data.blueprint;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A placeholder for a primitive of an expected type.
 */
public interface ValueBlueprint
  extends BlueprintElement
{
  /**
   * @return The type of primitive expected at this location.
   */
  @NonNull Primitive getType();
}
