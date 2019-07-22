package org.liara.data.model.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class StaticNullDescriptor
  implements NullDescriptor
{
  @NonNull
  public static final NullDescriptor INSTANCE = new StaticNullDescriptor();

  @NonNull
  private static final View<@NonNull ModelElementDescriptor> EMPTY_VIEW = View.readonly(
    ModelElementDescriptor.class, new ModelElementDescriptor[0]
  );

  /**
   * @see ModelElementDescriptor#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElementDescriptor> getChildren () {
    return EMPTY_VIEW;
  }
}
