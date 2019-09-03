package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface UnaryOperation {
  /**
   * Return true if the given operation is an unary one.
   *
   * @param operation An operation.
   *
   * @return True if the given operation is an unary one.
   */
  static boolean isUnaryOperation (@NonNull final Operation<?> operation) {
    switch (operation.getOperator()) {
      case NEGATION:
      case BITWISE_NOT:
      case NOT:
      case PLUS:
      case MINUS:
        return true;
    }

    return false;
  }
}
