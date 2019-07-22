package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

/**
 * An element of a data model.
 */
public interface ModelElement extends TreeElement
{
  /**
   * @return A number that fully identify this element into the parent model.
   */
  @NonNegative int getIdentifier ();

  /**
   * @return The parent model of this element.
   */
  @NonNull Model getModel ();

  /**
   * @return The parent model element of this element if exists, null otherwise.
   */
  default @Nullable ModelElement getParent () {
    return getModel().getParentOf(this);
  }

  /**
   * @return A view over each child element of this element.
   */
  @NonNull View<@NonNull ModelElement> getChildren();
}
