package org.liara.data.table.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;

public class StaticColumnDescriptor implements ColumnDescriptor
{
  @Nullable
  private String _name;

  @Nullable
  private Primitive<?> _type;

  public StaticColumnDescriptor () {
    _name = null;
    _type = null;
  }

  public StaticColumnDescriptor (@NonNull final StaticColumnDescriptor toCopy) {
    _name = toCopy.getName();
    _type = toCopy.getType();
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
