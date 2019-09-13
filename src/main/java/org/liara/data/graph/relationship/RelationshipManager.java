package org.liara.data.graph.relationship;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Graph;
import org.liara.data.graph.Table;
import org.liara.support.view.View;

public interface RelationshipManager {
  /**
   * @return The parent graph of this manager.
   */
  @NonNull Graph getGraph();

  /**
   * @return A view over all registered relationships.
   */
  @NonNull View<@NonNull Relationship> getRelationships();

  /**
   * Return all registered relationships of the given table.
   *
   * @param table Origin table of the relationships to return.
   *
   * @return All registered relationships of the given table.
   */
  @NonNull View<@NonNull Relationship> getRelationshipsOfTable (@NonNull final Table table);

  /**
   * Return a relationship of the given table.
   *
   * @param table Origin table of the relationship.
   * @param name Name of the relationship to get.
   *
   * @return All registered relationships of the given table.
   */
  @NonNull Relationship getRelationship (@NonNull final Table table, @NonNull final String name);

  /**
   * Return a relationship of this manager.
   *
   * @param identifier Identifier of the relationship to return.
   *
   * @return The requested relationship.
   */
  @NonNull Relationship getRelationship (@NonNegative final int identifier);

  /**
   * Return true if a relationship with the given identifier exists.
   *
   * @param identifier Identifier of the relationship to get.
   *
   * @return True if a relationship with the given identifier exists.
   */
  boolean hasRelationship (@NonNegative final int identifier);

  /**
   * Return true if a relationship from the given table and with the given name exists.
   *
   * @param table Origin table of the relationship.
   * @param name Name of the relationship to get.
   *
   * @return True if a relationship from the given table and with the given name exists.
   */
  boolean hasRelationship (@NonNull final Table table, @NonNull final String name);
}
