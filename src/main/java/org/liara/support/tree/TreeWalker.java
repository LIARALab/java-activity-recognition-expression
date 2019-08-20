package org.liara.support.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TreeWalker<Node extends TreeElement> {

  @Nullable
  private Node _root;

  @NonNull
  private final List<@NonNull Node> _path;

  @NonNull
  private final List<@NonNull Integer> _cursors;

  @NonNull
  private final List<@NonNull Node> _readonlyPath;

  @NonNull
  private final Class<Node> _elementClass;

  private boolean _forward;

  /**
   * Create a new tree walker.
   *
   * @param elementClass Type of tree element to walk.
   */
  public TreeWalker(@NonNull final Class<Node> elementClass) {
    this(elementClass, null);
  }

  /**
   * Create a new tree walker over a given tree.
   *
   * @param elementClass Type of tree element to walk.
   * @param root Root element of the tree to walk.
   */
  public TreeWalker(@NonNull final Class<Node> elementClass, @Nullable final Node root) {
    _root = root;
    _path = new ArrayList<>();
    _cursors = new ArrayList<>();
    _readonlyPath = Collections.unmodifiableList(_path);
    _forward = true;
    _elementClass = elementClass;

    _cursors.add(0);
  }

  /**
   * Create an walker that is a copy of another one.
   *
   * @param toCopy An existing walker instance to copy.
   */
  public TreeWalker(@NonNull final TreeWalker<Node> toCopy) {
    _root = toCopy._root;
    _path = new LinkedList<>(toCopy.getPath());
    _cursors = new LinkedList<>(toCopy._cursors);
    _readonlyPath = Collections.unmodifiableList(_path);
    _elementClass = toCopy.getElementClass();

    _forward = toCopy.doesMoveForward();
  }

  /**
   * Try to enter into the next child element if any.
   *
   * @return The next child element.
   * @throws NoSuchElementException If the current walker can't enter into another tree element.
   */
  public @NonNull Node enter() {
    @Nullable final Node current = _path.isEmpty() ? null : _path.get(_path.size() - 1);

    if (current != null || _root != null) {
      final int index = _cursors.get(_cursors.size() - 1) + (_forward ? 1 : -1);

      if (index > 0 && index <= (current == null ? 1 : current.getChildren().getSize())) {
        @NonNull final Node next = (
            current == null ? _root
                : _elementClass.cast(current.getChildren().get(index - 1))
        );

        _path.add(next);
        _cursors.set(_cursors.size() - 1, index);
        _cursors.add(_forward ? 0 : next.getChildren().getSize() + 1);

        return next;
      }
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker can enter into another tree element.
   */
  public boolean canEnter() {
    @Nullable final Node current = _path.isEmpty() ? null : _path.get(_path.size() - 1);
    final int index = _cursors.get(_cursors.size() - 1) + (_forward ? 1 : -1);

    return index > 0 && index <= (current == null ? 1 : current.getChildren().getSize());
  }

  /**
   * Try to return the current tree element if any.
   *
   * @return The current tree element.
   * @throws NoSuchElementException If the current walker is not in a tree element.
   */
  public @NonNull Node current() {
    if (_path.size() > 0) {
      return _path.get(_path.size() - 1);
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker is currently over a tree element element.
   */
  public boolean hasCurrent() {
    return _path.size() > 0;
  }

  /**
   * Try to exit the current tree element if any.
   *
   * @return The exited tree element if any.
   * @throws NoSuchElementException If the current walker can't exit any tree element.
   */
  public @NonNull Node exit() {
    if (_path.size() > 0) {
      _cursors.remove(_cursors.size() - 1);
      @NonNull final Node result = _path.remove(_path.size() - 1);

      @Nullable final Node current = _path.isEmpty() ? null : _path.get(_path.size() - 1);
      final int index = _cursors.get(_cursors.size() - 1);

      if (
          (!_forward && index == 1) ||
              (_forward && index == (current == null ? 1 : current.getChildren().getSize()))
      ) {
        _cursors.set(_cursors.size() - 1, index + (_forward ? 1 : -1));
      }

      return result;
    }

    throw new NoSuchElementException();
  }

  /**
   * @return True if this walker can exit a tree element.
   */
  public boolean canExit() {
    return _path.size() > 0;
  }

  /**
   * Move this walker before the first element.
   */
  public void moveToStart() {
    _path.clear();
    _cursors.clear();
    _cursors.add(0);
  }

  /**
   * Move this walker after the last element.
   */
  public void moveToEnd() {
    _path.clear();
    _cursors.clear();
    _cursors.add(2);
  }

  /**
   * Make this walker moving backward.
   */
  public void movesBackward() {
    _forward = false;
  }

  /**
   * Make this walker moving forward.
   */
  public void movesForward() {
    _forward = true;
  }

  /**
   * Move to the given location.
   *
   * @param walker A walker to go to.
   */
  public void moveTo(@NonNull final TreeWalker<Node> walker) {
    if (walker.getRoot() != _root) {
      throw new IllegalArgumentException(
          "Unable to moveTo to the given walker location because the given walker does not walk " +
              "throughout the same tree."
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
  public boolean isAtLocation(@NonNull final TreeWalker<Node> walker) {
    return walker.current() == current() && walker._cursors.equals(_cursors);
  }

  /**
   * @return True if this walker is at the end of the tree.
   */
  public boolean isAtEnd() {
    return _path.isEmpty() && _cursors.get(0) == 2;
  }

  /**
   * @return True if this walker is at the start of the tree.
   */
  public boolean isAtStart() {
    return _path.isEmpty() && _cursors.get(0) == 0;
  }

  /**
   * @return True if this walker moves movesForward.
   */
  public boolean doesMoveForward() {
    return _forward;
  }

  /**
   * Make this walker to move movesForward or movesBackward.
   *
   * @param forward True if this walker must move movesForward.
   */
  public void setMovingForward(final boolean forward) {
    _forward = forward;
  }

  /**
   * @return The root of the tree traversed by this walker.
   */
  public @Nullable Node getRoot() {
    return _elementClass.cast(_root);
  }

  /**
   * @param root The new root element of the tree to walk.
   */
  public void setRoot(@Nullable final Node root) {
    _root = root;

    if (_forward) {
      moveToStart();
    } else {
      moveToEnd();
    }
  }

  /**
   * @return The current path as a readonly collection.
   */
  public @NonNull List<@NonNull Node> getPath() {
    return _readonlyPath;
  }

  /**
   * @return The kind of element visited by this walker.
   */
  public @NonNull Class<Node> getElementClass() {
    return _elementClass;
  }
}
