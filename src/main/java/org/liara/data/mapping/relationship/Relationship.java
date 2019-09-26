package org.liara.data.mapping.relationship;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.expression.Expression;

public interface Relationship {
  /**
   * @return The parent manager of this relationship.
   */
  @NonNull RelationshipManager getManager();

  /**
   * @return The graph of this relationship.
   */
  default @NonNull Mapping getGraph() {
    return getManager().getMapping();
  }

  /**
   * @return The local identifier of this relationship.
   */
  @NonNegative int getIdentifier();

  /**
   * @return The name of this relationship.
   */
  @NonNull String getName();

  /**
   * @return The table from which this relationship begin.
   */
  @NonNull Structure getOriginStructure();

  /**
   * @return The table from which this relationship end.
   */
  @NonNull Structure getDestinationStructure();

  /**
   * @return A predicate that allows to filter the instances of the destination table in accordance with this relationship.
   */
  @NonNull Expression<@NonNull Boolean> resolve();
}
