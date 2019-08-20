package org.liara.data.blueprint.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.implementation.StaticValueBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class StaticValueBlueprintBuilder implements BlueprintElementBuilder {

  @NonNull
  private static final View<@NonNull BlueprintElementBuilder> EMPTY_VIEW = View.readonly(
      BlueprintElementBuilder.class, new BlueprintElementBuilder[0]
  );

  @Nullable
  private Primitive<?> _type;

  /**
   * Instantiate a new value blueprint builder.
   */
  public StaticValueBlueprintBuilder() {
    _type = null;
  }

  /**
   * @see BlueprintElementBuilder#build(BlueprintBuildingContext)
   */
  @Override
  public @NonNull StaticValueBlueprint build(@NonNull final BlueprintBuildingContext context) {
    return new StaticValueBlueprint(context, this);
  }

  /**
   * @see BlueprintElementBuilder#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren() {
    return EMPTY_VIEW;
  }

  /**
   * @return The type of value expected by the resulting blueprint.
   */
  public @Nullable Primitive<?> getType() {
    return _type;
  }

  /**
   * Update the type of value expected by the resulting blueprint.
   *
   * @param type The new type of value expected by the resulting blueprint.
   */
  public void setType(@Nullable final Primitive<?> type) {
    _type = type;
  }
}
