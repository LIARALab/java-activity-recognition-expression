package org.liara.data.graph.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.relationship.ManyToManyRelationship;
import org.liara.data.graph.relationship.OneToOneRelationship;
import org.liara.data.graph.relationship.RelationshipManager;

public class ManyToManyStaticFunctionalRelationship extends StaticFunctionalRelationship
implements ManyToManyRelationship {
  public ManyToManyStaticFunctionalRelationship(
      @NonNull final RelationshipManager manager,
      @NonNegative final int identifier,
      @NonNull final StaticFunctionalRelationshipBuilder builder) {
    super(manager, identifier, builder);
  }
}
