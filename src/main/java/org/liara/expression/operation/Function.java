package org.liara.expression.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;
import org.liara.support.view.View;

/**
 * A functional expression.
 */
public final class Function<Result> implements Expression<Result> {
  @NonNull
  private final String _name;

  @NonNull
  private final Primitive<Result> _type;

  @NonNull
  private final View<@NonNull ? extends Expression<?>> _operands;

  public Function (
      @NonNull final Primitive<Result> type,
      @NonNull final String name,
      @NonNull final Expression<?> ...operands
  ) {
    _type = type;
    _name = name;
    _operands = View.readonly(operands);
  }

  public Function (
      @NonNull final Primitive<Result> type,
      @NonNull final String name,
      @NonNull final Collection<@NonNull ? extends Expression<?>> operands
  ) {
    _type = type;
    _name = name;
    _operands = View.readonly(new ArrayList<>(operands));
  }

  public Function (
      @NonNull final Primitive<Result> type,
      @NonNull final String name,
      @NonNull final Iterator<@NonNull ? extends Expression<?>> operands
  ) {
    @NonNull final List<@NonNull Expression<?>> buffer = new LinkedList<>();
    operands.forEachRemaining(buffer::add);

    _type = type;
    _name = name;
    _operands = View.readonly(new ArrayList<>(buffer));
  }

  public Function (
      @NonNull final Primitive<Result> type,
      @NonNull final String name,
      @NonNull final View<@NonNull ? extends Expression<?>> operands
  ) {
    _type = type;
    _name = name;
    _operands = View.readonly(operands.stream().collect(Collectors.toList()));
  }

  public Function (@NonNull final Function<Result> toCopy) {
    _type = toCopy.getResultType();
    _name = toCopy.getName();
    _operands = toCopy.getChildren();
  }

  public @NonNull String getName() {
    return _name;
  }

  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _type;
  }

  @Override
  public @NonNull View<? extends Expression<?>> getChildren() {
    return _operands;
  }

  @Override
  public boolean equals(@Nullable final Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (other instanceof Function) {
      @NonNull final  Function<?> otherFunction = (Function<?>) other;

      return Objects.equals(
          _name, otherFunction.getName()
      ) && Objects.equals(
          _type, otherFunction.getResultType()
      ) && Objects.equals(
          _operands, otherFunction.getChildren()
      );
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(_name, _type, _operands);
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _type.getName() + " " + _name + " " +
        _operands.toString() + " }";
  }
}
