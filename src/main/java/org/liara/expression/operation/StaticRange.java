package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.expression.Expression;

public class StaticRange<Compared extends Comparable<Compared>>
       extends StaticOperation<Boolean>
       implements Range.Rewritable<Compared>
{
  @NonNull
  private final Primitive<Compared> _comparedType;

  /**
   * Create a new static range operation.
   *
   * @param comparedType The type compared.
   * @param builder A builder that describe each operand of this range operation.
   */
  private StaticRange (
    @NonNull final Primitive<Compared> comparedType,
    @NonNull final StaticOperationBuilder builder
  ) {
    super(Primitives.BOOLEAN, builder);
    _comparedType = comparedType;
  }

  /**
   * Create a new static range operation that is a copy of another one.
   *
   * @param toCopy An existing static range operation to copy.
   */
  public StaticRange (@NonNull final StaticRange<Compared> toCopy) {
    super(toCopy);
    _comparedType = toCopy.getComparedType();
  }

  /**
   * Create a new static range operation without a builder. For internal use only.
   *
   * @param value The expression that produce the value to compare.
   * @param minimum The expression that produce the minimum boundary.
   * @param maximum The expression that produce the maximum boundary.
   */
  protected StaticRange (
    @NonNull final Expression<Compared> value,
    @NonNull final Expression<Compared> minimum,
    @NonNull final Expression<Compared> maximum
  ) {
    super(Operator.BETWEEN, new Expression[]{ value, minimum, maximum }, Primitives.BOOLEAN);
    _comparedType = value.getResultType();
  }

  /**
   * @see Range#getValue()
   */
  @Override
  @SuppressWarnings("unchecked") // Underlying values are always of valid type.
  public @NonNull Expression<Compared> getValue () {
    return (Expression<Compared>) getOperand(0);
  }

  /**
   * @see Range#getMinimum()
   */
  @Override
  @SuppressWarnings("unchecked") // Underlying values are always of valid type.
  public @NonNull Expression<Compared> getMinimum () {
    return (Expression<Compared>) getOperand(1);
  }

  /**
   * @see Expression.Rewritable#rewrite(int, Expression)
   */
  @Override
  public @NonNull StaticRange<Compared> rewrite (
    @NonNegative final int index,
    @NonNull final Expression expression
  ) {
    if (expression.getResultType().equals(_comparedType)) {
      if (index < 3) {
        return new StaticRange<>(this, index, expression);
      } else {
        throw new IllegalArgumentException(
          "Unable to rewrite the " + index + "th child of this range expression because " +
          "the given child index does not exists."
        );
      }
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite this range expression, the given expression is not an " +
        "expression of " + _comparedType.getName() + " result but an expression of " +
        expression.getResultType().getName() + " result."
      );
    }
  }

  /**
   * @see Expression.Rewritable#rewrite(Expression[])
   */
  @Override
  public @NonNull StaticRange<Compared> rewrite (@NonNull final Expression<?>[] expressions) {
    if (expressions.length == 3) {
      return new StaticRange<>(
        expressions[0].as(_comparedType),
        expressions[1].as(_comparedType),
        expressions[2].as(_comparedType)
      );
    } else {
      throw new IllegalArgumentException(
        "Unable to rewrite this range expression with the given ones because " +
        "the given array of expression does not have the right size : " + expressions.length +
        " != 3."
      );
    }
  }

  /**
   * @return The type of value compared.
   */
  public @NonNull Primitive<Compared> getComparedType () {
    return _comparedType;
  }
}
