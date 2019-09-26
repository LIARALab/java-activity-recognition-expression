package org.liara.data.mapping.implementation;

import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Mapping;
import org.liara.support.BaseComparators;
import org.liara.support.index.IdentifiedIndex;
import org.liara.support.index.ListIndex;
import org.liara.support.view.View;

public class MutableMapping implements Mapping {
  @NonNull
  private String _name;

  @NonNull
  private final IdentifiedIndex<@NonNull MutableStructure> _structures;

  @NonNegative
  private int _nextStructureIdentifier;

  @NonNull
  private final IdentifiedIndex<@NonNull MutableField> _fields;

  @NonNegative
  private int _nextFieldIdentifier;

  @NonNull
  private final ListIndex<@NonNull String, @NonNull MutableStructure> _structuresByName;

  public MutableMapping () {
    _name = "unnamed";
    _structures = new IdentifiedIndex<>(16);
    _structuresByName = new ListIndex<>(16, BaseComparators.STRING_COMPARATOR);
    _fields = new IdentifiedIndex<>(64);
    _nextStructureIdentifier = 0;
    _nextFieldIdentifier = 0;
  }

  public @NonNull MutableStructure createStructure (@NonNull final String name) {
    @NonNull final MutableStructureBuilder builder = new MutableStructureBuilder();

    builder.setName(name);
    builder.setMapping(this);

    return builder.build();
  }

  public void renameStructure (@NonNull final String oldName, @NonNull final String newName) {
    if (containsStructureWithName(oldName)) {
      if (containsStructureWithName(newName)) {
        throw new IllegalArgumentException(
            "Unable to rename structure with name \"" + oldName + "\" to a structure with name \"" +
                newName + "\" because a structure with name \"" + newName +
                "\" already exists into this mapping."
        );
      } else {
        @NonNull final MutableStructure structure = _structuresByName.getValueWithKey(oldName);

        _structuresByName.put(newName, structure);
        _structuresByName.remove(oldName);

        structure.setName(newName);
      }
    } else {
      throw new IllegalArgumentException(
          "Unable to rename structure with name \"" + oldName + "\" to a structure with name \"" +
              newName + "\" because no structure with name \"" + oldName +
              "\" exists into this mapping."
      );
    }
  }

  public @NonNegative int registerStructure (@NonNull final MutableStructure structure) {
    if (_structuresByName.containsValueWithKey(structure.getName())) {
      if (_structuresByName.getValueWithKey(structure.getName()) == structure) {
        return structure.getIdentifier();
      } else {
        throw new IllegalArgumentException(
            "Unable to register the given structure " + structure + " because another structure " +
                "already exists with the same name into this mapping " +
                _structuresByName.getValueWithKey(structure.getName()) + "."
        );
      }
    }

    @NonNegative final int result = _nextStructureIdentifier;

    do {
      _nextStructureIdentifier += 1;
    } while (containsStructureWithIdentifier(_nextStructureIdentifier));

    _structures.put(result, structure);
    _structuresByName.put(structure.getName(), structure);

    return result;
  }

  public @NonNegative int registerField (@NonNull final MutableField field) {
    if (field.getStructure().containsFieldWithName(field.getName())) {
      if (field.getStructure().getFieldWithName(field.getName()) == field) {
        return field.getIdentifier();
      } else {
        throw new IllegalArgumentException(
            "Unable to register the given field " + field + " of structure " +
                field.getStructure() + " because another field " +
                "already exists with the given name into its parent structure. "
        );
      }
    }

    @NonNegative final int result = _nextFieldIdentifier;

    do {
      _nextFieldIdentifier += 1;
    } while (containsFieldWithIdentifier(_nextFieldIdentifier));

    _fields.put(result, field);

    return field.getStructure().registerField(field);
  }

  public void removeFieldWithIdentifier (@NonNegative final int identifier) {
    if (containsFieldWithIdentifier(identifier)) {
      removeField(getFieldWithIdentifier(identifier));
    }
  }

  public void removeField (@NonNull final MutableField field) {
    if (field.getStructure().getMapping() == this) {
      _fields.remove(field.getIdentifier());
      field.getStructure().removeField(field);

      _nextFieldIdentifier = Math.min(_nextFieldIdentifier, field.getIdentifier());
    }
  }

  public void removeStructureWithIdentifier (@NonNegative final int identifier) {
    if (containsStructureWithIdentifier(identifier)) {
      removeStructure(getStructureWithIdentifier(identifier));
    }
  }

  public void removeStructureWithName (@NonNull final String name) {
    if (containsStructureWithName(name)) {
      removeStructure(getStructureWithName(name));
    }
  }

  public void removeStructure (@NonNull final MutableStructure structure) {
    if (structure.getMapping() == this) {
      _structuresByName.remove(structure.getName());
      _structures.remove(structure.getIdentifier());

      _nextStructureIdentifier = Math.min(_nextStructureIdentifier, structure.getIdentifier());
    }
  }

  @Override
  public @NonNull String getName() {
    return _name;
  }

  public void setName (@NonNull final String name) {
    _name = name;
  }

  @Override
  public @NonNull View<@NonNull ? extends MutableStructure> getStructures() {
    return _structures.getValues();
  }

  @Override
  public @NonNull View<@NonNull ? extends MutableField> getFields() {
    return _fields.getValues();
  }

  @Override
  public @NonNull MutableStructure getStructureWithName(@NonNull final String name) throws NoSuchElementException {
    return _structuresByName.getValueWithKey(name);
  }

  @Override
  public @NonNull MutableStructure getStructureWithIdentifier(@NonNegative final int identifier)
      throws NoSuchElementException {
    return _structures.getValueWithKey(identifier);
  }

  @Override
  public @NonNull MutableField getFieldWithIdentifier(@NonNegative final int identifier)
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

  public void clear() {
    _structures.clear();
    _nextFieldIdentifier = 0;
    _nextStructureIdentifier = 0;
    _structuresByName.clear();
    _fields.clear();
  }
}
