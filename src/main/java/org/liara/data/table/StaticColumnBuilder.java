package org.liara.data.table;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.table.descriptor.ColumnDescriptor;

public class StaticColumnBuilder {
  @Nullable
  private DataTable _dataTable;

  @NonNegative
  private int _identifier;

  @Nullable
  private ColumnDescriptor _descriptor;

  public StaticColumnBuilder () {
    _dataTable = null;
    _identifier = 0;
    _descriptor = null;
  }

  public @NonNull StaticColumn build () {
    return new StaticColumn(this);
  }

  public @Nullable DataTable getDataTable () {
    return _dataTable;
  }

  public void setDataTable (final DataTable dataTable) {
    _dataTable = dataTable;
  }

  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  public void setIdentifier (@NonNegative final int identifier) {
    _identifier = identifier;
  }

  public @Nullable ColumnDescriptor getDescriptor () {
    return _descriptor;
  }

  public void setDescriptor (final ColumnDescriptor descriptor) {
    _descriptor = descriptor;
  }

  public void clear () {
    _descriptor = null;
    _identifier = 0;
    _dataTable = null;
  }
}
