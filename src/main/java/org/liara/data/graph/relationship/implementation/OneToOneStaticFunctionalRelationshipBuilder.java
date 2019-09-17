package org.liara.data.graph.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.relationship.Relationship;
import org.liara.data.graph.relationship.RelationshipManager;

public class OneToOneStaticFunctionalRelationshipBuilder extends StaticFunctionalRelationshipBuilder {
  @Override
  public @NonNull Relationship build(final @NonNull RelationshipManager parent,
      @NonNegative final int identifier) {
    return new OneToOneStaticFunctionalRelationship(parent, identifier, this);
  }
}
