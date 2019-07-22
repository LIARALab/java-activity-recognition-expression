package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

import java.util.Arrays;
import java.util.List;

public class StaticObjectModel extends StaticModelElement implements ObjectModel
{
  @NonNull
  private final View<@NonNull ModelElement> _children;

  @NonNull
  private final View<@NonNull String> _keys;

  @NonNegative
  private final int _offset;

  private final int[] _fieldsOfChildren;

  @NonNull
  private final String[] _orderedKeys;

  private final int[] _fieldsOfKeys;

  private StaticObjectModel (
    @NonNull final ModelElementBuildingContext context,
    @NonNull final String[] fields,
    @NonNull final ModelElement[] children
  ) {
    super(context);
    _children = View.readonly(ModelElement.class, children);
    _keys  = View.readonly(String.class, fields);

    @NonNegative final int[] boundaries = computeChildrenBoundaries();

    _offset = boundaries[0];
    _fieldsOfChildren = new int[boundaries[1] - boundaries[0] + 1];

    computeFieldsOfChildren();

    _orderedKeys = Arrays.copyOf(fields, fields.length);
    _fieldsOfKeys = new int[fields.length];

    computeFieldsOfKey();
  }

  public StaticObjectModel (
    @NonNull final ModelElementBuildingContext context,
    @NonNull final List<@NonNull String> fields,
    @NonNull final List<@NonNull ModelElement> children
  ) {
    this(context, fields.toArray(new String[0]), children.toArray(new ModelElement[0]));
  }

  public StaticObjectModel (
    @NonNull final ModelElementBuildingContext context,
    @NonNull final View<@NonNull String> fields,
    @NonNull final View<@NonNull ModelElement> children
  ) {
    this(context, fields.toArray(), children.toArray());
  }

  private int compareFieldKey (@NonNegative final Integer left, @NonNegative final Integer right) {
    return _keys.get(left).compareTo(_keys.get(right));
  }

  private void computeFieldsOfKey () {
    Arrays.sort(_orderedKeys);

    for (@NonNegative int index = 0, size = _keys.getSize(); index < size; ++index) {
      _fieldsOfKeys[Arrays.binarySearch(_orderedKeys, _keys.get(index))] = index;
    }
  }

  private void computeFieldsOfChildren () {
    Arrays.fill(_fieldsOfChildren, -1);

    for (@NonNegative int index = 0, size = _children.getSize(); index < size; ++index) {
      _fieldsOfChildren[_children.get(index).getIdentifier() - _offset] = index;
    }
  }

  private @NonNegative int[] computeChildrenBoundaries () {
    @NonNegative final int[] result = new int[] { Integer.MAX_VALUE, Integer.MIN_VALUE};

    for (@NonNegative int index = 0, size = _children.getSize(); index < size; ++index) {
      result[0] = Math.min(result[0], _children.get(index).getIdentifier());
      result[1] = Math.max(result[1], _children.get(index).getIdentifier());
    }

    return result;
  }

  /**
   * @see ObjectModel#getKeys()
   */
  @Override
  public @NonNull View<@NonNull String> getKeys () {
    return _keys;
  }

  /**
   * @see ObjectModel#getFieldOf(ModelElement)
   */
  @Override
  public int getFieldOf (@NonNull final ModelElement value) {
    if (value.getModel() != getModel()) return -1;

    int index = value.getIdentifier() - _offset;

    return index >= 0 && index < _fieldsOfChildren.length ? _fieldsOfChildren[index] : -1;
  }

  /**
   * @see ObjectModel#getFieldOf(String)
   */
  @Override
  public int getFieldOf (@NonNull final String key) {
    int index = Arrays.binarySearch(_orderedKeys, key);

    return index < 0 ? - 1 : _fieldsOfKeys[index];
  }

  /**
   * @see ModelElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElement> getChildren () {
    return _children;
  }
}
