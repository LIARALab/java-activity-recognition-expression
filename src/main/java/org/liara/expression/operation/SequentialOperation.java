package org.liara.expression.operation;

import java.util.ArrayList;
import java.util.Arrays;
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

public final class SequentialOperation<Result> implements Operation<Result> {
  @NonNull
  private final View<@NonNull ? extends Expression<?>> _operands;

  @NonNull
  private final Operator _operator;

  @NonNull
  private final Primitive<Result> _type;

  public SequentialOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Expression<?> ...operands
  ) {
    _type = type;
    _operator = operator;
    _operands = View.readonly(Arrays.copyOf(operands, operands.length));
  }

  public SequentialOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Collection<@NonNull ? extends Expression<?>> operands
  ) {
    _type = type;
    _operator = operator;
    _operands = View.readonly(new ArrayList<>(operands));
  }

  public SequentialOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final Iterator<@NonNull ? extends Expression<?>> operands
  ) {
    @NonNull final List<@NonNull Expression<?>> operandsBuffer = new LinkedList<>();
    operands.forEachRemaining(operandsBuffer::add);

    _type = type;
    _operator = operator;
    _operands = View.readonly(new ArrayList<>(operandsBuffer));
  }

  public SequentialOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final Operator operator,
      @NonNull final View<@NonNull ? extends Expression<?>> operands
  ) {
    _type = type;
    _operator = operator;
    _operands = View.readonly(operands.stream().collect(Collectors.toList()));
  }

  public SequentialOperation(@NonNull final SequentialOperation<Result> toCopy) {
    _type = toCopy.getResultType();
    _operator = toCopy.getOperator();
    _operands = toCopy.getChildren();
  }

  public @NonNull Operator getOperator () {
    return _operator;
  }

  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _type;
  }

  @Override
  public @NonNull View<? extends Expression<?>> getChildren() {
    return _operands;
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof SequentialOperation) {
      @NonNull final SequentialOperation otherOperation = (SequentialOperation) other;

      return Objects.equals(
          _operands,
          otherOperation.getChildren()
      ) && Objects.equals(
          _operator,
          otherOperation.getOperator()
      ) && Objects.equals(
          _type,
          otherOperation.getResultType()
      );
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(_operands, _operator, _type);
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _type.getName() + " " + _operator.toString() + " " +
        _operands.toString() + " }";
  }
}
