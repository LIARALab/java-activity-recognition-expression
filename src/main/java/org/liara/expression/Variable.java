package org.liara.expression;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.support.view.View;

public final class Variable<Result> implements Expression<Result> {
  @NonNull
  private final static View<@NonNull ? extends Expression<?>> CHILDREN = View.empty();

  @NonNull
  private final String _name;

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Instantiate a new identifier.
   * 
   * @param name Identifier.
   */
  public Variable(@NonNull final Primitive<Result> type, @NonNull final String name) {
    _type = type;
    _name = name;
  }

  public Variable(@NonNull final Variable<Result> toCopy) {
    _type = toCopy.getResultType();
    _name = toCopy.getName();
  }

  @Override
  public @NonNull Primitive<@NonNull Result> getResultType() {
    return _type;
  }

  @Override
  public @NonNull View<@NonNull ? extends Expression<?>> getChildren() {
    return CHILDREN;
  }

  public @NonNull String getName() {
    return _name;
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof Variable) {
      @NonNull final Variable otherVariable = (Variable) other;

      return Objects.equals(_name, otherVariable.getName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(_name);
  }

  @Override
  public @NonNull String toString() {
    return super.toString() + " { " + _type.getName() + " " + _name + " }";
  }
}
