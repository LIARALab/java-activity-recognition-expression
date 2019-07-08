package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.Type;
import org.liara.support.view.View;

/**
 * A mathematical expression.
 *
 * @param <Result> Type of value to expect from an evaluation of this expression.
 */
public interface Expression<Result>
{
  @NonNull
  View<@NonNull Expression> EMPTY_VIEW = View.readonly(Expression.class, new Expression[0]);

  /**
   * @return The type of result to expect from an evaluation of this expression.
   */
  @NonNull Type<Result> getResultType();

  /**
   * @return A view over each child expression of this expression.
   */
  default @NonNull View<@NonNull Expression> getChildren () {
    return EMPTY_VIEW;
  }
}
