package org.liara.data.mapping.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.relationship.Relationship;
import org.liara.data.mapping.relationship.RelationshipManager;

public class OneToManyStaticFunctionalRelationshipBuilder extends StaticFunctionalRelationshipBuilder {
  @Override
  public @NonNull Relationship build(final @NonNull RelationshipManager parent,
      @NonNegative final int identifier) {
    return new OneToManyStaticFunctionalRelationship(parent, identifier, this);
  }
}
