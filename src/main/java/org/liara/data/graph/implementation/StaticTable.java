package org.liara.data.graph.implementation;

import java.util.Arrays;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Column;
import org.liara.data.graph.Table;
import org.liara.data.graph.builder.GraphBuildingContext;
import org.liara.data.graph.builder.TableBuilder;
import org.liara.support.BaseComparators;
import org.liara.support.ListIndex;
import org.liara.support.view.View;

public class StaticTable extends StaticGraphElement
    implements Table {

  @NonNull
  private final ListIndex<@NonNull String, @NonNull Integer> _index;

  @NonNull
  private final View<@NonNull Column> _columns;

  @NonNull
  private final View<@NonNull String> _columnNames;

  private final int _offset;

  private final int[] _columnFields;

  public StaticTable(
      @NonNull final GraphBuildingContext context,
      @NonNull final TableBuilder builder
  ) {
    super(context, builder);
    _index = new ListIndex<>(builder.getColumns().getSize(), BaseComparators.STRING_COMPARATOR);

    fillIndex(context, builder);

    _columns = View.readonly(Integer.class, _index.getValues()).map(
        Column.class, getGraph().getColumns()::get
    );
    _columnNames = View.readonly(String.class, _index.getKeys());

    int[] boundaries = computeChildrenBoundaries();

    _columnFields = new int[boundaries[1] - boundaries[0] + 1];
    _offset = boundaries[0];

    Arrays.fill(_columnFields, -1);

    for (int index = 0; index < _index.getSize(); ++index) {
      _columnFields[_index.getValue(index) - _offset] = index;
    }
  }

  private void fillIndex(
      @NonNull final GraphBuildingContext context,
      @NonNull final TableBuilder builder
  ) {
    for (int index = 0, size = builder.getColumns().getSize(); index < size; ++index) {
      _index.put(
          builder.getColumnNames().get(index),
          context.getIdentifier(builder.getColumns().get(index))
      );
    }
  }

  private @NonNegative int[] computeChildrenBoundaries() {
    if (_index.getSize() == 0) {
      return new int[]{0, 0};
    }

    @NonNegative final int[] result = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

    for (@NonNegative int index = 0, size = _index.getSize(); index < size; ++index) {
      result[0] = Math.min(result[0], _index.getValue(index));
      result[1] = Math.max(result[1], _index.getValue(index));
    }

    return result;
  }

  @Override
  public @NonNull View<@NonNull Column> getColumns() {
    return _columns;
  }

  @Override
  public @NonNull View<@NonNull String> getColumnNames() {
    return _columnNames;
  }

  @Override
  public @NonNull String getNameOf(@NonNull final Column column) {
    return _columnNames.get(getIndexOf(column));
  }

  @Override
  public int getIndexOf(@NonNull final Column column) {
    if (column.getTable() == this) {
      return _columnFields[column.getIdentifier() - _offset];
    } else {
      return -1;
    }
  }

  @Override
  public @NonNull Column getColumn(@NonNull final String name) {
    return getGraph().getColumns().get(_index.getValue(name));
  }

  @Override
  public boolean hasColumn(@NonNull final String name) {
    return _index.containsKey(name);
  }

  @Override
  public @NonNull String getName() {
    return getGraph().getNameOf(this);
  }
}
