package org.liara.data.model.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class StaticValueDescriptor implements ValueDescriptor
{
  @NonNull
  private static final View<@NonNull ModelElementDescriptor> EMPTY_VIEW = View.readonly(
    ModelElementDescriptor.class, new ModelElementDescriptor[0]
  );

  @NonNull
  private Primitive<?> _type;

  /**
   * Instantiate a new descriptor that expect a value of a given type.
   *
   * @param type Type of value expected at this location.
   */
  public StaticValueDescriptor (@NonNull final Primitive<?> type) {
    _type = type;
  }

  /**
   * @see ValueDescriptor#getType()
   */
  @Override
  public @NonNull Primitive<?> getType () {
    return _type;
  }

  /**
   * Change the expected type at this location.
   *
   * @param type The new expected type at this location.
   */
  public void setType (final Primitive<?> type) {
    _type = type;
  }

  /**
   * @see ModelElementDescriptor#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElementDescriptor> getChildren () {
    return EMPTY_VIEW;
  }
}
