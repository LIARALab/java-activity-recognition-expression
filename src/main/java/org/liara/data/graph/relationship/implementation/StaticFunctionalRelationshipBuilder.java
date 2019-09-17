package org.liara.data.graph.relationship.implementation;

import java.util.function.Supplier;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.graph.Table;
import org.liara.data.graph.relationship.Relationship;
import org.liara.data.graph.relationship.RelationshipManager;
import org.liara.expression.Expression;

public class StaticFunctionalRelationshipBuilder implements StaticRelationshipBuilder {
  @Nullable
  private String _name;

  @Nullable
  private Table _originTable;

  @Nullable
  private Table _destinationTable;

  @Nullable
  private Supplier<@NonNull Expression<@NonNull Boolean>> _predicateFactory;

  public StaticFunctionalRelationshipBuilder () {
    _name = null;
    _originTable = null;
    _destinationTable = null;
    _predicateFactory = null;
  }

  @Override
  public @NonNull Relationship build(
      @NonNull final RelationshipManager parent,
      @NonNegative int identifier
  ) {
    return new StaticFunctionalRelationship(parent, identifier, this);
  }

  public @Nullable String getName() {
    return _name;
  }

  public void setName(@Nullable final String name) {
    _name = name;
  }

  public @Nullable Table getOriginTable() {
    return _originTable;
  }

  public void setOriginTable(@Nullable final Table originTable) {
    _originTable = originTable;
  }

  public @Nullable Table getDestinationTable() {
    return _destinationTable;
  }

  public void setDestinationTable(@Nullable final Table destinationTable) {
    _destinationTable = destinationTable;
  }

  public @Nullable Supplier<@NonNull Expression<@NonNull Boolean>> getPredicateFactory() {
    return _predicateFactory;
  }

  public void setPredicateFactory(
      @Nullable final Supplier<@NonNull Expression<@NonNull Boolean>>  predicateFactory
  ) {
    _predicateFactory = predicateFactory;
  }
}
