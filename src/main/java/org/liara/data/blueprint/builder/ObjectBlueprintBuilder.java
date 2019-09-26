package org.liara.data.blueprint.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public interface ObjectBlueprintBuilder
    extends BlueprintElementBuilder {

  /**
   * @return A view over each keys of the object to build in their apparition order.
   */
  @NonNull View<@NonNull ? extends String> getKeys();
}
