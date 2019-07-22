package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

import java.util.Arrays;
import java.util.List;

public class StaticTupleModel extends StaticModelElement implements TupleModel
{
  @NonNull
  private final View<@NonNull ModelElement> _children;

  private final int[] _indexes;

  @NonNegative
  private final int _offset;

  /**
   * Create a new static tuple model.
   *
   * @param context The building context to use for creating this tuple model.
   * @param children A list of children element of the tuple model to create.
   */
  public StaticTupleModel (
    @NonNull final ModelElementBuildingContext context,
    @NonNull final List<@NonNull ModelElement> children
  ) {
    super(context);

    _children = View.readonly(ModelElement.class, children.toArray(new ModelElement[0]));

    @NonNegative final int[] boundaries = getChildIdentifierBoundaries();

    _offset = boundaries[0];
    _indexes = new int[boundaries[1] - boundaries[0] + 1];

    Arrays.fill(_indexes, -1);

    for (int index = 0, size = _children.getSize(); index < size; ++index) {
      _indexes[_children.get(index).getIdentifier() - _offset] = index;
    }
  }

  /**
   * @return The boundaries of all identifiers of all children element of this tuple.
   */
  private @NonNegative int[] getChildIdentifierBoundaries () {
    @NonNegative int[] result = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};

    for (int index = 0, size = _children.getSize(); index < size; ++index) {
      @NonNegative final int elementIdentifier = _children.get(index).getIdentifier();

      result[0] = Math.min(result[0], elementIdentifier);
      result[1] = Math.max(result[1], elementIdentifier);
    }

    return result;
  }

  /**
   * @see TupleModel#getIndexOf(ModelElement)
   */
  @Override
  public @NonNegative int getIndexOf (@NonNull final ModelElement value) {
    if (value.getModel() != getModel()) return -1;

    @NonNegative final int index = value.getIdentifier() - _offset;

    return index >= 0 && index < _indexes.length ? _indexes[index] : - 1;
  }

  /**
   * @see ModelElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull ModelElement> getChildren () {
    return _children;
  }
}
