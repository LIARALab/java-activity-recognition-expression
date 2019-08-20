package org.liara.data.blueprint.implementation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.blueprint.ValueBlueprint;
import org.liara.data.blueprint.builder.BlueprintBuildingContext;
import org.liara.data.blueprint.builder.StaticValueBlueprintBuilder;
import org.liara.data.primitive.Primitive;

public class StaticValueBlueprint
    extends StaticBlueprintElement
    implements ValueBlueprint {

  @NonNull
  private final Primitive _type;

  /**
   * Create a new blueprint element from a given builder and a given context.
   *
   * @param context A context to use for instantiating blueprint element.
   * @param builder A builder to use for instantiating blueprint element.
   */
  public StaticValueBlueprint(
      @NonNull final BlueprintBuildingContext context,
      @NonNull final StaticValueBlueprintBuilder builder
  ) {
    super(context, builder);

    _type = Objects.requireNonNull(builder.getType());
  }

  /**
   * @see ValueBlueprint#getType()
   */
  @Override
  public @NonNull Primitive<?> getType() {
    return _type;
  }
}
