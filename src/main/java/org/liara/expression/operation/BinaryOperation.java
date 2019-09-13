package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface BinaryOperation {
  /**
   * Return true if the given operation is a binary one.
   *
   * @param operation An operation.
   *
   * @return True if the given operation is a binary one.
   */
  static boolean isBinaryOperation(@NonNull final Operation<?> operation) {
    return isRepeatableBinaryOperation(operation) || isSingletonBinaryOperation(operation);
  }

  /**
   * Return true if the given operation is a singleton binary operation.
   *
   * A singleton binary operation is an operation that can't be repeated multiple times.
   *
   * @param operation An operation.
   *
   * @return Return true if the given operation is a binary one.
   */
  static boolean isSingletonBinaryOperation (@NonNull final Operation<?> operation) {
    switch (operation.getOperator()) {
      case IS:
      case LIKE:
      case IN:
      case EQUAL:
      case REGEXP:
      case GREATER_THAN:
      case LESS_THAN:
      case LESS_THAN_OR_EQUAL:
      case GREATER_THAN_OR_EQUAL:
        return true;
    }

    return false;
  }

  /**
   * Return true if the given operation is a repeatable binary operation.
   *
   * A repeatable binary operation is an operation that can be chained multiple times like a sum
   * for the addition.
   *
   * @param operation An operation.
   *
   * @return True if the given operation is a repeatable binary operation.
   */
  static boolean isRepeatableBinaryOperation(@NonNull final Operation<?> operation) {
    switch (operation.getOperator()) {
      case OR:
      case AND:
      case XOR:
      case MODULUS:
      case ADDITION:
      case DIVISION:
      case BITWISE_OR:
      case SHIFT_LEFT:
      case BITWISE_AND:
      case BITWISE_NOT:
      case BITWISE_XOR:
      case SHIFT_RIGHT:
      case SUBTRACTION:
      case MULTIPLICATION:
        return true;
    }

    return false;
  }
}
