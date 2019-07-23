package org.liara.data.blueprint.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.implementation.StaticNullBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class StaticNullBlueprintBuilder implements BlueprintElementBuilder
{
  @NonNull
  private static final View<@NonNull BlueprintElementBuilder> EMPTY_VIEW = View.readonly(
    BlueprintElementBuilder.class, new BlueprintElementBuilder[0]
  );

  /**
   * @see BlueprintElementBuilder#build(BlueprintBuildingContext)
   */
  @Override
  public @NonNull StaticNullBlueprint build (@NonNull final BlueprintBuildingContext context) {
    return new StaticNullBlueprint(context, this);
  }

  /**
   * @see BlueprintElementBuilder#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren () {
    return EMPTY_VIEW;
  }
}
