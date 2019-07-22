package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public class StaticModelElement implements ModelElement
{
  @NonNull
  private static final View<@NonNull ModelElement> EMPTY_VIEW = View.readonly(
    ModelElement.class, new ModelElement[0]
  );

  @NonNull
  private final Model _model;

  @NonNull
  private final int _identifier;

  /**
   * Create a new instance of model element from a given building context.
   *
   * @param context A building context to use for instantiating this model element.
   */
  public StaticModelElement (@NonNull final ModelElementBuildingContext context) {
    _model = context.getModel();
    _identifier = context.getIdentifier();
  }

  /**
   * @see ModelElement#getIdentifier()
   */
  @Override
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  /**
   * @see ModelElement#getModel()
   */
  @Override
  public @NonNull Model getModel () {
    return _model;
  }

  /**
   * @see ModelElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElement> getChildren () {
    return EMPTY_VIEW;
  }
}
