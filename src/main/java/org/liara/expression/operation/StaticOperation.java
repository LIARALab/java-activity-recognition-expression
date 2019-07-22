package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;
import org.liara.expression.Expression;
import java.util.Objects;

public class StaticOperation<Result> implements Operation<Result>
{
  @NonNull
  private final View<@NonNull Expression> _operands;

  @NonNull
  private final Operator _operator;

  @NonNull
  private final Primitive<Result> _type;

  /**
   * Create a new static operation from a builder.
   *
   * @param type Primitive to expect from an evaluation of this expression.
   * @param builder A builder to use in order to instantiate this operation.
   */
  public StaticOperation (
    @NonNull final Primitive<Result> type,
    @NonNull final StaticOperationBuilder builder
  ) {
    _operands = View.readonly(Expression.class, builder.getOperands().toArray(new Expression[0]));
    _operator = Objects.requireNonNull(builder.getOperator());
    _type = type;
  }

  /**
   * Create a new static operation that is a copy of an existing one.
   *
   * @param toCopy A static operation to copy.
   */
  public StaticOperation (@NonNull final StaticOperation<Result> toCopy) {
    _operands = View.readonly(toCopy.getChildren());
    _operator = toCopy.getOperator();
    _type = toCopy.getResultType();
  }

  /**
   * @see Operation#getResultType()
   */
  @Override
  public @NonNull Primitive<Result> getResultType () {
    return _type;
  }

  /**
   * @see Operation#getOperator()
   */
  @Override
  public @NonNull Operator getOperator () {
    return _operator;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren () {
    return _operands;
  }
}
