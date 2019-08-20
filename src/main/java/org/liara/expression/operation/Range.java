package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.expression.Expression;

import java.util.Collection;

/**
 * A range expression that test that a comparable value is between a lower one (included) and an
 * upper one (included).
 *
 * @param <Compared> Type used to make the comparison.
 */
public interface Range<Compared extends Comparable<Compared>>
  extends Operation<Boolean>
{
  /**
   * @return The expression that gives the compared value.
   */
  @NonNull Expression<Compared> getValue ();

  /**
   * @return The expression that gives the minimum value of this range.
   */
  @NonNull Expression<Compared> getMinimum ();

  /**
   * @return The expression that gives the maximum value of this range.
   */
  @NonNull Expression<Compared> getMaximum ();

  /**
   * A rewritable expression that test that a comparable value is between a lower one (included) and
   * an upper one (included).
   *
   * @param <Compared> Type used to make the comparison.
   */
  interface Rewritable<Compared extends Comparable<Compared>>
    extends Range<Compared>, Operation.Rewritable<@NonNull Boolean>
  {
    /**
     * @see Operation.Rewritable#rewrite(int, Expression)
     */
    @Override
    @NonNull Range<Compared> rewrite (
      @NonNegative final int index, final @NonNull Expression<?> expression
    );

    /**
     * @see Operation.Rewritable#rewrite(Expression[])
     */
    @Override
    @NonNull Range<Compared> rewrite (@NonNull final Expression[] expressions);


    /**
     * @see Operation.Rewritable#setOperand(int, Expression)
     */
    @Override
    default @NonNull Range<Compared> setOperand (
      @NonNegative final int index, final @NonNull Expression<?> operand
    ) {
      return rewrite(index, operand);
    }

    /**
     * @see Operation.Rewritable#setOperands(Expression[])
     */
    @Override
    default @NonNull Range<Compared> setOperands (@NonNull final Expression<?>[] operands) {
      return rewrite(operands);
    }

    /**
     * @see Operation.Rewritable#setOperands(Collection)
     */
    @Override
    default @NonNull Range<Compared> setOperands (
      @NonNull final Collection<@NonNull Expression<?>> operands
    ) { return setOperands(operands.toArray(new Expression[0])); }

    /**
     * Instantiate a copy of this range expression with a new expression of the compared value.
     *
     * @param value The new expression of the value to compare.
     *
     * @return A copy of this range expression with a new expression of the compared value.
     */
    default @NonNull Range<Compared> setValue (@NonNull final Expression<Compared> value) {
      return rewrite(0, value);
    }

    /**
     * Instantiate a copy of this range expression with a new expression of the minimum boundary.
     *
     * @param minimum The new expression of the minimum boundary.
     *
     * @return A copy of this range expression with a new expression of the minimum boundary.
     */
    default @NonNull Range<Compared> setMinimum (@NonNull final Expression<Compared> minimum) {
      return rewrite(1, minimum);
    }

    /**
     * Instantiate a copy of this range expression with a new expression of the maximum boundary.
     *
     * @param maximum The new expression of the maximum boundary.
     *
     * @return A copy of this range expression with a new expression of the maximum boundary.
     */
    default @NonNull Range<Compared> setMaximum (@NonNull final Expression<Compared> maximum) {
      return rewrite(2, maximum);
    }
  }
}
