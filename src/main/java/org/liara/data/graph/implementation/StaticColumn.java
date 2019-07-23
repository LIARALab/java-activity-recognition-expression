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

public class StaticColumn implements Column
{
  @NonNull
  private final Graph _graph;

  @NonNegative
  private final int _identifier;

  @NonNegative
  private final int _tableIdentifier;

  @NonNull
  private final String _name;

  @NonNull
  private final Primitive<?> _type;

  public StaticColumn (
    @NonNull final GraphBuildingContext context,
    @NonNull final ColumnBuilder builder
  ) {
    _graph = Objects.requireNonNull(context.getGraph());
    _identifier = context.getIdentifier(builder);
    _tableIdentifier = context.getTableIdentifier(builder);

    _name = Objects.requireNonNull(builder.getName());
    _type = Objects.requireNonNull(builder.getType());
  }

  /**
   * @see GraphElement#getIdentifier()
   */
  @Override
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  /**
   * @see GraphElement#getGraph()
   */
  @Override
  public @NonNull Graph getGraph () {
    return _graph;
  }

  /**
   * @see Column#getTable()
   */
  @Override
  public @NonNull Table getTable () {
    return _graph.getTables().get(_tableIdentifier);
  }

  /**
   * @see Column#getName()
   */
  @Override
  public @NonNull String getName () {
    return _name;
  }

  /**
   * @see Column#getType()
   */
  @Override
  public @NonNull Primitive<?> getType () {
    return _type;
  }
}
