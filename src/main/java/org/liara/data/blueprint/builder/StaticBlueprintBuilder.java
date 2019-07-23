package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.Blueprint;
import org.liara.data.blueprint.BlueprintElement;
import org.liara.data.blueprint.implementation.StaticBlueprint;
import org.liara.data.blueprint.implementation.StaticBlueprintContent;
import org.liara.data.primitive.Primitive;
import org.liara.support.tree.TreeWalker;

public class StaticBlueprintBuilder
{
  @NonNull
  private static final BlueprintElementBuilder DEFAULT_BLUEPRINT = new StaticNullBlueprintBuilder();

  @Nullable
  private BlueprintElementBuilder _blueprint;

  @NonNull
  private final TreeWalker<BlueprintElementBuilder> _walker;

  @NonNull
  private final StaticBlueprintBuildingContext _context;

  public StaticBlueprintBuilder () {
    _blueprint = null;
    _walker = new TreeWalker<>(BlueprintElementBuilder.class);
    _context = new StaticBlueprintBuildingContext();
  }

  public @NonNull Blueprint build () {
    @NonNull final BlueprintElementBuilder root = _blueprint == null ? DEFAULT_BLUEPRINT
                                                                     : _blueprint;
    @NonNull final StaticBlueprintContent content = new StaticBlueprintContent();
    @NonNull final Blueprint blueprint = new StaticBlueprint(content);

    _context.setBlueprint(blueprint);

    assignIdentifiers(root);

    _walker.setRoot(root);
    _walker.movesForward();

    content.getElements().add(root.build(_context));
    content.getParents().add(null);
    content.setRoot(content.getElements().get(0));

    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        @NonNull final BlueprintElementBuilder next = _walker.enter();
        @NonNull final BlueprintElement current = content.getElements().get(
          _context.getIdentifier(next)
        );

        for (int index = 0, size = next.getChildren().getSize(); index < size; ++index) {
          content.getElements().add(next.getChildren().get(index).build(_context));
          content.getParents().add(current);
        }
      }

      _walker.exit();
    }

    _context.clear();
    _walker.setRoot(null);

    return blueprint;
  }

  private void assignIdentifiers (@NonNull final BlueprintElementBuilder blueprint) {
    _walker.setRoot(blueprint);
    _walker.movesForward();

    _context.setIdentifier(blueprint, 0);
    @NonNegative int nextIdentifier = 1;

    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        @NonNull final BlueprintElementBuilder next = _walker.enter();

        for (int index = 0, size = next.getChildren().getSize(); index < size; ++index) {
          _context.setIdentifier(next.getChildren().get(index), nextIdentifier);
          nextIdentifier += 1;
        }
      }

      _walker.exit();
    }

    _walker.setRoot(null);
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<StaticBlueprintBuilder> describeObject () {
    @NonNull final ChainedStaticObjectBlueprintBuilder<StaticBlueprintBuilder> result = (
      new ChainedStaticObjectBlueprintBuilder<>(this)
    );

    _blueprint = result;

    return result;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<StaticBlueprintBuilder> describeTuple () {
    @NonNull final ChainedStaticTupleBlueprintBuilder<StaticBlueprintBuilder> result = (
      new ChainedStaticTupleBlueprintBuilder<>(this)
    );

    _blueprint = result;

    return result;
  }

  public @NonNull StaticBlueprintBuilder describeValue (@NonNull final Primitive<?> type) {
    @NonNull final StaticValueBlueprintBuilder result = new StaticValueBlueprintBuilder();
    result.setType(type);

    _blueprint = result;

    return this;
  }

  public @NonNull StaticBlueprintBuilder describeNull () {
    _blueprint = new StaticNullBlueprintBuilder();
    return this;
  }

  public @NonNull StaticBlueprintBuilder clear () {
    _blueprint = null;
    return this;
  }

  public @Nullable BlueprintElementBuilder getBlueprint () {
    return _blueprint;
  }

  public void setBlueprint (@Nullable final BlueprintElementBuilder blueprint) {
    _blueprint = blueprint;
  }
}
