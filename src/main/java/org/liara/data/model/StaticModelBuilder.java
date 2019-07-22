package org.liara.data.model;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.model.descriptor.*;
import org.liara.support.tree.TreeWalker;
import org.liara.support.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticModelBuilder
{
  @Nullable
  private ModelElementDescriptor _root;

  @NonNegative
  private int _nextIdentifier;

  @NonNull
  private final Map<@NonNull ModelElementDescriptor, @NonNull ModelElement> _lookup;

  @NonNull
  private final TreeWalker<ModelElementDescriptor> _walker;

  public StaticModelBuilder () {
    _root = null;
    _walker = new TreeWalker<>(ModelElementDescriptor.class);
    _lookup = new HashMap<>();
    _nextIdentifier = 0;
  }

  public @NonNull Model build () {
    initialize(_root == null ? StaticNullDescriptor.INSTANCE : _root);

    @NonNull final StaticModelData                   data    = new StaticModelData(count());
    @NonNull final Model                             model   = new StaticModel(data);
    @NonNull final StaticModelElementBuildingContext context = new StaticModelElementBuildingContext(model);

    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        enteringDescriptor(_walker.enter());
      }

      @NonNull final ModelElementDescriptor origin = _walker.exit();

      context.setIdentifier(_nextIdentifier);

      @NonNull final ModelElement result = exitingDescritpor(context, origin);

      data.registerElement(_nextIdentifier, result);

      @NonNull final View<@NonNull ModelElement> children = result.getChildren();

      for (@NonNegative int index = 0, size = children.getSize(); index < size; ++index) {
          data.registerParent(children.get(index).getIdentifier(), _nextIdentifier);
      }

      _lookup.put(origin, result);
      _nextIdentifier += 1;
    }

    data.setRoot(_lookup.entrySet().iterator().next().getValue());
    _lookup.clear();

    terminate();

    return model;
  }

  private void initialize (@NonNull final ModelElementDescriptor root) {
    _walker.setRoot(root);
  }

  private @NonNegative int count () {
    @NonNegative int result = 0;

    _walker.moveToStart();
    _walker.movesForward();

    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        _walker.enter();
        result += 1;
      }

      _walker.exit();
    }

    _walker.moveToStart();

    return result;
  }

  private void enteringDescriptor (@NonNull final ModelElementDescriptor enter) {
  }

  private @NonNull ModelElement exitingDescritpor (
    @NonNull final StaticModelElementBuildingContext context,
    @NonNull final ModelElementDescriptor descriptor
  ) {
    if (descriptor instanceof ValueDescriptor) {
      return buildStaticValueModel(context, (StaticValueDescriptor) descriptor);
    } else if (descriptor instanceof TupleDescriptor) {
      return buildStaticTupleModel(context, (TupleDescriptor) descriptor);
    } else if (descriptor instanceof ObjectDescriptor) {
      return buildStaticObjectModel(context, (ObjectDescriptor) descriptor);
    } else if (descriptor instanceof NullDescriptor) {
      return buildStaticNullModel(context, (NullDescriptor) descriptor);
    }

    throw new IllegalArgumentException(
      "Unable to build the model element descriptor " + descriptor + " instance of " +
      descriptor.getClass().toString() + " because the given class of descriptor is not handled " +
      "by this builder."
    );
  }

  private @NonNull ArrayList<@NonNull ModelElement> _objectElements = new ArrayList<>();

  private @NonNull View<@NonNull ModelElement> _objectElementsView = View.readonly(
    ModelElement.class, _objectElements
  );

  private @NonNull ModelElement buildStaticObjectModel (
    @NonNull final StaticModelElementBuildingContext context,
    @NonNull final ObjectDescriptor descriptor
  ) {
    @NonNull final View<@NonNull String> keys = descriptor.getKeys();
    @NonNull final View<@NonNull ModelElementDescriptor> children = descriptor.getChildren();

    for (int index = 0, size = children.getSize(); index < size; ++index) {
      _objectElements.add(_lookup.get(children.get(index)));
      _lookup.remove(children.get(index));
    }

    @NonNull final StaticObjectModel model = new StaticObjectModel(
      context, keys, _objectElementsView
    );

    _objectElements.clear();

    return model;
  }

  private @NonNull ArrayList<@NonNull ModelElement> _tupleElements = new ArrayList<>();

  private @NonNull ModelElement buildStaticTupleModel (
    @NonNull final StaticModelElementBuildingContext context,
    @NonNull final TupleDescriptor descriptor
  ) {
    @NonNull final View<@NonNull ModelElementDescriptor> children = descriptor.getChildren();

    for (int index = 0, size = children.getSize(); index < size; ++index) {
      _tupleElements.add(_lookup.get(children.get(index)));
      _lookup.remove(children.get(index));
    }

    @NonNull final StaticTupleModel model = new StaticTupleModel(context, _tupleElements);

    _tupleElements.clear();

    return model;
  }

  private @NonNull ModelElement buildStaticNullModel (
    @NonNull final StaticModelElementBuildingContext context,
    @NonNull final NullDescriptor descriptor
  ) {
    return new StaticNullModel(context);
  }

  private @NonNull ModelElement buildStaticValueModel (
    @NonNull final StaticModelElementBuildingContext context,
    @NonNull final StaticValueDescriptor descriptor
  ) {
    return new StaticValueModel(context, descriptor.getType());
  }

  private void terminate () {
    _walker.setRoot(null);
    _lookup.clear();
    _nextIdentifier = 0;
  }

  public @NonNull ModelElementDescriptor getDescriptor () {
    return _root;
  }

  public void setDescriptor (@NonNull final ModelElementDescriptor root) {
    _root = root;
  }
}
