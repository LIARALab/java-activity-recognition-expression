package org.liara.data.mapping.implementation;

import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Field;
import org.liara.data.mapping.Structure;
import org.liara.data.primitive.Primitive;

public class MutableField implements Field {
  @NonNull
  private String _name;

  @NonNull
  private Primitive<?> _type;

  @NonNull
  private final MutableStructure _structure;

  @NonNegative
  private final int _identifier;

  public MutableField (@NonNull final MutableFieldBuilder builder) {
    _name = Objects.requireNonNull(builder.getName());
    _type = Objects.requireNonNull(builder.getType());
    _structure = Objects.requireNonNull(builder.getStructure());
    _identifier = _structure.registerField(this);
  }

  public void remove () {
    _structure.removeField(this);
  }

  @Override
  public @NonNull MutableMapping getMapping() {
    return _structure.getMapping();
  }

  @Override
  public @NonNull String getName() {
    return _name;
  }

  public void setName(@NonNull final String name) {
    if (!_name.equals(name)) {
      @NonNull final String oldName = _name;
      _name = name;
      _structure.renameField(oldName, _name);
    }
  }

  @Override
  public @NonNegative int getIdentifier() {
    return _identifier;
  }

  @Override
  public @NonNull MutableStructure getStructure() {
    return _structure;
  }

  @Override
  public @NonNull Primitive<?> getType() {
    return _type;
  }

  public void setType(@NonNull final Primitive<?> type) {
    _type = type;
  }
}
