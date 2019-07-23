package org.liara.data.graph.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Column;
import org.liara.data.primitive.Primitive;

public class StaticColumnBuilder implements ColumnBuilder
{
  @Nullable
  private String _name;

  @Nullable
  private Primitive<?> _type;

  public StaticColumnBuilder () {
    _name = null;
    _type = null;
  }

  public StaticColumnBuilder (@NonNull final StaticColumnBuilder toCopy) {
    _name = toCopy.getName();
    _type = toCopy.getType();
  }

  @Override
  public @NonNull Column build (final @NonNull GraphBuildingContext context) {
    return null;
  }

  @Override
  public @Nullable String getName () {
    return _name;
  }

  public void setName (@Nullable final String name) {
    _name = name;
  }

  @Override
  public @Nullable Primitive<?> getType () {
    return _type;
  }

  public void setType (@Nullable final Primitive<?> type) {
    _type = type;
  }
}
