package org.liara.expression;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.support.view.View;

public class Identifier implements Expression<@NonNull String> {
  @NonNull
  private final static View<@NonNull Expression> CHILDREN = View.readonly(Expression.class);

  private final String _name;

  public Identifier (@NonNull final String name) {
    _name = name;
  }

  @Override
  public @NonNull Primitive<@NonNull String> getResultType() {
    return Primitives.STRING;
  }

  @Override
  public @NonNull View<@NonNull Expression> getChildren() {
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

    if (other instanceof Identifier) {
      @NonNull final Identifier otherIdentifier = (Identifier) other;

      return Objects.equals(_name, otherIdentifier.getName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(_name);
  }

  @Override
  public @NonNull String toString() {
    return super.toString() + " { " + _name + " }";
  }
}
