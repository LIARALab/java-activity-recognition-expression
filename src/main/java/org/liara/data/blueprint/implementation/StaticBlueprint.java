package org.liara.data.blueprint.implementation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.Blueprint;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.support.view.View;

/**
 * A static immutable blueprint implementation.
 */
public class StaticBlueprint implements Blueprint {

  @NonNull
  private final StaticBlueprintContent _content;

  @NonNull
  private final View<@NonNull BlueprintElement> _elements;

  /**
   * Create a new static blueprint with a given content.
   *
   * @param content Content of the blueprint to instantiate.
   */
  public StaticBlueprint(@NonNull final StaticBlueprintContent content) {
    _content = content;
    _elements = View.readonly(BlueprintElement.class, _content.getElements());
  }

  /**
   * @see Blueprint#getElements()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElement> getElements() {
    return _elements;
  }

  /**
   * @see Blueprint#getParentOf(BlueprintElement)
   */
  @Override
  public @Nullable BlueprintElement getParentOf(@NonNull final BlueprintElement element) {
    return _content.getParents().get(element.getIdentifier());
  }

  /**
   * @see Blueprint#getRootElement()
   */
  @Override
  public @NonNull BlueprintElement getRootElement() {
    return _content.getRoot();
  }
}
