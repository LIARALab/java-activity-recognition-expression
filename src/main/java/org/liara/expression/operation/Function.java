package org.liara.expression.operation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;
import org.liara.expression.Identifier;

/**
 * A functional expression.
 */
public interface Function {
  /**
   * @return The operator that describe a functional operation.
   */
  static @NonNull Operator getOperator() {
    return Operator.FUNCTION;
  }

  /**
   * Returns true if the given operation is a functional operation.
   *
   * @param operation An operation to validate.
   *
   * @return True if the given operation is a functional operation.
   */
  static boolean isFunction (@Nullable final Operation<?> operation) {
    return Objects.nonNull(operation) &&
           operation.getOperator().equals(getOperator()) &&
           operation.getChildren().getSize() > 0 &&
           operation.getOperand(0) instanceof Identifier;
  }

  /**
   * Returns true if the given operation is a functional operation.
   *
   * @param operation An operation to validate.
   *
   * @return True if the given operation is a functional operation.
   */
  static <Value extends Operation<?>> @NonNull Value assertThatIsFunction(final Value operation) {
    if (Objects.isNull(operation)) {
      throw new IllegalArgumentException("Null operations are not functional operation.");
    }

    if (!operation.getOperator().equals(getOperator())) {
      throw new IllegalArgumentException(
          "The given operation is not a functional operation because its operator is " +
              operation.getOperator().toString() + " instead of " + getOperator().toString()
      );
    }

    if (operation.getChildren().isEmpty()) {
      throw new IllegalArgumentException(
          "The given operation is not a functional operation because it does not have any  " +
              "operands, and as a consequence, does not declare the identifier of the " +
              "function to call."
      );
    }

    if (!(operation.getOperand(0) instanceof Identifier)) {
      throw new IllegalArgumentException(
          "The given operation is not a functional operation because its first operand is not " +
              "an identifier."
      );
    }

    return operation;
  }

  /**
   * @param operation A functional operation.
   *
   * @return The identifier of the function to call.
   */
  static @NonNull Identifier getIdentifier(@NonNull final Operation<?> operation) {
    return (Identifier) operation.getOperand(0);
  }
}
