package org.liara.data.blueprint.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.Blueprint;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.builder.BlueprintBuildingContext;
import org.liara.data.blueprint.builder.BlueprintElementBuilder;
import org.liara.support.view.View;

import java.util.Objects;

public class StaticBlueprintElement
  implements BlueprintElement
{
  @NonNull
  private static final View<@NonNull BlueprintElement> EMPTY_VIEW = View.readonly(
    BlueprintElement.class, new BlueprintElement[0]
  );

  @NonNull
  private final Blueprint _blueprint;

  @NonNegative
  private final int _identifier;

  /**
   * Create a new blueprint element from a given builder and a given context.
   *
   * @param context A context to use for instantiating blueprint element.
   * @param builder A builder to use for instantiating blueprint element.
   */
  public StaticBlueprintElement (
    @NonNull final BlueprintBuildingContext context,
    @NonNull final BlueprintElementBuilder builder
  ) {
    _blueprint = Objects.requireNonNull(context.getBlueprint());
    _identifier = context.getIdentifier(builder);
  }

  /**
   * @see BlueprintElement#getIdentifier()
   */
  @Override
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  /**
   * @see BlueprintElement#getBlueprint()
   */
  @Override
  public @NonNull Blueprint getBlueprint () {
    return _blueprint;
  }

  /**
   * @see BlueprintElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElement> getChildren () {
    return EMPTY_VIEW;
  }
}
