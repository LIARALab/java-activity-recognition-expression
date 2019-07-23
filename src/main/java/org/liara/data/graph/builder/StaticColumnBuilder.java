package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Column;
import org.liara.data.graph.implementation.StaticColumn;
import org.liara.data.primitive.Primitive;

public class StaticColumnBuilder implements ColumnBuilder
{
  @Nullable
  private Primitive<?> _type;

  public StaticColumnBuilder () {
    _type = null;
  }

  public StaticColumnBuilder (@NonNull final StaticColumnBuilder toCopy) {
    _type = toCopy.getType();
  }

  @Override
  public @NonNull StaticColumn build (@NonNull final GraphBuildingContext context) {
    return new StaticColumn(context, this);
  }

  @Override
  public @Nullable Primitive<?> getType () {
    return _type;
  }

  @Override
  public void setType (@Nullable final Primitive<?> type) {
    _type = type;
  }
}
