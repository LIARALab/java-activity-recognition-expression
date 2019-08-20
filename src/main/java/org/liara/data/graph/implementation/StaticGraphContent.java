package org.liara.data.graph.implementation;

import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Column;
import org.liara.data.graph.Table;
import org.liara.support.BaseComparators;
import org.liara.support.ListIndex;

public class StaticGraphContent {

  @NonNull
  private final ListIndex<@NonNull String, @NonNull Table> _tables;

  @NonNull
  private final List<@NonNull Column> _columns;

  @Nullable
  private String _name;

  public StaticGraphContent() {
    this(16, 8 * 8);
  }

  public StaticGraphContent(
      @NonNegative final int tableCapacity,
      @NonNegative final int columnCapacity
  ) {
    _tables = new ListIndex<>(tableCapacity, BaseComparators.STRING_COMPARATOR);
    _columns = new ArrayList<>(columnCapacity);
    _name = null;
  }

  public @NonNull ListIndex<@NonNull String, @NonNull Table> getTables() {
    return _tables;
  }

  public @NonNull List<@NonNull Column> getColumns() {
    return _columns;
  }

  public @Nullable String getName() {
    return _name;
  }

  public void setName(@Nullable final String name) {
    _name = name;
  }
}
