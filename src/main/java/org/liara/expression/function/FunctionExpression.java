package org.liara.expression.function;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

public interface FunctionExpression<Result> extends Expression<Result>
{
  @NonNull FunctionOperator getOperator ();
}
