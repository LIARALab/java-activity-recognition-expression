package org.liara.support.index;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

public interface Index<Key, Value> {
  @NonNegative int getIndexOfKey(final Key key);

  Key getKey(@NonNegative final int index);

  boolean containsValueWithKey(final Key key);

  Value getValueWithIndex(@NonNegative final int index);

  Value getValueWithKey(final Key key);

  int getSize();

  @NonNull View<? extends Value> getValues();

  @NonNull View<? extends Key> getKeys();
}
