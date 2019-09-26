package org.liara.data.mapping.implementation;

import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Field;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.support.BaseComparators;
import org.liara.support.index.IdentifiedIndex;
import org.liara.support.index.ListIndex;
import org.liara.support.view.View;

public class ImmutableMapping implements Mapping {

  @NonNull
  private final String _name;

  @NonNull
  private final IdentifiedIndex<@NonNull ImmutableStructure> _structures;

  @NonNull
  private final ListIndex<@NonNull String, @NonNull ImmutableStructure> _structuresByName;

  @NonNull
  private final IdentifiedIndex<@NonNull ImmutableField> _fields;

  public ImmutableMapping (@NonNull final Mapping mapping) {
    _name = mapping.getName();

    _structures = new IdentifiedIndex<>(mapping.getStructures().getSize());
    _structuresByName = new ListIndex<>(mapping.getStructures().getSize(), BaseComparators.STRING_COMPARATOR);
    _fields = new IdentifiedIndex<>(mapping.getFields().getSize());

    for (@NonNull final Structure structure : mapping.getStructures()) {
      @NonNull final ImmutableStructure immutableStructure = new ImmutableStructure(this, structure);

      _structuresByName.put(immutableStructure.getName(), immutableStructure);
      _structures.put(immutableStructure.getIdentifier(), immutableStructure);

      for (@NonNull final ImmutableField field : immutableStructure.getFields()) {
        _fields.put(field.getIdentifier(), field);
      }
    }
  }

  @Override
  public @NonNull String getName() {
    return _name;
  }

  @Override
  public @NonNull View<@NonNull ? extends ImmutableStructure> getStructures() {
    return _structures.getValues();
  }

  @Override
  public @NonNull View<@NonNull ? extends ImmutableField> getFields() {
    return _fields.getValues();
  }

  @Override
  public @NonNull ImmutableStructure getStructureWithName(@NonNull final String name)
      throws NoSuchElementException {
    return _structuresByName.getValueWithKey(name);
  }

  @Override
  public @NonNull ImmutableStructure getStructureWithIdentifier(@NonNegative final int identifier)
      throws NoSuchElementException {
    return _structures.getValueWithIndex(identifier);
  }

  @Override
  public @NonNull ImmutableField getFieldWithIdentifier(@NonNegative final int identifier)
      throws NoSuchElementException {
    return _fields.getValueWithKey(identifier);
  }

  @Override
  public boolean containsStructureWithName(@NonNull final String name) {
    return _structuresByName.containsValueWithKey(name);
  }

  @Override
  public boolean containsStructureWithIdentifier(@NonNegative final int identifier) {
    return _structures.containsValueWithKey(identifier);
  }

  @Override
  public boolean containsFieldWithIdentifier(@NonNegative final int identifier) {
    return _fields.containsValueWithKey(identifier);
  }
}
