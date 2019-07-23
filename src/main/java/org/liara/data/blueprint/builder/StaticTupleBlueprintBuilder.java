package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.implementation.StaticTupleBlueprint;
import org.liara.support.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StaticTupleBlueprintBuilder implements BlueprintElementBuilder
{
  @NonNull
  private final List<@NonNull BlueprintElementBuilder> _children;

  @NonNull
  private final View<@NonNull BlueprintElementBuilder> _view;

  public StaticTupleBlueprintBuilder () {
    this(4);
  }

  public StaticTupleBlueprintBuilder (@NonNegative final int capacity) {
    _children = new ArrayList<>(capacity);
    _view = View.readonly(BlueprintElementBuilder.class, _children);
  }

  @Override
  public @NonNull StaticTupleBlueprint build (@NonNull final BlueprintBuildingContext context) {
    return new StaticTupleBlueprint(context, this);
  }

  /**
   * @see BlueprintElementBuilder#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren () {
    return _view;
  }


  /**
   * Append a child element to this tuple.
   *
   * @param builder A builder of the child element to append.
   */
  public void append (@NonNull final BlueprintElementBuilder builder) {
    _children.add(builder);
  }

  /**
   * Insert a child element into this tuple.
   *
   * @param index Index of the location where to insert the child element.
   * @param child Child element to insert.
   */
  public void append (@NonNegative final int index, @NonNull final BlueprintElementBuilder child) {
    _children.add(index, child);
  }

  /**
   * Change the list of children element of the tuple to build.
   *
   * @param children An iterator over an ordered sequence of children to set.
   */
  public void set (@NonNull final Iterator<@NonNull BlueprintElementBuilder> children) {
    _children.clear();
    children.forEachRemaining(_children::add);
  }

  /**
   * Change the list of children element of the tuple to build.
   *
   * @param children An ordered collection of children to set.
   */
  public void set (@NonNull final Collection<@NonNull BlueprintElementBuilder> children) {
    _children.clear();
    _children.addAll(children);
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
