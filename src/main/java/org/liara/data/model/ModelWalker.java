package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.tree.TreeWalker;

public class ModelWalker extends TreeWalker<ModelElement>
{
  public ModelWalker (@NonNull final ModelElement root) {
    super(ModelElement.class, root);
  }

  public ModelWalker (@NonNull final TreeWalker<ModelElement> toCopy) {
    super(toCopy);
  }
}
