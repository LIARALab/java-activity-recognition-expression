package org.liara.data.graph.relationship.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.liara.expression.Expression;
import org.liara.support.BaseComparators;
import org.liara.support.view.View;

public class StaticRelationshipManager implements RelationshipManager {
  private class SearchedRelationship implements Relationship {
    @NonNull
    private String _name = "";

    @Override
    public @NonNull RelationshipManager getManager() {
      return null;
    }

    @Override
    public @NonNegative int getIdentifier() {
      return 0;
    }

    @Override
    public @NonNull String getName() {
      return _name;
    }

    public void setName(@NonNull final String name) {
      _name = name;
    }

    @Override
    public @NonNull Table getOriginTable() {
      return null;
    }

    @Override
    public @NonNull Table getDestinationTable() {
      return null;
    }

    @Override
    public @NonNull Expression<@NonNull Boolean> resolve() {
      return null;
    }
  }

  @NonNull
  private final SearchedRelationship _searchedRelationship = new SearchedRelationship();

  @NonNull
  private final Graph _graph;

  @NonNull
  private final View<@NonNull Relationship> _relationships;

  @NonNull
  private final List<@NonNull List<@NonNull Relationship>> _relationshipsByTable;

  @NonNull
  private final List<@NonNull View<@NonNull Relationship>> _relationshipsByTableViews;

  public StaticRelationshipManager (@NonNull final StaticRelationshipManagerBuilder builder) {
    _graph = Objects.requireNonNull(builder.getGraph());
    _relationships = buildRelationshipsViewFromBuilder(builder);
    _relationshipsByTable = buildRelationshipsByTableFromBuilder(builder);
    _relationshipsByTableViews = _relationshipsByTable.stream().map(
        (@NonNull final List<@NonNull Relationship> relationships) -> View.readonly(
            Relationship.class, relationships
        )
    ).collect(Collectors.toList());
  }

  private @NonNull List<@NonNull List<@NonNull Relationship>> buildRelationshipsByTableFromBuilder(
      @NonNull final StaticRelationshipManagerBuilder builder
  ) {
    @NonNull final List<@NonNull List<@NonNull Relationship>> relationshipsByTable = (
        new ArrayList<>(_graph.getTables().getSize())
    );

    for (int index = 0, size = _graph.getTables().getSize(); index < size; ++index) {
      relationshipsByTable.add(new ArrayList<>(8));
    }

    for (int index = 0, size = _relationships.getSize(); index < size; ++index) {
      @NonNull final Relationship relationship = _relationships.get(index);

      relationshipsByTable.get(relationship.getOriginTable().getIdentifier()).add(relationship);
    }

    for (int index = 0, size = _graph.getTables().getSize(); index < size; ++index) {
      relationshipsByTable.get(index).sort(
          Comparator.comparing(Relationship::getName, BaseComparators.STRING_COMPARATOR)
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
    _searchedRelationship.setName(name);

    final int index = Collections.binarySearch(
        _relationshipsByTable.get(table.getIdentifier()),
        _searchedRelationship,
        Comparator.comparing(Relationship::getName, BaseComparators.STRING_COMPARATOR)
    );

    if (index < 0) {
      throw new NoSuchElementException(
          "No relationship " + name + "@" + table + " found into this relationship manager."
      );
    }

    return _relationshipsByTable.get(table.getIdentifier()).get(index);
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
    _searchedRelationship.setName(name);

    return Collections.binarySearch(
        _relationshipsByTable.get(table.getIdentifier()),
        _searchedRelationship,
        Comparator.comparing(Relationship::getName, BaseComparators.STRING_COMPARATOR)
    ) >= 0;
  }
}
