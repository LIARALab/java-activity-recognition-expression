package org.liara.expression.operation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;
import org.liara.support.view.View;

public class StaticOperation<Result> implements Operation<Result> {

  @NonNull
  private final View<@NonNull Expression> _operands;

  @NonNull
  private final Operator _operator;

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Create a new static operation from a builder.
   *
   * @param type The expected type of the operation.
   * @param builder A builder to use in order to instantiate this operation.
   */
  public StaticOperation(
      @NonNull final Primitive<Result> type,
      @NonNull final StaticOperationBuilder builder
  ) {
    _operands = View.readonly(Expression.class, builder.getOperands().toArray(new Expression[0]));
    _operator = Objects.requireNonNull(builder.getOperator());
    _type = Objects.requireNonNull(type);
  }

  /**
   * Create a new static operation that is a copy of an existing one.
   *
   * @param toCopy A static operation to copy.
   */
  public StaticOperation(@NonNull final StaticOperation<Result> toCopy) {
    _operands = View.readonly(toCopy.getChildren());
    _operator = toCopy.getOperator();
    _type = toCopy.getResultType();
  }

  /**
   * Create a new static operation that is a copy of an existing operation.
   *
   * @param toCopy An operation to copy.
   */
  public StaticOperation(@NonNull final Operation<Result> toCopy) {
    _operands = View.readonly(toCopy.getChildren());
    _operator = toCopy.getOperator();
    _type = toCopy.getResultType();
  }

  /**
   * Create a new operation without a builder. For internal use only.
   *
   * @param resultType Expected result type of the given operation.
   * @param operator The operator of the operation to instantiate.
   * @param children An array with all children expression of this operation.
   */
  public StaticOperation(
      @NonNull final Primitive<Result> resultType,
      @NonNull final Operator operator,
      @NonNull final Expression[] children
  ) {
    _operands = View.readonly(Expression.class, Arrays.copyOf(children, children.length));
    _operator = operator;
    _type = resultType;
  }

  /**
   * @see Operation#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType() {
    return _type;
  }

  /**
   * @see Operation#getOperator()
   */
  @Override
  public @NonNull Operator getOperator() {
    return _operator;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren() {
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

    if (other instanceof StaticOperation) {
      return equals((StaticOperation) other);
    }

    return false;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(@NonNull final StaticOperation other) {
    if (
        !Objects.equals(_operator, other.getOperator()) ||
            !Objects.equals(_type, other.getResultType()) ||
            _operands.getSize() != other.getChildren().getSize()
    ) { return false; }

    for (int index = 0, size = _operands.getSize(); index < size; ++index) {
      if (!_operands.get(index).equals(other.getChildren().get(index))) {
        return false;
      }
    }

    return true;
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
    @NonNull final StringBuilder builder = new StringBuilder();

    builder.append(super.toString());
    builder.append("{ ");
    builder.append(_operator.toString());
    builder.append(" [");

    @NonNull final Iterator<@NonNull Expression> iterator = _operands.iterator();

    while (iterator.hasNext()) {
      builder.append(iterator.next().toString());
      if (iterator.hasNext()) {
        builder.append(", ");
      }
    }

    builder.append(" ] ");
    builder.append(_type.getName());
    builder.append(" }");

    return builder.toString();
  }
}
