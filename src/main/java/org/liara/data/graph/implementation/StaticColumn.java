package org.liara.data.graph.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Column;
import org.liara.data.graph.Graph;
import org.liara.data.graph.GraphElement;
import org.liara.data.graph.Table;
import org.liara.data.graph.builder.ColumnBuilder;
import org.liara.data.graph.builder.GraphBuildingContext;
import org.liara.data.primitive.Primitive;

import java.util.Objects;

public class StaticColumn extends StaticGraphElement implements Column
{
  @NonNegative
  private final int _tableIdentifier;

  @NonNull
  private final Primitive<?> _type;

  public StaticColumn (
    @NonNull final GraphBuildingContext context,
    @NonNull final ColumnBuilder builder
  ) {
    super(context, builder);
    _tableIdentifier = context.getTableIdentifier(builder);
    _type = Objects.requireNonNull(builder.getType());
  }

  /**
   * @see Column#getTable()
   */
  @Override
  public @NonNull Table getTable () {
    return getGraph().getTables().get(_tableIdentifier);
  }

  /**
   * @see Column#getName()
   */
  @Override
  public @NonNull String getName () {
    return getTable().getNameOf(this);
  }

  /**
   * @see Column#getType()
   */
  @Override
  public @NonNull Primitive<?> getType () {
    return _type;
  }
}
