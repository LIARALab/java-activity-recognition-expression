package org.liara.support;

import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface Index<Key, Value> {

  @NonNegative int getIndexOfKey(final Key key);

  Key getKey(@NonNegative final int index);

  boolean containsKey(final Key key);

  Value getValue(@NonNegative final int index);

  Value getValue(final Key key);

  int getSize();

  @NonNull List<Value> getValues();

  @NonNull List<Key> getKeys();
}
