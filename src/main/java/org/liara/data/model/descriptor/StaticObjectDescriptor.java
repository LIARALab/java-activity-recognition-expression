package org.liara.data.model.descriptor;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

import java.util.*;

public class StaticObjectDescriptor
  implements ObjectDescriptor
{
  @NonNull
  private final List<@NonNull ModelElementDescriptor> _childrenByField;

  @NonNull
  private final List<@NonNull String> _keysByField;

  @NonNull
  private final List<@NonNull String> _keysByOrder;

  @NonNull
  private final List<@NonNull Integer> _fieldsByKey;

  @NonNull
  private final View<@NonNull ModelElementDescriptor> _childrenView;

  @NonNull
  private final View<@NonNull String> _keysView;

  /**
   * Create a new static object descriptor.
   */
  public StaticObjectDescriptor () {
    _childrenByField = new ArrayList<>(16);
    _keysByField = new ArrayList<>(16);
    _keysByOrder = new ArrayList<>(16);
    _fieldsByKey = new ArrayList<>(16);

    _childrenView = View.readonly(ModelElementDescriptor.class, _childrenByField);
    _keysView = View.readonly(String.class, _keysByField);
  }

  /**
   * Set the value of a field identified by a given key.
   *
   * @param key The key that identify the field to set.
   * @param descriptor A descriptor of the expected value of the field identified by the given key.
   */
  public void set (@NonNull final String key, @NonNull final ModelElementDescriptor descriptor) {
    final int orderedKeyIndex = Collections.binarySearch(_keysByOrder, key);

    if (orderedKeyIndex < 0) {
      final int insertionIndex = -orderedKeyIndex - 1;
      _keysByOrder.add(insertionIndex, key);
      _fieldsByKey.add(insertionIndex, _childrenByField.size());
      _keysByField.add(key);
      _childrenByField.add(descriptor);
    } else {
      _childrenByField.set(_fieldsByKey.get(orderedKeyIndex), descriptor);
    }
  }

  /**
   * Rename a field of this object.
   *
   * @param oldName Name of the field to rename.
   * @param newName The new name of the given field.
   */
  public void rename (@NonNull final String oldName, @NonNull final String newName) {
    final int oldNameOrderedIndex = Collections.binarySearch(_keysByOrder, oldName);

    if (oldNameOrderedIndex < 0) {
      throw new IllegalArgumentException(
        "Unable to rename the field \"" + oldName + "\" field of this object descriptor to \"" +
        newName + "\" because no field exists with the given name into this object descriptor."
      );
    }

    rename(_fieldsByKey.get(oldNameOrderedIndex), newName);
  }

  /**
   * Rename a field of this object.
   *
   * @param field Index of the field to rename.
   * @param name The new name of the given field.
   */
  public void rename (@NonNegative final int field, @NonNull final String name) {
    if (field >= _childrenByField.size()) {
      throw new NoSuchElementException(
        "Unable to rename the " + field + "th field of this object descriptor to \"" + name +
        "\" because the given field does not exists (" + field + " not in [0, " +
        _childrenByField.size() + "[)."
      );
    }

    if (containsKey(name)) {
      throw new IllegalArgumentException(
        "Unable to rename the " + field + "th field of this object descriptor to \"" + name +
        "\" because another field does already have the given name."
      );
    }

    final int oldNameOrderedIndex = Collections.binarySearch(_keysByOrder, _keysByField.get(field));

    _keysByField.set(field, name);
    _keysByOrder.remove(oldNameOrderedIndex);
    _fieldsByKey.remove(oldNameOrderedIndex);

    final int newNameOrderedIndex = -Collections.binarySearch(_keysByOrder, name) - 1;

    _keysByOrder.add(newNameOrderedIndex, name);
    _fieldsByKey.add(newNameOrderedIndex, field);
  }

  /**
   * Remove a field identified by the given key.
   *
   * @param key The key that identify the field to remove.
   */
  public void remove (@NonNull final String key) {
    int orderedKeyIndex = Collections.binarySearch(_keysByOrder, key);

    if (orderedKeyIndex < 0) {
      throw new NoSuchElementException(
        "Unable to delete the field identified by the key \"" + key + "\" from this object " +
        "descriptor because this object descriptor does not contains any field identified by the " +
        "given key."
      );
    } else {
      @NonNegative final int keyField = _fieldsByKey.get(orderedKeyIndex);

      _childrenByField.remove(keyField);
      _keysByField.remove(keyField);
      _keysByOrder.remove(orderedKeyIndex);
      _fieldsByKey.remove(orderedKeyIndex);

      for (int index = keyField, size = _keysByField.size(); index < size; ++index) {
        int keyIndex = Collections.binarySearch(_keysByOrder, _keysByField.get(index));
        _fieldsByKey.set(keyIndex, _fieldsByKey.get(keyIndex) - 1);
      }
    }
  }

  /**
   * Remove the nth field.
   *
   * @param index Index of the field to remove.
   */
  public void remove (@NonNegative final int index) {
    remove(_keysByField.get(index));
  }

  /**
   * Empty this object description of its fields.
   */
  public void clear () {
    _keysByField.clear();
    _childrenByField.clear();
    _keysByOrder.clear();
    _fieldsByKey.clear();
  }

  /**
   * Return true if this object contains a field identified by the given key.
   *
   * @param key A key.
   *
   * @return True if this object contains a field identified by the given key.
   */
  public boolean containsKey (@NonNull final String key) {
    return Collections.binarySearch(_keysByOrder, key) >= 0;
  }

  /**
   * @see ModelElementDescriptor#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElementDescriptor> getChildren () {
    return _childrenView;
  }

  /**
   * @see ObjectDescriptor#getKeys()
   */
  @Override
  public @NonNull View<@NonNull String> getKeys () {
    return _keysView;
  }

  /**
   * @see ObjectDescriptor#getFieldOf(String)
   */
  @Override
  public @NonNegative int getFieldOf (final @NonNull String key) {
    final int index = Collections.binarySearch(_keysByOrder, key);

    if (index < 0) {
      throw new NoSuchElementException(
        "Unable to get the index of the field with key \"" + key + "\" of this object " +
        "descriptor because this object descriptor does not contains any field identified by the " +
        "given key."
      );
    } else {
      return _fieldsByKey.get(index);
    }
  }
}
