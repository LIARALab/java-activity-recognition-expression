package org.liara.data.model.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

/**
 * An object that describe the configuration of a given model element.
 */
public interface ModelElementDescriptor
  extends TreeElement
{
  /**
   * @return A view over each children element of this descriptor.
   */
  @NonNull View<@NonNull ModelElementDescriptor> getChildren();
}
