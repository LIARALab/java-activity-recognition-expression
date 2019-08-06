package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.expression.RewritableExpression;
import org.liara.support.view.View;
import org.liara.expression.Expression;
import java.util.Objects;

public class StaticOperation<Result> implements Operation<Result>, RewritableExpression<Result>
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
   * Rewrite an existing operation.
   *
   * @param origin Original operation.
   * @param operands The new operands of the given operation.
   */
  private StaticOperation (
    @NonNull final StaticOperation<Result> origin,
    @NonNegative final Expression[] operands
  ) {
    _operands = View.readonly(Expression.class, operands);
    _operator = origin.getOperator();
    _type = origin.getResultType();
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

  /**
   * @see RewritableExpression#rewrite(int, Expression)
   */
  @Override
  public @NonNull Expression<Result> rewrite (
    @NonNegative final int index,
    @NonNull final Expression<?> expression
  ) {
    if (index < _operands.getSize()) {
      @NonNull final Expression[] operands = getChildren().toArray();
      operands[index] = expression;

      return new StaticOperation<>(this, operands);
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite the " + index + "th child of this operation expression because " +
        "this operation expression only have " + getChildren().getSize() + " children."
      );
    }
  }

  /**
   * @see RewritableExpression#rewrite(Expression[])
   */
  @Override
  public @NonNull Expression<Result> rewrite (
    @NonNull final Expression[] expressions
  ) {
    if (expressions.length == _operands.getSize()) {
      return new StaticOperation<>(this, expressions);
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite this operation expression with the given ones because " +
        "the given array of expression does not have the right size : " + expressions.length +
        " != " + _operands.getSize()
      );
    }
  }
}
