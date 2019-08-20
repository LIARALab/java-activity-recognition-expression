package org.liara.expression.operation;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public enum Operator
{
  //INTERVAL(0),
  //BINARY(1),
  //COLLATE(2)
  NEGATION(3),
  MINUS(4),
  PLUS(4),
  BITWISE_NOT(4),
  BITWISE_XOR(5),
  MULTIPLICATION(6),
  DIVISION(6),
  MODULUS(6),
  SUBTRACTION(7),
  ADDITION(7),
  SHIFT_LEFT(8),
  SHIFT_RIGHT(8),
  BITWISE_AND(9),
  BITWISE_OR(10),
  EQUAL(11),
  NOT_EQUAL(11),
  GREATER_THAN(11),
  GREATER_THAN_OR_EQUAL(11),
  LESS_THAN(11),
  LESS_THAN_OR_EQUAL(11),
  IS(11),
  LIKE(11),
  REGEXP(11),
  IN(11),
  BETWEEN(12),
  NOT(13),
  AND(14),
  XOR(15),
  OR(16),
  FUNCTION(17);

  @NonNegative
  private final int _priority;

  Operator (@NonNegative final int priority) {
    _priority = priority;
  }

  public @NonNegative int getPriority () {
    return _priority;
  }
}
