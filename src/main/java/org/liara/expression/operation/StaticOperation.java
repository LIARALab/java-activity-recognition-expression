package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.view.View;
import org.liara.expression.Expression;

import java.util.Arrays;
import java.util.Objects;

public class StaticOperation<Result>
  implements Operation.Rewritable<Result>, Expression.Rewritable<Result>
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
   * Create a new operation without a builder. For internal use only.
   *
   * @param operator The operator of the operation to instantiate.
   * @param children An array with all children expression of this operation.
   * @param resultType Expected result type of the given operation.
   */
  protected StaticOperation (
    @NonNull final Operator operator,
    @NonNull final Expression<?>[] children,
    @NonNull final Primitive<Result> resultType
  ) {
    _operands = View.readonly(Expression.class, Arrays.copyOf(children, children.length));
    _operator = operator;
    _type = resultType;
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
   * @see Expression.Rewritable#rewrite(int, Expression)
   */
  @Override
  public @NonNull Operation<Result> rewrite (
    @NonNegative final int index,
    @NonNull final Expression<?> expression
  ) {
    if (index < _operands.getSize()) {
      @NonNull final Expression<?>[] expressions = _operands.toArray();
      expressions[index] = expression;

      return rewrite(expressions);
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite the " + index + "th operand of this operation expression because " +
        "this operation expression only have " + getChildren().getSize() + " children."
      );
    }
  }

  /**
   * @see Expression.Rewritable#rewrite(Expression[])
   */
  @Override
  public @NonNull Operation<Result> rewrite (@NonNull final Expression[] expressions) {
    return new StaticOperation<>(_operator, expressions, _type);
  }
}
