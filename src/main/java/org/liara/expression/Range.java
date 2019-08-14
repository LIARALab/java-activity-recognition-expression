package org.liara.expression;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.support.view.View;

import java.util.Arrays;

public class Range<T extends Comparable> implements Expression<@NonNull Boolean>,
                                                    RewritableExpression<@NonNull Boolean>
{
  @NonNull
  private final Expression<T> _value;

  @NonNull
  private final Expression<T> _minimum;

  @NonNull
  private final Expression<T> _maximum;

  @NonNull
  private final View<@NonNull Expression> _children;

  public Range (
    @NonNull final Expression<T> value,
    @NonNull final Expression<T> minimum,
    @NonNull final Expression<T> maximum
  ) {
    _value = value;
    _minimum = minimum;
    _maximum = maximum;

    _children = View.readonly(Expression.class, Arrays.asList(value, minimum, maximum));
  }

  public Range (@NonNull final Range<T> toCopy) {
    this(toCopy.getValue(), toCopy.getMinimum(), toCopy.getMaximum());
  }

  public @NonNull Expression<T> getValue () {
    return _value;
  }

  public @NonNull Range<T> setValue (@NonNull final Expression<T> value) {
    return new Range<>(value, _minimum, _maximum);
  }

  public @NonNull Expression<T> getMinimum () {
    return _minimum;
  }

  public @NonNull Range<T> setMinimum (@NonNull final Expression<T> minimum) {
    return new Range<>(_value, minimum, _maximum);
  }

  public @NonNull Expression<T> getMaximum () {
    return _maximum;
  }

  public @NonNull Range<T> setMaximum (@NonNull final Expression<T> maximum) {
    return new Range<>(_value, _minimum, maximum);
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<@NonNull Boolean> getResultType () {
    return Primitives.BOOLEAN;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<@NonNull Expression> getChildren () {
    return _children;
  }

  /**
   * @see RewritableExpression#rewrite(int, Expression)
   */
  @Override
  public @NonNull Range<T> rewrite (
    @NonNegative final int index,
    @NonNull final Expression expression
  ) {
    if (expression.getResultType().equals(_value.getResultType())) {
      switch (index) {
        case 0: return setValue(_value);
        case 1: return setMinimum(_minimum);
        case 2: return setMaximum(_maximum);
      }

      throw new IllegalArgumentException(
        "Unable to rewrite the " + index + "th child of this range expression because " +
        "the given child index does not exists."
      );
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite this range expression, the given expression is not an " +
        "expression of " + _value.getResultType() + " result but an expression of " +
        expression.getResultType() + " result."
      );
    }
  }

  /**
   * @see RewritableExpression#rewrite(Expression[])
   */
  @Override
  @SuppressWarnings("unchecked") // Checked at the begining of the method.
  public @NonNull Range<T> rewrite (
    @NonNull final Expression[] expressions
  ) {
    for (int index = 0; index < expressions.length; ++index) {
      if (expressions[index].getResultType().equals(_value.getResultType())) {
        throw new IllegalArgumentException(
          "Unable to rewrite this range expression with the given ones because " +
          "the " + index + "th expression is not an expression of  " + _value.getResultType() +
          " result but an expression of " + expressions[index].getResultType() + " result."
        );
      }
    }

    if (expressions.length == 3) {
      return new Range<>(
        (Expression<T>) expressions[0],
        (Expression<T>) expressions[1],
        (Expression<T>) expressions[2]
      );
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite this range expression with the given ones because " +
        "the given array of expression does not have the right size : " + expressions.length +
        " != 3."
      );
    }
  }
}
