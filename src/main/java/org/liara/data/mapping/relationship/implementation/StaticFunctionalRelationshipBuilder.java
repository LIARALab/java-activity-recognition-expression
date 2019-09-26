package org.liara.data.mapping.relationship.implementation;

import java.util.function.Supplier;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.mapping.Structure;
import org.liara.data.mapping.relationship.Relationship;
import org.liara.data.mapping.relationship.RelationshipManager;
import org.liara.expression.Expression;

public class StaticFunctionalRelationshipBuilder implements StaticRelationshipBuilder {
  @Nullable
  private String _name;

  @Nullable
  private Structure _originStructure;

  @Nullable
  private Structure _destinationStructure;

  @Nullable
  private Supplier<@NonNull Expression<@NonNull Boolean>> _predicateFactory;

  public StaticFunctionalRelationshipBuilder () {
    _name = null;
    _originStructure = null;
    _destinationStructure = null;
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

  public @Nullable Structure getOriginStructure() {
    return _originStructure;
  }

  public void setOriginStructure(@Nullable final Structure originStructure) {
    _originStructure = originStructure;
  }

  public @Nullable Structure getDestinationStructure() {
    return _destinationStructure;
  }

  public void setDestinationStructure(@Nullable final Structure destinationStructure) {
    _destinationStructure = destinationStructure;
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
