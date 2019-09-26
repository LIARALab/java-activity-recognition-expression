package org.liara.data.mapping.implementation;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Field;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.support.BaseComparators;
import org.liara.support.index.ListIndex;
import org.liara.support.view.View;

public class ImmutableStructure implements Structure {
  @NonNull
  private final ListIndex<@NonNull String, @NonNull ImmutableField> _fields;

  @NonNull
  private final String _name;

  @NonNegative
  private final int _identifier;

  @NonNull
  private final ImmutableMapping _mapping;

  public ImmutableStructure(
      @NonNull final ImmutableMapping mapping,
      @NonNull final Structure structure
  ) {
    _identifier = structure.getIdentifier();
    _name = structure.getName();
    _mapping = mapping;
    _fields = new ListIndex<>(structure.getFields().getSize(), BaseComparators.STRING_COMPARATOR);

    for (@NonNull final Field field : structure.getFields()) {
      _fields.put(
          field.getName(), new ImmutableField(this, field)
      );
    }
  }

  @Override
  public @NonNull ImmutableMapping getMapping() {
    return _mapping;
  }

  @Override
  public @NonNegative int getIdentifier() {
    return _identifier;
  }

  @Override
  public @NonNull String getName() {
    return _name;
  }

  @Override
  public @NonNull View<@NonNull ? extends ImmutableField> getFields() {
    return _fields.getValues();
  }

  @Override
  public @NonNull ImmutableField getFieldWithName(@NonNull final String name) throws NoSuchElementException {
    return _fields.getValueWithKey(name);
  }

  @Override
  public @NonNull ImmutableField getFieldWithIdentifier(@NonNegative final int identifier)
      throws NoSuchElementException {
    @NonNull final ImmutableField field = _mapping.getFieldWithIdentifier(identifier);

    if (field.getStructure() == this) {
      return field;
    } else {
      throw new NoSuchElementException(
          "No field with identifier " + identifier + " exists into this structure."
      );
    }
  }

  @Override
  public boolean containsFieldWithName(final @NonNull String name) {
    return _fields.containsValueWithKey(name);
  }

  @Override
  public boolean containsFieldWithIdentifier(@NonNegative final int identifier) {
    return _mapping.containsFieldWithIdentifier(identifier) &&
           _mapping.getFieldWithIdentifier(identifier).getStructure() == this;
  }
}
