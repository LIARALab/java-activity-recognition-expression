package org.liara.support.tree;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * A tree structure element.
 */
public interface TreeElement {

  /**
   * @return A view over each children of this tree element.
   */
  @NonNull View<@NonNull ? extends TreeElement> getChildren();
}
