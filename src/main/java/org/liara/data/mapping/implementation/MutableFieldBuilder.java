package org.liara.data.mapping.implementation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.mapping.Field;
import org.liara.data.primitive.Primitive;

public class MutableFieldBuilder {
  @Nullable
  private String _name;

  @Nullable
  private MutableStructure _structure;

  @Nullable
  private Primitive<?> _type;

  public MutableFieldBuilder () {
    _name = null;
    _structure = null;
    _type = null;
  }

  public MutableFieldBuilder (@NonNull final MutableFieldBuilder toCopy) {
    _name = toCopy.getName();
    _structure = toCopy.getStructure();
    _type = toCopy.getType();
  }

  public @NonNull MutableField build () {
    return new MutableField(this);
  }

  public @Nullable String getName() {
    return _name;
  }

  public void setName(@Nullable final String name) {
    _name = name;
  }

  public @Nullable MutableStructure getStructure() {
    return _structure;
  }

  public void setStructure(@Nullable final MutableStructure structure) {
    _structure = structure;
  }

  public @Nullable Primitive<?> getType() {
    return _type;
  }

  public void setType(@Nullable final Primitive<?> type) {
    _type = type;
  }
}
