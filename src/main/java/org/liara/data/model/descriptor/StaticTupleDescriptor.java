package org.liara.data.model.descriptor;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

import java.util.*;

public class StaticTupleDescriptor
  implements TupleDescriptor
{
  @NonNull
  private final List<@NonNull ModelElementDescriptor> _children;

  @NonNull
  private final View<@NonNull ModelElementDescriptor> _childrenView;

  public StaticTupleDescriptor () {
    _children = new ArrayList<>(4);
    _childrenView = View.readonly(ModelElementDescriptor.class, _children);
  }

  /**
   * @see ModelElementDescriptor#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElementDescriptor> getChildren () {
    return _childrenView;
  }

  /**
   * Change the list of children of this tuple.
   *
   * @param children An iterator over an ordered sequence of children to set.
   */
  public void setChildren (@NonNull final Iterator<@NonNull ModelElementDescriptor> children) {
    _children.clear();
    children.forEachRemaining(_children::add);
  }

  /**
   * Change the list of children of this tuple.
   *
   * @param children An ordered collection of children to set.
   */
  public void setChildren (@NonNull final Collection<@NonNull ModelElementDescriptor> children) {
    _children.clear();
    _children.addAll(children);
  }

  /**
   * Add a child element at the end of this tuple.
   *
   * @param descriptor A child element to add to this tuple.
   */
  public void append (@NonNull final ModelElementDescriptor descriptor) {
    _children.add(descriptor);
  }

  /**
   * Insert a child element into this tuple.
   *
   * @param index Index of the location where to insert the child element.
   * @param descriptor Child element to insert.
   */
  public void insert (
    @NonNegative final int index,
    @NonNull final ModelElementDescriptor descriptor
  ) {
    _children.add(index, descriptor);
  }

  /**
   * Remove the nth child of this tuple.
   *
   * @param index Index of the child to remove.
   */
  public void remove (@NonNegative final int index) {
    _children.remove(index);
  }

  /**
   * Clear this tuple of all of its children.
   */
  public void clear () {
    _children.clear();
  }
}
