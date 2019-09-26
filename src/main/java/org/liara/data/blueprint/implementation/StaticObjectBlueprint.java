package org.liara.data.blueprint.implementation;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.ObjectBlueprint;
import org.liara.data.blueprint.builder.BlueprintBuildingContext;
import org.liara.data.blueprint.builder.ObjectBlueprintBuilder;
import org.liara.support.view.View;
import org.liara.support.view.primitive.PrimitiveView;

public class StaticObjectBlueprint
    extends StaticBlueprintElement
    implements ObjectBlueprint {

  private final int[] _children;

  @NonNull
  private final View<@NonNull ? extends BlueprintElement> _childrenView;

  @NonNull
  private final View<@NonNull ? extends String> _keys;

  @NonNegative
  private final int _offset;

  private final int[] _fieldsOfChildren;

  @NonNull
  private final String[] _orderedKeys;

  private final int[] _fieldsOfKeys;

  /**
   * Create a new blueprint element from a given builder and a given context.
   *
   * @param context A context to use for instantiating blueprint element.
   * @param builder A builder to use for instantiating blueprint element.
   */
  public StaticObjectBlueprint(
      @NonNull final BlueprintBuildingContext context,
      @NonNull final ObjectBlueprintBuilder builder
  ) {
    super(context, builder);
    _children = new int[builder.getChildren().getSize()];

    for (int index = 0, size = builder.getChildren().getSize(); index < size; ++index) {
      _children[index] = context.getIdentifier(builder.getChildren().get(index));
    }

    _childrenView = PrimitiveView.readonly(_children).map(getBlueprint().getElements()::get);
    _keys = View.readonly(builder.getKeys().stream().collect(Collectors.toList()));

    @NonNegative final int[] boundaries = computeChildrenBoundaries();

    _offset = boundaries[0];
    _fieldsOfChildren = new int[boundaries[1] - boundaries[0] + 1];

    computeFieldsOfChildren();

    _orderedKeys = _keys.stream().toArray(String[]::new);
    _fieldsOfKeys = new int[_orderedKeys.length];

    computeFieldsOfKey();
  }

  private int compareFieldKey(@NonNegative final Integer left, @NonNegative final Integer right) {
    return _keys.get(left).compareTo(_keys.get(right));
  }

  private void computeFieldsOfKey() {
    Arrays.sort(_orderedKeys);

    for (@NonNegative int index = 0, size = _keys.getSize(); index < size; ++index) {
      _fieldsOfKeys[Arrays.binarySearch(_orderedKeys, _keys.get(index))] = index;
    }
  }

  private void computeFieldsOfChildren() {
    Arrays.fill(_fieldsOfChildren, -1);

    for (@NonNegative int index = 0, size = _children.length; index < size; ++index) {
      _fieldsOfChildren[_children[index] - _offset] = index;
    }
  }

  private @NonNegative int[] computeChildrenBoundaries() {
    if (_children.length == 0) {
      return new int[]{0, 0};
    }

    @NonNegative final int[] result = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

    for (@NonNegative int index = 0, size = _children.length; index < size; ++index) {
      result[0] = Math.min(result[0], _children[index]);
      result[1] = Math.max(result[1], _children[index]);
    }

    return result;
  }

  /**
   * @see ObjectBlueprint#getKeys()
   */
  @Override
  public @NonNull View<@NonNull ? extends String> getKeys() {
    return _keys;
  }

  /**
   * @see ObjectBlueprint#getFieldOf(BlueprintElement)
   */
  @Override
  public int getFieldOf(@NonNull final BlueprintElement value) {
    if (value.getBlueprint() != getBlueprint()) {
      return -1;
    }

    int index = value.getIdentifier() - _offset;

    return index >= 0 && index < _fieldsOfChildren.length ? _fieldsOfChildren[index] : -1;
  }

  /**
   * @see ObjectBlueprint#getFieldOf(String)
   */
  @Override
  public int getFieldOf(@NonNull final String key) {
    int index = Arrays.binarySearch(_orderedKeys, key);

    return index < 0 ? -1 : _fieldsOfKeys[index];
  }

  /**
   * @see BlueprintElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ? extends BlueprintElement> getChildren() {
    return _childrenView;
  }
}
