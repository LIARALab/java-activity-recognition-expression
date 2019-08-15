package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

/**
 * A mathematical expression.
 *
 * @param <Result> Primitive type to expect from an evaluation of this expression.
 */
public interface Expression<Result> extends TreeElement
{
  /**
   * Cast an unknown expression type to a known one.
   *
   * @param type Expected type of the expression.
   * @param expression Expression to cast.
   *
   * @param <Cast> Expected result type of the given expression.
   *
   * @return An expression of known type.
   */
  @SuppressWarnings("unchecked") // Checked by result type comparison.
  static <Cast> Expression<Cast> cast (
    @NonNull final Primitive<Cast> type,
    @NonNull final Expression<?> expression
  ) {
    if (expression.getResultType().equals(type)) {
      return (Expression<Cast>) expression;
    } else {
      throw new IllegalArgumentException(
        "Unable to cast an expression of " + expression.getResultType().toString() + " result " +
        "to an expression of " + type.toString() + " result."
      );
    }
  }

  /**
   * @return The type of result to expect from an evaluation of this expression.
   */
  @NonNull Primitive<Result> getResultType();

  /**
   * @return A view over each child expression of this expression.
   */
  @NonNull View<@NonNull Expression> getChildren ();
}
