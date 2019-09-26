package org.liara.data.mapping.relationship.implementation;

import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.mapping.Mapping;

public class StaticRelationshipManagerBuilder {
  @Nullable
  private Mapping _mapping;

  @NonNull
  private final List<@NonNull StaticRelationshipBuilder> _relationships;

  public StaticRelationshipManagerBuilder () {
    _mapping = null;
    _relationships = new ArrayList<>(32);
  }

  public StaticRelationshipManagerBuilder (@NonNull final StaticRelationshipManagerBuilder toCopy) {
    _mapping = toCopy.getMapping();
    _relationships = new ArrayList<>(toCopy.getRelationships());
  }

  public @Nullable Mapping getMapping() {
    return _mapping;
  }

  public void setMapping(@Nullable final Mapping mapping) {
    _mapping = mapping;
  }

  public @NonNull List<@NonNull StaticRelationshipBuilder> getRelationships() {
    return _relationships;
  }

  public void setRelationships (
      @Nullable final List<@NonNull StaticRelationshipBuilder> relationships
  ) {
    _relationships.clear();

    if (relationships != null) {
      _relationships.addAll(relationships);
    }
  }
}
