package org.liara.data.graph.relationship.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.graph.Graph;
import org.liara.data.graph.Table;
import org.liara.data.graph.relationship.Relationship;
import org.liara.data.graph.relationship.RelationshipManager;
import org.liara.support.BaseComparators;
import org.liara.support.Index;
import org.liara.support.ListIndex;
import org.liara.support.view.View;

public class StaticRelationshipManager implements RelationshipManager {
  @NonNull
  private final Graph _graph;

  @NonNull
  private final View<@NonNull Relationship> _relationships;

  @NonNull
  private final List<? extends @NonNull Index<@NonNull String, @NonNull Relationship>> _relationshipsByTable;

  @NonNull
  private final List<@NonNull View<@NonNull Relationship>> _relationshipsByTableViews;

  public StaticRelationshipManager (@NonNull final StaticRelationshipManagerBuilder builder) {
    _graph = Objects.requireNonNull(builder.getGraph());
    _relationships = buildRelationshipsViewFromBuilder(builder);
    _relationshipsByTable = buildRelationshipsByTableFromBuilder(builder);
    _relationshipsByTableViews = _relationshipsByTable.stream().map(
        (@NonNull final Index<@NonNull String, @NonNull Relationship> index) -> View.readonly(
            Relationship.class, index.getValues()
        )
    ).collect(Collectors.toList());
  }

  private @NonNull List<? extends @NonNull Index<@NonNull String, @NonNull Relationship>> buildRelationshipsByTableFromBuilder(
      @NonNull final StaticRelationshipManagerBuilder builder
  ) {
    @NonNull final List<@NonNull ListIndex<@NonNull String, @NonNull Relationship>> relationshipsByTable = (
        new ArrayList<>(_graph.getTables().getSize())
    );

    for (int index = 0, size = _graph.getTables().getSize(); index < size; ++index) {
      relationshipsByTable.add(new ListIndex<>(8, BaseComparators.STRING_COMPARATOR));
    }

    for (int index = 0, size = _relationships.getSize(); index < size; ++index) {
      @NonNull final Relationship relationship = _relationships.get(index);

      relationshipsByTable.get(relationship.getOriginTable().getIdentifier()).put(
          relationship.getName(), relationship
      );
    }

    return relationshipsByTable;
  }

  private @NonNull View<@NonNull Relationship> buildRelationshipsViewFromBuilder(
      @NonNull final StaticRelationshipManagerBuilder builder
  ) {
    @NonNull final List<@NonNull StaticRelationshipBuilder> relationshipBuilders = (
        builder.getRelationships()
    );

    @NonNull final List<@NonNull Relationship> relationships = new ArrayList<>(
        relationshipBuilders.size()
    );

    for (@NonNegative int index = 0, size = relationshipBuilders.size(); index < size; ++index) {
      relationships.add(relationshipBuilders.get(index).build(this, index));
    }

    return View.readonly(Relationship.class, relationships);
  }


  @Override
  public @NonNull Graph getGraph() {
    return _graph;
  }

  @Override
  public @NonNull View<@NonNull Relationship> getRelationships() {
    return _relationships;
  }

  @Override
  public @NonNull View<@NonNull Relationship> getRelationshipsOfTable(@NonNull final Table table) {
    return _relationshipsByTableViews.get(table.getIdentifier());
  }

  @Override
  public @NonNull Relationship getRelationship(
      @NonNull final Table table,
      @NonNull final String name
  ) {
    if (hasRelationship(table, name)) {
      return _relationshipsByTable.get(table.getIdentifier()).getValue(name);
    } else {
      throw new NoSuchElementException(
          "No relationship " + name + "@" + table + " found into this relationship manager."
      );
    }
  }

  @Override
  public @NonNull Relationship getRelationship(@NonNegative final int identifier) {
    return _relationships.get(identifier);
  }

  @Override
  public boolean hasRelationship(@NonNegative final int identifier) {
    return identifier < _relationships.getSize();
  }

  @Override
  public boolean hasRelationship(@NonNull final Table table, @NonNull final String name) {
    return _relationshipsByTable.get(table.getIdentifier()).containsKey(name);
  }
}