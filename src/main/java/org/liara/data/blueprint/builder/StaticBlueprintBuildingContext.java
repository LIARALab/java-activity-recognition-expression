package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.Blueprint;

import java.util.HashMap;
import java.util.Map;

public class StaticBlueprintBuildingContext implements BlueprintBuildingContext
{
  @NonNull
  private final Map<@NonNull BlueprintElementBuilder, @NonNegative @NonNull Integer> _identifiers;

  @Nullable
  private Blueprint _blueprint;

  public StaticBlueprintBuildingContext () {
    _blueprint = null;
    _identifiers = new HashMap<>();
  }

  public void setIdentifier (
    @NonNull final BlueprintElementBuilder builder,
    @NonNegative final int identifier
  ) {
    _identifiers.put(builder, identifier);
  }

  @Override
  public @NonNegative int getIdentifier (@NonNull final BlueprintElementBuilder builder) {
    return _identifiers.get(builder);
  }

  public void clear () {
    _identifiers.clear();
    _blueprint = null;
  }

  @Override
  public @Nullable Blueprint getBlueprint () {
    return _blueprint;
  }

  public void setBlueprint (@Nullable final Blueprint blueprint) {
    _blueprint = blueprint;
  }
}
