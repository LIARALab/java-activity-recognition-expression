package org.liara.support.view;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ViewIterator<T> implements Iterator<T> {

  @NonNegative
  private int _next;

  @NonNull
  private final View<T> _view;

  public ViewIterator(@NonNull final View<T> view) {
    _next = 0;
    _view = view;
  }

  @Override
  public boolean hasNext() {
    return _next < _view.getSize();
  }

  @Override
  public T next() {
    if (_next < _view.getSize()) {
      return _view.get(_next++);
    } else {
      throw new NoSuchElementException();
    }
  }

  public boolean hasPrevious() {
    return _next > 0;
  }

  public T previous() {
    if (_next > 0) {
      return _view.get(--_next);
    } else {
      throw new NoSuchElementException();
    }
  }

  public int nextIndex() {
    if (_next < _view.getSize()) {
      return _next;
    } else {
      throw new NoSuchElementException();
    }
  }

  public int previousIndex() {
    if (_next > 0) {
      return _next - 1;
    } else {
      throw new NoSuchElementException();
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Move this iterator to the given location.
   *
   * @param index The index where to set this iterator.
   */
  public void setIndex(@NonNegative final int index) {
    _next = index;
  }

  /**
   * @return The current position of this iterator.
   */
  public @NonNegative int getIndex() {
    return _next;
  }
}
