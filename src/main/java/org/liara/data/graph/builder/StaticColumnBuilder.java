package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.implementation.StaticColumn;
import org.liara.data.primitive.Primitive;

public class StaticColumnBuilder<Type> implements ColumnBuilder<Type>
{
  @Nullable
  private Primitive<Type> _type;

  public StaticColumnBuilder () {
    _type = null;
  }

  public StaticColumnBuilder (@NonNull final StaticColumnBuilder<Type> toCopy) {
    _type = toCopy.getType();
  }

  @Override
  public @NonNull StaticColumn<Type> build (@NonNull final GraphBuildingContext context) {
    return new StaticColumn<>(context, this);
  }

  @Override
  public @Nullable Primitive<Type> getType () {
    return _type;
  }

  @Override
  public void setType (@Nullable final Primitive<Type> type) {
    _type = type;
  }
}
