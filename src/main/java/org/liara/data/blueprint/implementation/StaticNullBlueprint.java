package org.liara.data.blueprint.implementation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.NullBlueprint;
import org.liara.data.blueprint.builder.BlueprintBuildingContext;
import org.liara.data.blueprint.builder.BlueprintElementBuilder;

public class StaticNullBlueprint
    extends StaticBlueprintElement
    implements NullBlueprint {

  /**
   * Create a new blueprint element from a given builder and a given context.
   *
   * @param context A context to use for instantiating blueprint element.
   * @param builder A builder to use for instantiating blueprint element.
   */
  public StaticNullBlueprint(
      @NonNull final BlueprintBuildingContext context,
      @NonNull final BlueprintElementBuilder builder
  ) {
    super(context, builder);
  }
}
