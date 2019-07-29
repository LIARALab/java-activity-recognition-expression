package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.implementation.StaticColumn;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class ChainedStaticColumnBuilder<Type, Parent> implements ColumnBuilder<Type>
{
  @NonNull
  private final Parent _parent;

  @NonNull
  private final StaticColumnBuilder _builder;

  public ChainedStaticColumnBuilder (@NonNull final Parent parent) {
    _parent = parent;
    _builder = new StaticColumnBuilder();
  }

  public @NonNull Parent ofType (@Nullable final Primitive<Type> type) {
    setType(type);
    return _parent;
  }

  @Override
  public @NonNull StaticColumn<Type> build (@NonNull final GraphBuildingContext context) {
    return new StaticColumn<>(context, this);
  }

  @Override
  public @Nullable Primitive<Type> getType () {
    return _builder.getType();
  }

  @Override
  public void setType (@Nullable final Primitive<Type> type) {
    _builder.setType(type);
  }
}
