package org.liara.data.mapping.implementation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MutableStructureBuilder {
  @Nullable
  private String _name;

  @Nullable
  private MutableMapping _mapping;

  public MutableStructureBuilder () {
    _name = null;
    _mapping = null;
  }

  public MutableStructureBuilder (@NonNull final MutableStructureBuilder builder) {
    _name = builder.getName();
    _mapping = builder.getMapping();
  }

  public @NonNull MutableStructure build () {
    return new MutableStructure(this);
  }

  public @Nullable String getName() {
    return _name;
  }

  public void setName(@Nullable final String name) {
    _name = name;
  }

  public @Nullable MutableMapping getMapping() {
    return _mapping;
  }

  public void setMapping(@Nullable final MutableMapping mapping) {
    _mapping = mapping;
  }
}
