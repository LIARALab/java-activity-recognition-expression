package org.liara.data.mapping.implementation;

import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Field;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.data.primitive.Primitive;

public class ImmutableField implements Field {
  @NonNull
  private final ImmutableStructure _structure;

  @NonNegative
  private final int _identifier;

  @NonNull
  private final String _name;

  @NonNull
  private final Primitive<?> _type;

  public ImmutableField(@NonNull final ImmutableStructure structure, @NonNull final Field field) {
    _structure = structure;
    _identifier = field.getIdentifier();
    _name = field.getName();
    _type = field.getType();
  }

  /**
   * @see Field#getName()
   */
  @Override
  public @NonNull String getName() {
    return _name;
  }

  /**
   * @see Field#getType()
   */
  @Override
  public @NonNull Primitive<?> getType() {
    return _type;
  }

  @Override
  public @NonNegative int getIdentifier() {
    return _identifier;
  }

  @Override
  public @NonNull ImmutableStructure getStructure() {
    return _structure;
  }

  @Override
  public @NonNull ImmutableMapping getMapping() {
    return _structure.getMapping();
  }
}
