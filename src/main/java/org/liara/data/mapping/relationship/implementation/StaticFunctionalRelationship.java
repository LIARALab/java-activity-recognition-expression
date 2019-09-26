package org.liara.data.mapping.relationship.implementation;

import java.util.Objects;
import java.util.function.Supplier;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Structure;
import org.liara.data.mapping.relationship.Relationship;
import org.liara.data.mapping.relationship.RelationshipManager;
import org.liara.expression.Expression;

public class StaticFunctionalRelationship implements Relationship {
  @NonNull
  private final String _name;

  @NonNegative
  private final int _identifier;

  @NonNull
  private final RelationshipManager _manager;

  @NonNull
  private final Structure _originStructure;

  @NonNull
  private final Structure _destinationStructure;

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
    _originStructure = Objects.requireNonNull(builder.getOriginStructure());
    _destinationStructure = Objects.requireNonNull(builder.getDestinationStructure());
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
  public @NonNull Structure getOriginStructure() {
    return _originStructure;
  }

  @Override
  public @NonNull Structure getDestinationStructure() {
    return _destinationStructure;
  }

  @Override
  public @NonNull Expression<@NonNull Boolean> resolve() {
    return _predicateFactory.get();
  }
}
