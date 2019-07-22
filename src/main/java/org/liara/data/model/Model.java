package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

public interface Model
{
  /**
   * @return A view over each elements of this model.
   */
  @NonNull View<@NonNull ModelElement> getElements ();

  /**
   * Return the parent element of a given model element if any.
   *
   * @param element The element to check.
   *
   * @return The parent element of the given model element if any, null otherwise.
   */
  @Nullable ModelElement getParentOf (@NonNull ModelElement element);

  /**
   * @return The root element of this model.
   */
  @NonNull ModelElement getRootElement ();
}
