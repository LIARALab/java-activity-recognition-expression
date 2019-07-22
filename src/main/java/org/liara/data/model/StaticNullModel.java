package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public class StaticNullModel extends StaticModelElement implements NullModel
{
  /**
   * Instantiate a new static null model.
   *
   * @param context The building context to use for instantiating this null model.
   */
  public StaticNullModel (@NonNull final ModelElementBuildingContext context) {
    super(context);
  }
}
