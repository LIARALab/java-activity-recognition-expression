package org.liara.data.blueprint;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

/**
 * An element of a blueprint.
 */
public interface BlueprintElement extends TreeElement {

  /**
   * @return A number that fully identify this element into the parent blueprint.
   */
  @NonNegative int getIdentifier();

  /**
   * @return The parent blueprint of this element.
   */
  @NonNull Blueprint getBlueprint();

  /**
   * @return The parent blueprint element of this element if exists, null otherwise.
   */
  default @Nullable BlueprintElement getParent() {
    return getBlueprint().getParentOf(this);
  }

  /**
   * @return A view over each child element of this element.
   */
  @NonNull View<@NonNull ? extends BlueprintElement> getChildren();
}
