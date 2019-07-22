package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface ModelElementBuildingContext
{
  /**
   * @return The model that is currently built.
   */
  @NonNull Model getModel ();

  /**
   * @return The identifier of the model element that is currently built.
   */
  int getIdentifier ();
}
