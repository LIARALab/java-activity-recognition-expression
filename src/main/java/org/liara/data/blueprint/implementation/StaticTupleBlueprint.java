package org.liara.data.blueprint.implementation;

import java.util.Arrays;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.TupleBlueprint;
import org.liara.data.blueprint.builder.BlueprintBuildingContext;
import org.liara.data.blueprint.builder.BlueprintElementBuilder;
import org.liara.support.view.View;

public class StaticTupleBlueprint
    extends StaticBlueprintElement
    implements TupleBlueprint {

  private final int[] _children;

  @NonNull
  private final View<@NonNull BlueprintElement> _view;

  @NonNegative
  private final int _offset;

  private final int[] _indexes;

  /**
   * Create a new blueprint element from a given builder and a given context.
   *
   * @param context A context to use for instantiating blueprint element.
   * @param builder A builder to use for instantiating blueprint element.
   */
  public StaticTupleBlueprint(
      @NonNull final BlueprintBuildingContext context,
      @NonNull final BlueprintElementBuilder builder
  ) {
    super(context, builder);

    _children = new int[builder.getChildren().getSize()];
    _view = View.readonly(_children).map(
        BlueprintElement.class, getBlueprint().getElements()::get
    );

    for (int index = 0, size = builder.getChildren().getSize(); index < size; ++index) {
      _children[index] = context.getIdentifier(builder.getChildren().get(index));
    }

    @NonNegative final int[] boundaries = computeChildrenBoundaries();

    _offset = boundaries[0];
    _indexes = new int[boundaries[1] - boundaries[0] + 1];

    Arrays.fill(_indexes, -1);

    for (int index = 0, size = _children.length; index < size; ++index) {
      _indexes[_children[index] - _offset] = index;
    }
  }

  /**
   * @return The boundaries of all identifiers of all children element of this tuple.
   */
  private @NonNegative int[] computeChildrenBoundaries() {
    if (_children.length == 0) {
      return new int[]{0, 0};
    }

    @NonNegative int[] result = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

    for (int index = 0, size = _children.length; index < size; ++index) {
      @NonNegative final int elementIdentifier = _children[index];

      result[0] = Math.min(result[0], elementIdentifier);
      result[1] = Math.max(result[1], elementIdentifier);
    }

    return result;
  }

  /**
   * @see TupleBlueprint#getIndexOf(BlueprintElement)
   */
  @Override
  public @NonNegative int getIndexOf(@NonNull final BlueprintElement value) {
    if (value.getBlueprint() != getBlueprint()) {
      return -1;
    }

    @NonNegative final int index = value.getIdentifier() - _offset;

    return index >= 0 && index < _indexes.length ? _indexes[index] : -1;
  }

  /**
   * @see BlueprintElement#getChildren()
   */
  @Override
  public @NonNull View<@NonNull BlueprintElement> getChildren() {
    return _view;
  }
}
