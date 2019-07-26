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
   * @return The type of result to expect from an evaluation of this expression.
   */
  @NonNull Primitive<Result> getResultType();

  /**
   * @return A view over each child expression of this expression.
   */
  @NonNull View<@NonNull Expression> getChildren ();
}
