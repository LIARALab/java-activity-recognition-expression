package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

public interface Operation<Result> extends Expression<Result> {
  @NonNull Operator getOperator();
}
