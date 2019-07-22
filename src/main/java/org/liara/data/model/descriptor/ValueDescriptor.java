package org.liara.data.model.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

/**
 * A descriptor of a placeholder that expected a value of a given type.
 */
public interface ValueDescriptor
  extends ModelElementDescriptor
{
  /**
   * @return The type of value expected at this location.
   */
  @NonNull Primitive<?> getType();
}
