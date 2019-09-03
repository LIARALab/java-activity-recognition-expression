package org.liara.support.view;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class BaseView<T> implements View<T> {
  @Override
  public @NonNull String toString() {
    @NonNull final StringBuilder builder = new StringBuilder();
    builder.append(super.toString());
    builder.append('[');

    for (int index = 0, size = getSize(); index < size; ++index) {
      builder.append(get(index).toString());
      if (index < size - 1) {
        builder.append(", ");
      }
    }

    builder.append(']');

    return builder.toString();
  }
}
