package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.implementation.StaticObjectBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class ChainedStaticObjectBlueprintBuilder<Parent>
  implements ObjectBlueprintBuilder
{
  @NonNull
  private final Parent _parent;

  @NonNull
  private final StaticObjectBlueprintBuilder _builder;

  public ChainedStaticObjectBlueprintBuilder (@NonNull final Parent parent) {
    _parent = parent;
    _builder = new StaticObjectBlueprintBuilder();
  }

  @Override
  public @NonNull StaticObjectBlueprint build (@NonNull final BlueprintBuildingContext context) {
    return new StaticObjectBlueprint(context, this);
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> append (
    @NonNull final String name,
    @NonNull final BlueprintElementBuilder builder
  ) {
    _builder.append(name, builder);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> appendValue (
    @NonNull final String name,
    @NonNull final Primitive<?> type
  ) {
    @NonNull final StaticValueBlueprintBuilder builder = new StaticValueBlueprintBuilder();
    builder.setType(type);

    return append(name, builder);
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> appendNull (@NonNull final String name) {
    return append(name, new StaticNullBlueprintBuilder());
  }

  public
  @NonNull ChainedStaticTupleBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>>
  appendTuple (@NonNull final String name) {
    @NonNull
    final ChainedStaticTupleBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>> builder = (
      new ChainedStaticTupleBlueprintBuilder<>(this)
    );

    append(name, builder);

    return builder;
  }

  public
  @NonNull ChainedStaticObjectBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>>
  appendObject (@NonNull final String name) {
    @NonNull
    final ChainedStaticObjectBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>> builder = (
      new ChainedStaticObjectBlueprintBuilder<>(this)
    );

    append(name, builder);

    return builder;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> set (
    @NonNull final String name,
    @NonNull final BlueprintElementBuilder builder
  ) {
    _builder.set(name, builder);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> rename (
    @NonNull final String oldName,
    @NonNull final String newName
  ) {
    _builder.rename(oldName, newName);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> rename (
    @NonNegative final int field,
    @NonNull final String name
  ) {
    _builder.rename(field, name);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> remove (@NonNull final String key) {
    _builder.remove(key);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> remove (@NonNegative final int index) {
    _builder.remove(index);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> clear () {
    _builder.clear();
    return this;
  }

  public boolean containsKey (@NonNull final String key) {
    return _builder.containsKey(key);
  }

  public @NonNegative int getFieldOf (@NonNull final String key) {
    return _builder.getFieldOf(key);
  }

  @Override
  public @NonNull View<@NonNull String> getKeys () {
    return _builder.getKeys();
  }

  @Override
  public @NonNull View<@NonNull BlueprintElementBuilder> getChildren () {
    return _builder.getChildren();
  }
  public @NonNull Parent endObject () {
    return _parent;
  }
}
