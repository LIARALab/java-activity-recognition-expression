package org.liara.data.mapping.implementation;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.Field;
import org.liara.data.mapping.Mapping;
import org.liara.data.mapping.Structure;
import org.liara.data.primitive.Primitive;
import org.liara.support.BaseComparators;
import org.liara.support.index.ListIndex;
import org.liara.support.view.View;

public class MutableStructure implements Structure {
  @NonNull
  private final MutableMapping _mapping;

  @NonNull
  private String _name;

  @NonNull
  private final ListIndex<@NonNull String, @NonNull MutableField> _fields;

  @NonNegative
  private final int _identifier;

  public MutableStructure (@NonNull final MutableStructureBuilder builder) {
    _mapping = Objects.requireNonNull(builder.getMapping());
    _name = Objects.requireNonNull(builder.getName());
    _fields = new ListIndex<>(8, BaseComparators.STRING_COMPARATOR);
    _identifier = _mapping.registerStructure(this);
  }

  public <T> @NonNull MutableField createField (
      @NonNull final Primitive<T> type,
      @NonNull final String name
  ) {
    @NonNull final MutableFieldBuilder builder = new MutableFieldBuilder();
    builder.setName(name);
    builder.setType(type);
    builder.setStructure(this);
    return builder.build();
  }

  public void remove () {
    _mapping.removeStructure(this);
  }

  public void removeFieldWithName(@NonNull final String name) {
    if (containsFieldWithName(name)) {
      removeField(getFieldWithName(name));
    }
  }

  public void removeFieldWithIdentifier(@NonNegative final int identifier) {
    if (containsFieldWithIdentifier(identifier)) {
      removeField(getFieldWithIdentifier(identifier));
    }
  }

  public void removeField(@NonNull final MutableField field) {
    if (field.getStructure() == this) {
      _fields.remove(field.getName());
      _mapping.removeField(field);
    }
  }

  @Override
  public @NonNull MutableMapping getMapping() {
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

  public void setName (@NonNull final String name) {
    if (!_name.equals(name)) {
      @NonNull final String oldName = _name;
      _name = name;
      _mapping.renameStructure(oldName, name);
    }
  }

  public void renameField (@NonNull final String oldName, @NonNull final String newName) {
    if (containsFieldWithName(oldName)) {
      if (containsFieldWithName(newName)) {
        throw new IllegalArgumentException(
            "Unable to rename structure with name \"" + oldName + "\" to a structure with name \"" +
                newName + "\" because a structure with name \"" + newName +
                "\" already exists into this mapping."
        );
      } else {
        @NonNull final MutableField field = _fields.getValueWithKey(oldName);

        _fields.put(newName, field);
        _fields.remove(oldName);

        field.setName(newName);
      }
    } else {
      throw new IllegalArgumentException(
          "Unable to rename structure with name \"" + oldName + "\" to a structure with name \"" +
              newName + "\" because no structure with name \"" + oldName +
              "\" exists into this mapping."
      );
    }
  }

  @Override
  public @NonNull View<@NonNull ? extends Field> getFields() {
    return _fields.getValues();
  }

  @Override
  public @NonNull MutableField getFieldWithName(final @NonNull String name) throws NoSuchElementException {
    return _fields.getValueWithKey(name);
  }

  @Override
  public @NonNull MutableField getFieldWithIdentifier(
      @NonNegative final int identifier
  ) throws NoSuchElementException {
    @NonNull final MutableField field = _mapping.getFieldWithIdentifier(identifier);

    if (field.getStructure() == this) {
      return field;
    } else {
      throw new NoSuchElementException(
          "No field with identifier #" + identifier + " into this structure."
      );
    }
  }

  /**
   * Register the given field into this structure.
   *
   * @param field A field to register.
   *
   * @return The identifier assigned to the given field.
   */
  public @NonNegative int registerField (@NonNull final MutableField field) {
    if (field.getStructure() != this) {
      throw new IllegalArgumentException(
          "Unable to register the field " + field + "\" into this structure " + this +
              " because the field belongs to the structure " +  field.getStructure() + "."
      );
    }

    if (containsFieldWithName(field.getName())) {
      if (getFieldWithName(field.getName()) == field) {
        return _mapping.registerField(field);
      } else {
        throw new IllegalArgumentException(
            "Unable to register the field " + field + "\" into this structure because another " +
                "field of the same name already exists into this structure " +
                getFieldWithName(field.getName()) + "."
        );
      }
    }

    _fields.put(field.getName(), field);

    return _mapping.registerField(field);
  }

  @Override
  public boolean containsFieldWithName(@NonNull final String name) {
    return _fields.containsValueWithKey(name);
  }

  @Override
  public boolean containsFieldWithIdentifier(@NonNegative final int identifier) {
    return _mapping.containsFieldWithIdentifier(identifier) &&
           _mapping.getFieldWithIdentifier(identifier).getStructure() == this;
  }

  public void clear () {
    _fields.getValues().stream().forEach(this::removeField);
  }
}
