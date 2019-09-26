package org.liara.data.mapping.relationship.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.data.mapping.relationship.Relationship;
import org.liara.data.mapping.relationship.RelationshipManager;
import org.liara.support.BaseComparators;
import org.liara.support.index.Index;
import org.liara.support.index.ListIndex;
import org.liara.support.view.View;

public class StaticRelationshipManager implements RelationshipManager {
  @NonNull
  private final Mapping _mapping;

  @NonNull
  private final View<@NonNull Relationship> _relationships;

  @NonNull
  private final List<? extends @NonNull Index<@NonNull String, @NonNull Relationship>> _relationshipsByTable;

  @NonNull
  private final List<@NonNull View<@NonNull Relationship>> _relationshipsByTableViews;

  public StaticRelationshipManager (@NonNull final StaticRelationshipManagerBuilder builder) {
    _mapping = Objects.requireNonNull(builder.getMapping());
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
        new ArrayList<>(_mapping.getTables().getSize())
    );

    for (int index = 0, size = _mapping.getTables().getSize(); index < size; ++index) {
      relationshipsByTable.add(new ListIndex<>(8, BaseComparators.STRING_COMPARATOR));
    }

    for (int index = 0, size = _relationships.getSize(); index < size; ++index) {
      @NonNull final Relationship relationship = _relationships.get(index);

      relationshipsByTable.get(relationship.getOriginStructure().getIdentifier()).put(
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
  public @NonNull Mapping getMapping() {
    return _mapping;
  }

  @Override
  public @NonNull View<@NonNull Relationship> getRelationships() {
    return _relationships;
  }

  @Override
  public @NonNull View<@NonNull Relationship> getRelationshipsOfTable(@NonNull final Structure structure) {
    return _relationshipsByTableViews.get(structure.getIdentifier());
  }

  @Override
  public @NonNull Relationship getRelationship(
      @NonNull final Structure structure,
      @NonNull final String name
  ) {
    if (hasRelationship(structure, name)) {
      return _relationshipsByTable.get(structure.getIdentifier()).getValueWithKey(name);
    } else {
      throw new NoSuchElementException(
          "No relationship " + name + "@" + structure + " found into this relationship manager."
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
  public boolean hasRelationship(@NonNull final Structure structure, @NonNull final String name) {
    return _relationshipsByTable.get(structure.getIdentifier()).containsValueWithKey(name);
  }
}
