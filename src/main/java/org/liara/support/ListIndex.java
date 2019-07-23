package org.liara.support;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ListIndex<Key, Value> implements Index<Key, Value>
{
  @NonNull
  private final Comparator<Key> _keyComparator;

  @NonNull
  private final List<Value> _values;

  @NonNull
  private final List<Key> _keys;

  @NonNull
  private final List<Key> _orderedKeys;

  @NonNull
  private final List<@NonNegative @NonNull Integer> _keysIndexes;

  @NonNull
  private final List<Value> _readonlyValues;

  @NonNull
  private final List<Key> _readonlyKeys;

  public ListIndex (
    @NonNull final Comparator<Key> keyComparator
  ) {
    this(16, keyComparator);
  }

  public ListIndex (
    @NonNegative final int capacity,
    @NonNull final Comparator<Key> keyComparator
  ) {
    _keys = new ArrayList<>(capacity);
    _orderedKeys = new ArrayList<>(capacity);
    _keysIndexes = new ArrayList<>(capacity);
    _values = new ArrayList<>(capacity);

    _readonlyKeys = Collections.unmodifiableList(_keys);
    _readonlyValues = Collections.unmodifiableList(_values);

    _keyComparator = keyComparator;
  }

  public void put (final Key key, final Value value) {
    final int orderedKeyIndex = Collections.binarySearch(_orderedKeys, key, _keyComparator);

    if (orderedKeyIndex < 0) {
      final int keyInsertionIndex = -orderedKeyIndex - 1;

      _orderedKeys.add(keyInsertionIndex, key);
      _keysIndexes.add(keyInsertionIndex, _keys.size());
      _keys.add(key);
      _values.add(value);
    } else {
      _values.set(_keysIndexes.get(orderedKeyIndex), value);
    }
  }

  public void setValue (final int index, final Value value) {
    _values.set(index, value);
  }

  public void setValue (final Key key, final Value value) {
    _values.set(getIndexOfKey(key), value);
  }

  public void setKey (final Key from, final Key to) {
    final int index = Collections.binarySearch(_orderedKeys, from, _keyComparator);

    if (index < 0) {
      throw new IllegalArgumentException(
        "Unable to update the key of the pair with key \"" + from.toString() + "\" to \"" +
        to.toString() + "\" because no pair with key \"" + from.toString() + "\" exists " +
        "into this index."
      );
    }

    setKey(_keysIndexes.get(index), to);
  }

  public void setKey (final int index, final Key target) {
    if (index >= _keys.size()) {
      throw new IllegalArgumentException(
        "Unable to update the key of the " + index + "th pair to \"" + target.toString() +
        "\" because the " + index + "th pair is out of this index bounds that are between " +
        "0 (included) and " + _keys.size() + " (excluded)."
      );
    }

    if (containsKey(target)) {
      throw new IllegalArgumentException(
        "Unable to update the key of the " + index + "th pair to \"" + target.toString() +
        "\" because another pair of this index does already use the given key."
      );
    }

    final int oldKeyIndex = Collections.binarySearch(
      _orderedKeys, _keys.get(index), _keyComparator
    );
    final int keyField = _keysIndexes.get(oldKeyIndex);

    _keys.set(keyField, target);
    _orderedKeys.remove(oldKeyIndex);
    _keysIndexes.remove(oldKeyIndex);

    final int newKeyIndex = -Collections.binarySearch(_orderedKeys, target, _keyComparator) - 1;

    _orderedKeys.add(newKeyIndex, target);
    _keysIndexes.add(newKeyIndex, keyField);
  }

  public void remove (final Key key) {
    int orderedKeyIndex = Collections.binarySearch(_orderedKeys, key, _keyComparator);

    if (orderedKeyIndex < 0) {
      throw new IllegalArgumentException(
        "Unable to remove the pair with the key \"" + key.toString() + "\" from this " +
        "index because no pair with key \"" + key.toString() + "\" exists into it."
      );
    } else {
      @NonNegative final int keyIndex = _keysIndexes.get(orderedKeyIndex);

      _values.remove(keyIndex);
      _keys.remove(keyIndex);
      _orderedKeys.remove(orderedKeyIndex);
      _keysIndexes.remove(orderedKeyIndex);

      for (int index = keyIndex, size = _keys.size(); index < size; ++index) {
        int otherKeyIndex = Collections.binarySearch(
          _orderedKeys, _keys.get(index), _keyComparator
        );
        _keysIndexes.set(otherKeyIndex, _keysIndexes.get(otherKeyIndex) - 1);
      }
    }
  }

  public void remove (final int keyIndex) {
    int orderedKeyIndex = Collections.binarySearch(
      _orderedKeys, _keys.get(keyIndex), _keyComparator
    );

    _values.remove(keyIndex);
    _keys.remove(keyIndex);
    _orderedKeys.remove(orderedKeyIndex);
    _keysIndexes.remove(orderedKeyIndex);

    for (int index = keyIndex, size = _keys.size(); index < size; ++index) {
      int otherKeyIndex = Collections.binarySearch(
        _orderedKeys, _keys.get(index), _keyComparator
      );
      _keysIndexes.set(otherKeyIndex, _keysIndexes.get(otherKeyIndex) - 1);
    }
  }

  public void clear () {
    _keys.clear();
    _keysIndexes.clear();
    _orderedKeys.clear();
    _values.clear();
  }

  @Override
  public @NonNegative int getIndexOfKey (final Key key) {
    final int index = Collections.binarySearch(_orderedKeys, key, _keyComparator);

    if (index < 0) {
      throw new NoSuchElementException(
        "Unable to retrieve the index of key \"" + key.toString() +
        "\" into this index because the given key is not in this index."
      );
    }

    return _keysIndexes.get(index);
  }

  @Override
  public boolean containsKey (final Key key) {
    return Collections.binarySearch(_orderedKeys, key, _keyComparator) >= 0;
  }

  @Override
  public Key getKey (@NonNegative final int index) {
    return _keys.get(index);
  }

  @Override
  public Value getValue (@NonNegative final int index) {
    return _values.get(index);
  }

  @Override
  public Value getValue (final Key key) {
    return _values.get(getIndexOfKey(key));
  }

  @Override
  public int getSize () {
    return _keys.size();
  }

  @Override
  public @NonNull List<Value> getValues () {
    return _readonlyValues;
  }

  @Override
  public @NonNull List<Key> getKeys () {
    return _readonlyKeys;
  }
}
