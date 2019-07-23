package org.liara.data.blueprint.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.Blueprint;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.support.view.View;

public class UndefinedBlueprintElement implements BlueprintElement
{
  @NonNull
  public static final BlueprintElement INSTANCE = new UndefinedBlueprintElement();

  private UndefinedBlueprintElement () {

  }

  @Override
  public @NonNegative int getIdentifier () {
    throw new IllegalStateException(getErrorMessage());
  }

  @Override
  public @NonNull Blueprint getBlueprint () {
    throw new IllegalStateException(getErrorMessage());
  }

  @Override
  public @NonNull View<@NonNull BlueprintElement> getChildren () {
    throw new IllegalStateException(getErrorMessage());
  }

  private @NonNull String getErrorMessage () {
    return (
      "Unable to get the identifier of this blueprint element instance because this " +
      "blueprint element instance was not defined. This error may occur if you trying to " +
      "manipulate a blueprint instance during its creation or if the blueprint instance was " +
      "malformed."
    );
  }
}
