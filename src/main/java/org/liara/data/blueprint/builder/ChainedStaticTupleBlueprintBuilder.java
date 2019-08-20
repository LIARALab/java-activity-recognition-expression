package org.liara.data.blueprint.builder;

import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.implementation.StaticTupleBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class ChainedStaticTupleBlueprintBuilder<Parent>
    implements BlueprintElementBuilder {

  @NonNull
  private final Parent _parent;

  @NonNull
  private final StaticTupleBlueprintBuilder _builder;

  public ChainedStaticTupleBlueprintBuilder(@NonNull final Parent parent) {
    _parent = parent;
    _builder = new StaticTupleBlueprintBuilder();
  }

  @Override
  public @NonNull StaticTupleBlueprint build(final @NonNull BlueprintBuildingContext context) {
    return new StaticTupleBlueprint(context, this);
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> append(
      @NonNull final BlueprintElementBuilder builder
  ) {
    _builder.append(builder);
    return this;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> appendValue(
      @NonNull final Primitive<?> type
  ) {
    @NonNull final StaticValueBlueprintBuilder builder = new StaticValueBlueprintBuilder();
    builder.setType(type);

    return append(builder);
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> appendNull() {
    return append(new StaticNullBlueprintBuilder());
  }

  public
  @NonNull ChainedStaticTupleBlueprintBuilder<ChainedStaticTupleBlueprintBuilder<Parent>>
  appendTuple() {
    @NonNull final ChainedStaticTupleBlueprintBuilder<ChainedStaticTupleBlueprintBuilder<Parent>> builder = (
        new ChainedStaticTupleBlueprintBuilder<>(this)
    );

    append(builder);

    return builder;
  }

  public
  @NonNull ChainedStaticObjectBlueprintBuilder<ChainedStaticTupleBlueprintBuilder<Parent>>
  appendObject() {
    @NonNull final ChainedStaticObjectBlueprintBuilder<ChainedStaticTupleBlueprintBuilder<Parent>> builder = (
        new ChainedStaticObjectBlueprintBuilder<>(this)
    );

    append(builder);

    return builder;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> append(
      @NonNegative final int index,
      @NonNull final BlueprintElementBuilder child
  ) {
    _builder.append(index, child);
    return this;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> set(
      @NonNull final Iterator<@NonNull BlueprintElementBuilder> children
  ) {
    _builder.set(children);
    return this;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> set(
      @NonNull final Collection<@NonNull BlueprintElementBuilder> children
  ) {
    _builder.set(children);
    return this;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> remove(
      @NonNegative final int index
  ) {
    _builder.remove(index);
    return this;
  }

  public @NonNull ChainedStaticTupleBlueprintBuilder<Parent> clear() {
    _builder.clear();
    return this;
  }

  public @NonNull Parent endTuple() {
    return _parent;
  }

  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren() {
    return _builder.getChildren();
  }
}
