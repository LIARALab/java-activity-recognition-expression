package org.liara.data.model.descriptor;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * An object model descriptor.
 */
public interface ObjectDescriptor extends ModelElementDescriptor
{
  /**
   * @return A view over each keys of this descriptor.
   */
  @NonNull View<@NonNull String> getKeys ();

  /**
   * Return the index of the field identified by the given key.
   *
   * @param key The key of the field to retrieve.
   *
   * @return The index of the field identified by the given key.
   */
  @NonNegative int getFieldOf (@NonNull final String key);
}
