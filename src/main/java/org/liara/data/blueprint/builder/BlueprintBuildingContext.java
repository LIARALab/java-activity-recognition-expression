package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.Blueprint;

/**
 * An object that store contextual information used for building blueprint elements.
 */
public interface BlueprintBuildingContext {

  /**
   * Return the identifier assigned to the given builder in accordance with this context.
   *
   * @param builder A builder to resolve.
   * @return The identifier assigned to the given builder in accordance with this context.
   */
  @NonNegative int getIdentifier(@NonNull final BlueprintElementBuilder builder);

  /**
   * @return The blueprint that is currently build.
   */
  @Nullable Blueprint getBlueprint();
}
