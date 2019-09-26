package org.liara.support.view;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ViewIterator<T> implements Iterator<T> {
  @NonNegative
  private int _cursor;

  @NonNull
  private final View<T> _view;

  public ViewIterator(@NonNull final View<T> view) {
    _cursor = 0;
    _view = view;
  }

  @Override
  public boolean hasNext() {
    return _cursor < _view.getSize();
  }

  @Override
  public T next() {
    if (hasNext()) {
      _cursor += 1;
      return _view.get(_cursor - 1);
    } else {
      throw new NoSuchElementException();
    }
  }

  public boolean hasPrevious() {
    return _cursor > 0;
  }

  public T previous() {
    if (_cursor > 0) {
      _cursor -= 1;
      return _view.get(_cursor);
    } else {
      throw new NoSuchElementException();
    }
  }

  public @NonNegative int getCursor () {
    return _cursor;
  }

  public void setCursor (@NonNegative final int cursor) {
    _cursor = cursor;
  }
}
