package org.liara.data.blueprint;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public interface ObjectBlueprint
  extends BlueprintElement
{
  @NonNull View<@NonNull String> getKeys();

  int getFieldOf (@NonNull final BlueprintElement value);

  int getFieldOf (@NonNull final String key);
}
