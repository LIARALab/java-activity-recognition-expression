package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.implementation.StaticObjectBlueprint;
import org.liara.support.ListIndex;
import org.liara.support.view.View;

import java.util.Comparator;

public class StaticObjectBlueprintBuilder
  implements ObjectBlueprintBuilder
{
  @NonNull
  private final ListIndex<@NonNull String, @NonNull BlueprintElementBuilder> _index;

  @NonNull
  private final View<@NonNull BlueprintElementBuilder> _children;

  @NonNull
  private final View<@NonNull String> _keys;

  /**
   * Create a new static object blueprint builder.
   */
  public StaticObjectBlueprintBuilder () {
    this(16);
  }

  public StaticObjectBlueprintBuilder (@NonNegative final int capacity) {
    _index = new ListIndex<>(capacity, Comparator.comparing((@NonNull String x) -> x));
    _children = View.readonly(BlueprintElementBuilder.class, _index.getValues());
    _keys = View.readonly(String.class, _index.getKeys());
  }

  /**
   * @see ObjectBlueprintBuilder#build(BlueprintBuildingContext)
   */
  @Override
  public @NonNull StaticObjectBlueprint build (@NonNull final BlueprintBuildingContext context) {
    return new StaticObjectBlueprint(context, this);
  }

  /**
   * @see ListIndex#put(Object, Object) 
   */
  public void put (@NonNull final String name, @NonNull final BlueprintElementBuilder builder) {
    _index.put(name, builder);
  }

  /**
   * @see ListIndex#setValue(int, Object)
   */
  public void setValue (final int index, @NonNull final BlueprintElementBuilder builder) {
    _index.setValue(index, builder);
  }

  /**
   * @see ListIndex#setValue(Object, Object)
   */
  public void setValue (
    @NonNull final String name,
    @NonNull final BlueprintElementBuilder builder
  ) {
    _index.setValue(name, builder);
  }

  /**
   * @see ListIndex#setKey(Object, Object)
   */
  public void setKey (
    @NonNull final String from,
    @NonNull final String to
  ) {
    _index.setKey(from, to);
  }

  /**
   * @see ListIndex#setKey(int, Object)
   */
  public void setKey (
    final int index,
    @NonNull final String target
  ) {
    _index.setKey(index, target);
  }

  /**
   * @see ListIndex#remove(Object)
   */
  public void remove (@NonNull final String name) {
    _index.remove(name);
  }

  /**
   * @see ListIndex#remove(int)
   */
  public void remove (final int keyIndex) {
    _index.remove(keyIndex);
  }

  /**
   * @see ListIndex#clear()
   */
  public void clear () {
    _index.clear();
  }

  /**
   * @see ListIndex#getIndexOfKey(Object)
   */
  public @NonNegative int getIndexOfKey (@NonNull final String name) {
    return _index.getIndexOfKey(name);
  }

  /**
   * @see ListIndex#containsKey(Object)
   */
  public boolean containsKey (@NonNull final String name) {
    return _index.containsKey(name);
  }

  /**
   * @see ListIndex#getKey(int)
   */
  public @NonNull String getKey (@NonNegative final int index) {
    return _index.getKey(index);
  }

  /**
   * @see ListIndex#getValue(int)
   */
  public @NonNull BlueprintElementBuilder getValue (
    @NonNegative final int index
  ) {
    return _index.getValue(index);
  }

  /**
   * @see ListIndex#getValue(Object)
   */
  public @NonNull BlueprintElementBuilder getValue (@NonNull final String name) {
    return _index.getValue(name);
  }

  /**
   * @see ListIndex#getSize()
   */
  public int getSize () {
    return _index.getSize();
  }

  /**
   * @see BlueprintElementBuilder#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren () {
    return _children;
  }

  /**
   * @see ObjectBlueprintBuilder#getKeys()
   */
  @Override
  public @NonNull View<@NonNull String> getKeys () {
    return _keys;
  }
}
