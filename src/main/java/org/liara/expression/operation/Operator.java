package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class Operator
{
  @NonNull
  private final static ArrayList<@NonNull Operator> OPERATORS = new ArrayList<>();

  /**
   * Register an operator and return an identifier for it.
   *
   * @param operator An operator to register.
   *
   * @return An identifier for the given operator.
   */
  private static @NonNegative int register (@NonNull final Operator operator) {
    @NonNegative final int identifier;

    synchronized (OPERATORS) {
      identifier = OPERATORS.size();
      OPERATORS.add(operator);
    }

    return identifier;
  }

  /**
   * Return the operator with the given identifier.
   *
   * @param identifier Identifier of the operator to get.
   *
   * @return The operator with the given identifier.
   */
  public static @NonNull Operator get (@NonNegative final int identifier) {
    return OPERATORS.get(identifier);
  }

  /**
   * Return the number of existing operators.
   *
   * @return The number of existing operators.
   */
  public static @NonNegative int getCount () {
    return OPERATORS.size();
  }

  @NonNegative
  private final int _identifier;

  @NonNegative
  private final int _order;

  /**
   * Instantiate an operator.
   */
  public Operator () {
    _identifier = Operator.register(this);
    _order = 2;
  }

  public Operator (@NonNegative final int order) {
    _identifier = Operator.register(this);
    _order = order;
  }

  /**
   * @return A number that identify this operator.
   */
  public @NonNegative int getIdentifier () {
    return _identifier;
  }

  /**
   * @return The order of this operator.
   */
  public @NonNegative int getOrder () {
    return _order;
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof Operator) {
      @NonNull final Operator otherOperator = (Operator) other;
      return Objects.equals(_identifier, otherOperator.getIdentifier());
    }

    return false;
  }

  @Override
  public int hashCode () {
    return _identifier;
  }
}
