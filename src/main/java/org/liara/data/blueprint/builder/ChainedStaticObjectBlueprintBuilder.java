package org.liara.data.blueprint.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.implementation.StaticObjectBlueprint;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;

public class ChainedStaticObjectBlueprintBuilder<Parent>
    implements ObjectBlueprintBuilder {

  @NonNull
  private final Parent _parent;

  @NonNull
  private final StaticObjectBlueprintBuilder _builder;

  public ChainedStaticObjectBlueprintBuilder(@NonNull final Parent parent) {
    _parent = parent;
    _builder = new StaticObjectBlueprintBuilder();
  }

  @Override
  public @NonNull StaticObjectBlueprint build(@NonNull final BlueprintBuildingContext context) {
    return new StaticObjectBlueprint(context, this);
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> put(
      final @NonNull String name,
      final @NonNull BlueprintElementBuilder builder
  ) {
    _builder.put(name, builder);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> setValue(
      final int index,
      final @NonNull BlueprintElementBuilder builder
  ) {
    _builder.setValue(index, builder);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> setValue(
      final @NonNull String name,
      final @NonNull BlueprintElementBuilder builder
  ) {
    _builder.setValue(name, builder);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> setKey(
      final @NonNull String from,
      final @NonNull String to
  ) {
    _builder.setKey(from, to);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> setKey(
      final int index,
      final @NonNull String target
  ) {
    _builder.setKey(index, target);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> remove(final @NonNull String name) {
    _builder.remove(name);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> remove(final int keyIndex) {
    _builder.remove(keyIndex);
    return this;
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> clear() {
    _builder.clear();
    return this;
  }

  public @NonNegative int getIndexOfKey(final @NonNull String name) {
    return _builder.getIndexOfKey(name);
  }

  public boolean containsKey(final @NonNull String name) {
    return _builder.containsKey(name);
  }

  @NonNull
  public String getKey(@NonNegative final int index) {
    return _builder.getKey(index);
  }

  @NonNull
  public BlueprintElementBuilder getValue(
      @NonNegative final int index
  ) {
    return _builder.getValue(index);
  }

  @NonNull
  public BlueprintElementBuilder getValue(final @NonNull String name) {
    return _builder.getValue(name);
  }

  public int getSize() {
    return _builder.getSize();
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> appendValue(
      @NonNull final String name,
      @NonNull final Primitive<?> type
  ) {
    @NonNull final StaticValueBlueprintBuilder builder = new StaticValueBlueprintBuilder();
    builder.setType(type);

    return put(name, builder);
  }

  public @NonNull ChainedStaticObjectBlueprintBuilder<Parent> appendNull(
      @NonNull final String name) {
    return put(name, new StaticNullBlueprintBuilder());
  }

  public
  @NonNull ChainedStaticTupleBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>>
  appendTuple(@NonNull final String name) {
    @NonNull final ChainedStaticTupleBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>> builder = (
        new ChainedStaticTupleBlueprintBuilder<>(this)
    );

    put(name, builder);

    return builder;
  }

  public
  @NonNull ChainedStaticObjectBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>>
  appendObject(@NonNull final String name) {
    @NonNull final ChainedStaticObjectBlueprintBuilder<ChainedStaticObjectBlueprintBuilder<Parent>> builder = (
        new ChainedStaticObjectBlueprintBuilder<>(this)
    );

    put(name, builder);

    return builder;
  }

  @Override
  public @NonNull View<@NonNull ? extends String> getKeys() {
    return _builder.getKeys();
  }

  @Override
  public @NonNull View<@NonNull ? extends BlueprintElementBuilder> getChildren() {
    return _builder.getChildren();
  }

  public @NonNull Parent endObject() {
    return _parent;
  }
}
