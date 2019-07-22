package org.liara.data.table;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

import java.util.Objects;

public class StaticColumn implements Column
{
  @NonNegative
  private final int _identifier;

  @NonNull
  private final DataTable _dataTable;

  @NonNull
  private final String _name;

  @NonNull
  private final Primitive<?> _type;

  public StaticColumn (@NonNull final StaticColumnBuilder context) {
    Objects.requireNonNull(context.getDescriptor());
    Objects.requireNonNull(context.getDataTable());

    _dataTable = context.getDataTable();
    _identifier = context.getIdentifier();
    _name = Objects.requireNonNull(context.getDescriptor().getName());
    _type = Objects.requireNonNull(context.getDescriptor().getType());
  }

  @Override
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  @Override
  public @NonNull DataTable getDataTable () {
    return _dataTable;
  }

  @Override
  public @NonNull String getName () {
    return _name;
  }

  @Override
  public @NonNull Primitive<?> getType () {
    return _type;
  }
}
