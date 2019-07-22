package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public interface ObjectModel
  extends ModelElement
{
  @NonNull View<@NonNull String> getKeys();

  int getFieldOf (@NonNull final ModelElement value);

  int getFieldOf (@NonNull final String key);
}
