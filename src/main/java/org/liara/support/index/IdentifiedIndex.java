package org.liara.support.index;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public class IdentifiedIndex<T> implements Index<@NonNegative Integer, T> {
  @NonNull
  private final SparseSet _set;

  @NonNull
  private ArrayList<@NonNull T> _values;

  @NonNull
  private final View<@NonNull T> _valuesView;

  @NonNull
  private final View<@NonNegative Integer> _keysView;

  public IdentifiedIndex (@NonNegative final int capacity) {
    _set = new SparseSet(capacity);
    _values = new ArrayList<>(capacity);
    _valuesView = View.readonly(_values);
    _keysView = View.readonly(_set::get, _set::getSize);
  }

  public void put (@NonNegative final int key, final T value) {
    if (key >= _set.getCapacity()) {
      int nextCapacity = Math.max(_set.getCapacity(), 1);
      while (nextCapacity <= key) nextCapacity *= 2;
      _set.reallocate(nextCapacity);
    }

    if (_set.contains(key)) {
      _values.set(_set.indexOf(key), value);
    } else {
      _set.add(key);
      _values.add(value);
    }
  }

  public void remove(@NonNegative final int key) {
    if (_set.contains(key)) {
      @NonNegative final int index = _set.indexOf(key);
      _set.remove(key);
      _values.set(index, _values.get(_values.size() - 1));
      _values.remove(_values.size() - 1);
    }
  }

  @Override
  public @NonNegative int getIndexOfKey(@NonNegative final Integer integer) {
    final int index = _set.indexOf(integer);

    if (index > -1) {
      return index;
    } else {
      throw new NoSuchElementException(
          "Unable to retrieve the index of key #" + integer + " into this index because the " +
          "given key does not exists."
      );
    }
  }

  @Override
  public @NonNegative Integer getKey(@NonNegative final int index) {
    return _set.get(index);
  }

  @Override
  public boolean containsValueWithKey(@NonNegative final Integer integer) {
    return _set.contains(integer);
  }

  @Override
  public T getValueWithIndex(@NonNegative final int index) {
    return _values.get(index);
  }

  @Override
  public T getValueWithKey(@NonNegative final Integer key) {
    return _values.get(_set.indexOf(key));
  }

  @Override
  public int getSize() {
    return _values.size();
  }

  @Override
  public @NonNull View<? extends T> getValues() {
    return _valuesView;
  }

  @Override
  public @NonNull View<? extends @NonNegative Integer> getKeys() {
    return _keysView;
  }

  public void clear () {
    _set.clear();
    _values.clear();
  }
}
