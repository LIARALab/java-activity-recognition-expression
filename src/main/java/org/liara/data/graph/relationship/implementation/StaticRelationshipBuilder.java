package org.liara.data.graph.relationship.implementation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.relationship.Relationship;
import org.liara.data.graph.relationship.RelationshipManager;

public interface StaticRelationshipBuilder {
  /**
   * Build a relationship for the given manager and with the given identifier.
   *
   * @param parent The parent manager of the relationship to build.
   * @param identifier The identifier of the relationship to build.
   *
   * @return A new relationship for the given manager and with the given identifier.
   */
  @NonNull Relationship build (
      @NonNull final RelationshipManager parent,
      @NonNegative final int identifier
  );
}
