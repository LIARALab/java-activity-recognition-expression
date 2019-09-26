package org.liara.expression;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.support.tree.TreeElement;
import org.liara.support.view.View;

public interface Expression<Result> extends TreeElement {

  /**
   * Assert that the given expression does return a value of the given type.
   *
   * @param type The type of result expected from a resolution of the given expression.
   * @param value An expression to assert.
   * @param <Value> Exact kind of expression to assert.
   * @return The given expression.
   */
  static <Value extends Expression<?>> Value assertThatExpressionDoesReturns(
      @NonNull final Primitive<?> type, final Value value
  ) {
    if (value == null || value.getResultType().equals(type)) {
      return value;
    } else {
      throw new IllegalArgumentException(
          "The given expression, expected to resolve to a result of type " + type.getName() +
              ", resolve to a value of type " + value.getResultType().toString() + " instead."
      );
    }
  }

  /**
   * Assert that the given expression does return a value of any of the given type.
   *
   * @param types An array of valid types.
   * @param value An expression to assert.
   * @param <Value> Exact kind of expression to assert.
   * @return The given expression.
   */
  static <Value extends Expression<?>> Value assertThatExpressionDoesReturns(
      @NonNull final Primitive<?>[] types, final Value value
  ) {
    if (value == null) {
      return null;
    }

    for (@NonNull final Primitive<?> type : types) {
      if (value.getResultType().equals(type)) {
        return value;
      }
    }

    throw new IllegalArgumentException(
        "The given expression, expected to resolve to a result of any of the types [" +
            Arrays.stream(types).map(Primitive::getName).collect(Collectors.joining(", ")) +
            "], resolve to a value of type " + value.getResultType().toString() + " instead."
    );
  }

  /**
   * @return The type of result to expect from an evaluation of this expression.
   */
  @NonNull Primitive<Result> getResultType();

  /**
   * @return A view over each child expression of this expression.
   */
  @NonNull View<@NonNull ? extends Expression<?>> getChildren();


}
