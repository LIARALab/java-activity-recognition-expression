package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.util.Objects;

public class StaticModelData
{
  @NonNull
  private final ModelElement[] _elements;

  @NonNull
  private final View<@NonNull ModelElement> _elementsView;

  private final int[] _parents;

  @Nullable
  private ModelElement _root;

  public StaticModelData (@NonNegative final int size) {
    _elements = new ModelElement[size];
    _elementsView = View.readonly(ModelElement.class, _elements);
    _parents = new int[size];
    _root = null;
  }

  public @NonNull ModelElement getRoot () {
    return Objects.requireNonNull(_root);
  }

  public void setRoot (@Nullable final ModelElement root) {
    _root = root;
  }

  public @NonNull View<@NonNull ModelElement> getElementsView () {
    return _elementsView;
  }

  public int[] getParents () {
    return _parents;
  }

  public void registerElement (
    @NonNegative final int identifier,
    @NonNull final ModelElement element
  ) {
    _elements[identifier] = element;
  }

  public void registerParent (@NonNegative final int identifier, @NonNull final int parent) {
    _parents[identifier] = parent;
  }
}
