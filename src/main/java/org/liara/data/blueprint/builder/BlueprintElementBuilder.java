package org.liara.data.blueprint.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

/**
 * An object that is able to build blueprint elements.
 */
public interface BlueprintElementBuilder extends TreeElement {

  /**
   * Build an instance of blueprint element in accordance with this builder state.
   *
   * @param context Contextual information to use for building the blueprint element.
   * @return A blueprint element instance.
   */
  @NonNull BlueprintElement build(@NonNull final BlueprintBuildingContext context);

  /**
   * @return A view over each child builder of this builder.
   */
  @NonNull View<@NonNull ? extends BlueprintElementBuilder> getChildren();
}
