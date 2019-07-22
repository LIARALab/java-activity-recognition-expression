package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

/**
 * A data model.
 */
public class StaticModel
  implements Model
{
  @NonNull
  private final StaticModelData _data;

  public StaticModel (@NonNull final StaticModelData data) {
    _data = data;
  }

  @Override
  public @NonNull View<@NonNull ModelElement> getElements () {
    return _data.getElementsView();
  }

  @Override
  public @Nullable ModelElement getParentOf (@NonNull final ModelElement element) {
    if (element.getModel() != this) return null;

    final int parent = _data.getParents()[element.getIdentifier()];

    return parent == -1 ? null : _data.getElementsView().get(parent);
  }

  @Override
  public @NonNull ModelElement getRootElement () {
    return _data.getRoot();
  }
}
