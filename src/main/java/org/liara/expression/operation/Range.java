package org.liara.expression.operation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.Primitives;
import org.liara.expression.Expression;
import org.liara.support.view.View;

/**
 * A range expression that test that a comparable value is between a lower one (included) and an
 * upper one (included).
 */
public final class Range<Type extends Comparable<? super Type>> implements Expression<@NonNull Boolean> {
  @NonNull
  private final Expression<Type> _value;

  @NonNull
  private final Expression<Type> _minimum;

  @NonNull
  private final Expression<Type> _maximum;

  @NonNull
  private final View<? extends Expression<?>> _children;

  public Range(
      @NonNull final Expression<Type> value,
      @NonNull final Expression<Type> minimum,
      @NonNull final Expression<Type> maximum
  ) {
    _value = value;
    _minimum = minimum;
    _maximum = maximum;
    _children = View.readonly(new Expression<?>[]{value, minimum, maximum});
  }

  /**
   * @return The expression of the value to compare.
   */
  public @NonNull Expression<Type> getValue() {
    return _value;
  }

  /**
   * @return The expression of the minimum allowed value.
   */
  public @NonNull Expression<Type> getMinimum() {
    return _minimum;
  }

  /**
   * @return The expression of the maximum allowed value.
   */
  public @NonNull Expression<Type> getMaximum() {
    return _maximum;
  }

  /**
   * @see Expression#getResultType()
   */
  @Override
  public @NonNull Primitive<@NonNull Boolean> getResultType() {
    return Primitives.BOOLEAN;
  }

  /**
   * @see Expression#getChildren()
   */
  @Override
  public @NonNull View<? extends Expression<?>> getChildren() {
    return _children;
  }


  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof Range) {
      @NonNull final Range otherRange = (Range) other;

      return Objects.equals(
          _children,
          otherRange.getChildren()
      );
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(_children);
  }

  /**
   * @see Object#toString()
   */
  @Override
  public @NonNull String toString() {
    return super.toString() + "{ " + _children.toString() + " }";
  }
}
