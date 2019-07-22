package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public class StaticModelElementBuildingContext
  implements ModelElementBuildingContext
{
  @NonNull
  private Model _model;

  private int _identifier;

  /**
   * Create a new building context for a given model.
   *
   * @param model A parent model to build.
   */
  public StaticModelElementBuildingContext (@NonNull final Model model) {
    _model = model;
    _identifier = 0;
  }

  @Override
  public @NonNull Model getModel () {
    return _model;
  }

  /**
   * @param model The new model that is currently built.
   */
  public void setModel (@NonNull final Model model) {
    _model = model;
  }

  @Override
  public int getIdentifier () {
    return _identifier;
  }

  /**
   * @param identifier The identifier of the element that is currently built.
   */
  public void setIdentifier (final int identifier) {
    _identifier = identifier;
  }
}
