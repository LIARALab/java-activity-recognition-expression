package org.liara.expression.function;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.data.type.DataType;
import org.liara.support.view.View;
import org.liara.expression.Expression;

import java.util.Objects;

public class StaticFunctionExpression<Result> implements FunctionExpression<Result>
{
  @NonNull
  private final View<@NonNull Expression> _parameters;

  @NonNull
  private final FunctionOperator _operator;

  @NonNull
  private final Primitive<Result> _type;

  public StaticFunctionExpression (@NonNull final StaticFunctionExpression<Result> expression) {
    _parameters = expression.getChildren();
    _operator = expression.getOperator();
    _type = expression.getResultType();
  }

  public StaticFunctionExpression (
    @NonNull final Primitive<Result> type,
    @NonNull final StaticFunctionExpressionBuilder builder
  ) {
    _parameters = View.readonly(Expression.class, builder.getOperands().toArray(new Expression[0]));
    _operator = Objects.requireNonNull(builder.getOperator());
    _type = type;
  }

  @Override
  public @NonNull View<@NonNull Expression> getChildren () {
    return _parameters;
  }

  @Override
  public @NonNull FunctionOperator getOperator () {
    return _operator;
  }

  @Override
  public @NonNull Primitive<Result> getResultType () {
    return _type;
  }
}
