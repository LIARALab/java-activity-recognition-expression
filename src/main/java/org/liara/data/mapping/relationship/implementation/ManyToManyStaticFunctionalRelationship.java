package org.liara.data.mapping.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.relationship.ManyToManyRelationship;
import org.liara.data.mapping.relationship.RelationshipManager;

public class ManyToManyStaticFunctionalRelationship extends StaticFunctionalRelationship
implements ManyToManyRelationship {
  public ManyToManyStaticFunctionalRelationship(
      @NonNull final RelationshipManager manager,
      @NonNegative final int identifier,
      @NonNull final StaticFunctionalRelationshipBuilder builder) {
    super(manager, identifier, builder);
  }
}
