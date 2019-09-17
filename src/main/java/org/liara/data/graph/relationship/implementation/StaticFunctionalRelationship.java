package org.liara.data.graph.relationship.implementation;

import java.util.Objects;
import java.util.function.Supplier;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Table;
import org.liara.data.graph.relationship.Relationship;
import org.liara.data.graph.relationship.RelationshipManager;
import org.liara.expression.Expression;

public class StaticFunctionalRelationship implements Relationship {
  @NonNull
  private final String _name;

  @NonNegative
  private final int _identifier;

  @NonNull
  private final RelationshipManager _manager;

  @NonNull
  private final Table _originTable;

  @NonNull
  private final Table _destinationTable;

  @NonNull
  private final Supplier<@NonNull Expression<@NonNull Boolean>> _predicateFactory;

  public StaticFunctionalRelationship(
      @NonNull final RelationshipManager manager,
      @NonNegative final int identifier,
      @NonNull final StaticFunctionalRelationshipBuilder builder
  ) {
    _manager = manager;
    _identifier = identifier;
    _name = Objects.requireNonNull(builder.getName());
    _predicateFactory = Objects.requireNonNull(builder.getPredicateFactory());
    _originTable = Objects.requireNonNull(builder.getOriginTable());
    _destinationTable = Objects.requireNonNull(builder.getDestinationTable());
  }

  @Override
  public @NonNull RelationshipManager getManager() {
    return _manager;
  }

  @Override
  public @NonNegative int getIdentifier() {
    return _identifier;
  }

  @Override
  public @NonNull String getName() {
    return _name;
  }

  @Override
  public @NonNull Table getOriginTable() {
    return _originTable;
  }

  @Override
  public @NonNull Table getDestinationTable() {
    return _destinationTable;
  }

  @Override
  public @NonNull Expression<@NonNull Boolean> resolve() {
    return _predicateFactory.get();
  }
}
