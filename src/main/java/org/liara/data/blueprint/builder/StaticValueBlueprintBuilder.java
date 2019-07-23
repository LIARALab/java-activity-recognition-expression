package org.liara.data.blueprint.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.implementation.StaticValueBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class StaticValueBlueprintBuilder implements BlueprintElementBuilder
{
  @NonNull
  private static final View<@NonNull BlueprintElementBuilder> EMPTY_VIEW = View.readonly(
    BlueprintElementBuilder.class, new BlueprintElementBuilder[0]
  );

  @Nullable
  private Primitive<?> _type;

  /**
   * Instantiate a new value blueprint builder.
   */
  public StaticValueBlueprintBuilder () {
    _type = null;
  }

  @Override
  public @NonNull StaticValueBlueprint build (@NonNull final BlueprintBuildingContext context) {
    return new StaticValueBlueprint(context, this);
  }

  /**
   * @see BlueprintElementBuilder#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren () {
    return EMPTY_VIEW;
  }

  public @Nullable Primitive<?> getType () {
    return _type;
  }

  public void setType (@Nullable final Primitive<?> type) {
    _type = type;
  }
}
