package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

public class StaticValueModel extends StaticModelElement implements ValueModel
{
  @NonNull
  private final Primitive _type;

  /**
   * Instantiate a new static value model.
   *
   * @param context The building context to use for instantiating this value model.
   * @param type The expected type of this value model.
   */
  public StaticValueModel (
    @NonNull final ModelElementBuildingContext context,
    @NonNull final Primitive<?> type
  ) {
    super(context);
    _type = type;
  }

  /**
   * @see ValueModel#getType()
   */
  @Override
  public @NonNull Primitive<?> getType () {
    return _type;
  }
}
