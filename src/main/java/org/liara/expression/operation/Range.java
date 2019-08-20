package org.liara.expression.operation;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.expression.Expression;

/**
 * A range expression that test that a comparable value is between a lower one (included) and an
 * upper one (included).
 */
public interface Range {
  /**
   * @return The operator that describe a range operation.
   */
  static @NonNull Operator getOperator () {
    return Operator.BETWEEN;
  }

  /**
   * Returns true if the given operation is a range operation.
   *
   * @param operation An operation to validate.
   *
   * @return True if the given operation is a range operation.
   */
  static boolean isRange (@Nullable final Operation<?> operation) {
    return Objects.nonNull(operation) &&
           operation.getOperator().equals(getOperator()) &&
           operation.getChildren().getSize() == 3 &&
           Objects.equals(
               operation.getOperand(0).getResultType(),
               operation.getOperand(1).getResultType()
           ) && Objects.equals(
               operation.getOperand(1).getResultType(),
               operation.getOperand(2).getResultType()
           );
  }

  /**
   * Returns true if the given operation is a range operation.
   *
   * @param operation An operation to validate.
   *
   * @return True if the given operation is a range operation.
   */
  static <Value extends Operation<?>> @NonNull Value assertThatIsRange (final Value operation) {
    if (Objects.isNull(operation)) {
      throw new IllegalArgumentException("Null operations are not range operation.");
    }

    if (!operation.getOperator().equals(getOperator())) {
      throw new IllegalArgumentException(
          "The given operation is not a range operation because its operator is " +
              operation.getOperator().toString() + " instead of " + getOperator().toString()
      );
    }

    if (operation.getChildren().getSize() != 3) {
      throw new IllegalArgumentException(
          "The given operation is not a range operation because it contains " +
              operation.getChildren().getSize() + "operand(s) instead of 3 : the value to compare" +
              ", the lower boundary and the upper boundary."
      );
    }

    @NonNull final Primitive<?> type = operation.getOperand(0).getResultType();

    for (int index = 1; index < 3; ++index) {
      if (!operation.getOperand(index).getResultType().equals(type)) {
        throw new IllegalArgumentException(
            "The given operation is not a range operation because its operators are not of the " +
                "same type, the expected type of the operands is " + type.getName() +
                " in accordance with the first one but the " + index + "th operand is of type " +
                operation.getOperand(index).getResultType().getName() + "."
        );
      }
    }

    return operation;
  }

  /**
   * @param operation A range operation.
   *
   * @return The expression that gives the value to compare.
   */
  static @NonNull Expression<?> getValue(@NonNull final Operation<?> operation) {
    return operation.getOperand(0);
  }

  /**
   * @param operation A range operation.
   *
   * @return The expression that gives the lower bound of this range (included).
   */
  static @NonNull Expression<?> getMinimum(@NonNull final Operation<?> operation) {
    return operation.getOperand(1);
  }

  /**
   * @param operation A range operation.
   *
   * @return The expression that gives the upper bound of this range (included).
   */
  static @NonNull Expression<?> getMaximum(@NonNull final Operation<?> operation) {
    return operation.getOperand(2);
  }
}
