package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ExpressionWalker
{
  @NonNull
  private Expression<?> _root;

  @NonNull
  private final List<@NonNull Expression<?>> _path;

  @NonNull
  private final List<@NonNull Integer> _cursors;

  @NonNull
  private final List<@NonNull Expression<?>> _readonlyPath;

  private boolean _forward;

  /**
   * Create a new walker of expression.
   *
   * @param root Identity expression of this walker.
   */
  public ExpressionWalker (@NonNull final Expression<?> root) {
    _root = new Identity<>(root);
    _path = new ArrayList<>();
    _cursors = new ArrayList<>();
    _readonlyPath = Collections.unmodifiableList(_path);
    _forward = true;

    _cursors.add(0);
  }

  /**
   * Create an walker that is a copy of another one.
   *
   * @param toCopy An walker to copy.
   */
  public ExpressionWalker (@NonNull final ExpressionWalker toCopy) {
    _root = toCopy._root;
    _path = new LinkedList<>(toCopy.getPath());
    _cursors = new LinkedList<>(toCopy._cursors);
    _readonlyPath = Collections.unmodifiableList(_path);

    _forward = toCopy.doesMoveForward();
  }

  /**
   * Try to enter into the next child expression if any.
   *
   * @return The next child expression.
   *
   * @throws NoSuchElementException If the current walker can't enter into another expression.
   */
  public @NonNull Expression<?> enter () {
    @NonNull final Expression<?> current = _path.isEmpty() ? _root : _path.get(_path.size() - 1);
    final int index = _cursors.get(_cursors.size() - 1) + (_forward ? 1 : -1);

    if (index > 0 && index <= current.getChildren().getSize()) {
      @NonNull final Expression<?> next = current.getChildren().get(index - 1);
      _path.add(next);
      _cursors.set(_cursors.size() - 1, index);
      _cursors.add(_forward ? 0 : next.getChildren().getSize() + 1);

      return next;
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker can enter into another expression.
   */
  public boolean canEnter () {
    @NonNull final Expression<?> current = _path.isEmpty() ? _root : _path.get(_path.size() - 1);
    final int index = _cursors.get(_cursors.size() - 1) + (_forward ? 1 : -1);

    return index > 0 && index <= current.getChildren().getSize();
  }

  /**
   * Try to return the current expression if any.
   *
   * @return The current expression.
   *
   * @throws NoSuchElementException If the current walker does not reference an expression.
   */
  public @NonNull Expression<?> current () {
    if (_path.size() > 0) {
      return _path.get(_path.size() - 1);
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker is currently over an expression element.
   */
  public boolean hasCurrent () {
    return _path.size() > 0;
  }

  /**
   * Try to exit the current expression if any.
   *
   * @return The exited expression if any.
   *
   * @throws NoSuchElementException If the current walker can't exit any expression.
   */
  public @NonNull Expression<?> exit () {
    if (_path.size() > 0) {
      _cursors.remove(_cursors.size() - 1);
      @NonNull final Expression<?> result = _path.remove(_path.size() - 1);

      @NonNull final Expression<?> current = _path.isEmpty() ? _root : _path.get(_path.size() - 1);
      final int index = _cursors.get(_cursors.size() - 1);

      if ((!_forward && index == 1) || (_forward && index == current.getChildren().getSize())) {
        _cursors.set(_cursors.size() - 1,  index + (_forward ? 1 : -1));
      }

      return result;
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker can exit an expression.
   */
  public boolean canExit () {
    return _path.size() > 0;
  }

  /**
   * Move this walker before the first element.
   */
  public void moveToStart () {
    _path.clear();
    _cursors.clear();
    _cursors.add(0);
  }

  /**
   * Move this walker after the last element.
   */
  public void moveToEnd () {
    _path.clear();
    _cursors.clear();
    _cursors.add(2);
  }

  /**
   * Make this walker moving movesBackward.
   */
  public void movesBackward () {
    _forward = false;
  }

  /**
   * Make this walker moving movesForward.
   */
  public void movesForward () {
    _forward = true;
  }

  /**
   * Move to the given walker location.
   *
   * @param walker A walker to go to.
   */
  public void moveTo (@NonNull final ExpressionWalker walker) {
    if (walker.getExpression() != getExpression()) {
      throw new IllegalArgumentException(
        "Unable to moveTo to the given walker location because the given walker does not walk " +
        "throughout the same expression."
      );
    } else {
      _path.clear();
      _cursors.clear();
      _path.addAll(walker.getPath());
      _cursors.addAll(walker._cursors);
    }
  }

  /**
   * @return True if this walker is at the same location as the given one.
   */
  public boolean isAtLocation (@NonNull final ExpressionWalker walker) {
    return walker.getExpression() == getExpression() && walker._cursors.equals(_cursors);
  }

  /**
   * @return True if this walker is at the end of the expression.
   */
  public boolean isAtEnd () {
    return _path.isEmpty() && _cursors.get(0) == 2;
  }

  /**
   * @return True if this walker is at the start of the expression.
   */
  public boolean isAtStart () {
    return _path.isEmpty() && _cursors.get(0) == 0;
  }

  /**
   * @return True if this walker moves movesForward.
   */
  public boolean doesMoveForward () {
    return _forward;
  }

  /**
   * Make this walker to move movesForward or movesBackward.
   *
   * @param forward True if this walker must move movesForward.
   */
  public void setMovingForward (final boolean forward) {
    _forward = forward;
  }

  /**
   * @return The expression traversed by this walker.
   */
  public @NonNull Expression<?> getExpression () {
    return _root.getChildren().get(0);
  }

  public void setExpression (@NonNull final Expression<?> expression) {
    _root = new Identity<>(expression);

    if (_forward) {
      moveToStart();
    } else {
      moveToEnd();
    }
  }

  /**
   * @return The current path as a readonly collection.
   */
  public @NonNull List<@NonNull Expression<?>> getPath () {
    return _readonlyPath;
  }
}
