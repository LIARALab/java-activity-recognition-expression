package org.liara.data.mapping.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.relationship.OneToManyRelationship;
import org.liara.data.mapping.relationship.RelationshipManager;

public class OneToManyStaticFunctionalRelationship extends StaticFunctionalRelationship
implements OneToManyRelationship {
  public OneToManyStaticFunctionalRelationship(
      @NonNull final RelationshipManager manager,
      @NonNegative final int identifier,
      @NonNull final StaticFunctionalRelationshipBuilder builder) {
    super(manager, identifier, builder);
  }
}
