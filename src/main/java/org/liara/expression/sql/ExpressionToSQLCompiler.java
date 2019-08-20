package org.liara.expression.sql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.expression.Constant;
import org.liara.expression.Expression;
import org.liara.expression.operation.BinaryOperation;
import org.liara.expression.operation.Operation;
import org.liara.expression.operation.Operator;
import org.liara.expression.operation.Range;
import org.liara.expression.operation.UnaryOperation;
import org.liara.support.tree.TreeWalker;

public class ExpressionToSQLCompiler {

  @NonNull
  private final static String EMPTY_STRING = "";

  @NonNull
  private static final List<@NonNull String> SYMBOLS;

  static {
    SYMBOLS = new ArrayList<>(Operator.values().length);

    for (int index = 0; index < Operator.values().length; ++index) {
      SYMBOLS.add(EMPTY_STRING);
    }

    SYMBOLS.set(Operator.BITWISE_XOR.ordinal(), "^");
    SYMBOLS.set(Operator.MULTIPLICATION.ordinal(), "*");
    SYMBOLS.set(Operator.DIVISION.ordinal(), "/");
    SYMBOLS.set(Operator.MODULUS.ordinal(), "%");
    SYMBOLS.set(Operator.SUBTRACTION.ordinal(), "-");
    SYMBOLS.set(Operator.ADDITION.ordinal(), "+");
    SYMBOLS.set(Operator.SHIFT_LEFT.ordinal(), "<<");
    SYMBOLS.set(Operator.SHIFT_RIGHT.ordinal(), ">>");
    SYMBOLS.set(Operator.BITWISE_AND.ordinal(), "&");
    SYMBOLS.set(Operator.BITWISE_OR.ordinal(), "|");
    SYMBOLS.set(Operator.EQUAL.ordinal(), "=");
    SYMBOLS.set(Operator.GREATER_THAN_OR_EQUAL.ordinal(), ">=");
    SYMBOLS.set(Operator.GREATER_THAN.ordinal(), ">");
    SYMBOLS.set(Operator.LESS_THAN_OR_EQUAL.ordinal(), "<=");
    SYMBOLS.set(Operator.LESS_THAN.ordinal(), "<");
    SYMBOLS.set(Operator.NOT_EQUAL.ordinal(), "!=");
    SYMBOLS.set(Operator.LIKE.ordinal(), "LIKE");
    SYMBOLS.set(Operator.REGEXP.ordinal(), "REGEXP");
    SYMBOLS.set(Operator.AND.ordinal(), "AND");
    SYMBOLS.set(Operator.XOR.ordinal(), "XOR");
    SYMBOLS.set(Operator.OR.ordinal(), "OR");
    SYMBOLS.set(Operator.PLUS.ordinal(), "+");
    SYMBOLS.set(Operator.MINUS.ordinal(), "-");
    SYMBOLS.set(Operator.NOT.ordinal(), "NOT");
    SYMBOLS.set(Operator.BITWISE_NOT.ordinal(), "~");
  }

  @NonNull
  private final List<@NonNull Operator> _operators;

  @NonNull
  private final TreeWalker<Expression> _walker;

  /**
   * Instantiate a new expression to SQL transpiler.
   */
  public ExpressionToSQLCompiler() {
    _operators = new LinkedList<>();
    _walker = new TreeWalker<>(Expression.class);
  }

  /**
   * Reset this compiler inner state in order to walk throughout the expression another time.
   */
  public void reset() {
    _walker.moveToStart();
    _operators.clear();
  }

  /**
   * Compile the entire expression into the given builder.
   *
   * @param output The string builder to fill with the compiled content.
   */
  public void compile(@NonNull final StringBuilder output) {
    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        enter(output);
      }

      if (_walker.canExit()) {
        exit(output);

        if (_walker.hasCurrent()) {
          back(output);
        }
      }
    }
  }

  /**
   * Let the compiler enter into the next child expression.
   *
   * @param output The string builder to fill with the compiled content.
   *
   * @return The entered expression.
   */
  public @NonNull Expression<?> enter (@NonNull final StringBuilder output) {
    @NonNull final Expression<?> expression = _walker.enter();

    if (expression instanceof Operation) {
      enterOperation((Operation<?>) expression, output);
    }

    return expression;
  }

  /**
   * Render the given operation expression as this compiler enter in it.
   *
   * @param operation The visited operation.
   * @param output The string builder to fill with the compiled content.
   * @param <T> The expected result type of the given operation.
   */
  private <T> void enterOperation(
      @NonNull final Operation<T> operation,
      @NonNull final StringBuilder output
  ) {
    _operators.add(operation.getOperator());

    if (doViolatePrecedence()) {
      output.append('(');
    }

    if (UnaryOperation.isUnaryOperation(operation)) {
      output.append(SYMBOLS.get(operation.getOperator().ordinal()));
      output.append(' ');
    }
  }

  /**
   * @see TreeWalker#canEnter()
   */
  public boolean canEnter() {
    return _walker.canEnter();
  }

  /**
   * @see TreeWalker#current()
   */
  public @NonNull Expression current() {
    return _walker.current();
  }

  /**
   * @see TreeWalker#hasCurrent()
   */
  public boolean hasCurrent() {
    return _walker.hasCurrent();
  }

  /**
   * @see TreeWalker#canExit()
   */
  public boolean canExit() {
    return _walker.canExit();
  }

  /**
   * Let the compiler moves out of its current node.
   *
   * @param output The string builder to fill with the compiled content.
   * @return The exited expression.
   */
  public @NonNull Expression<?> exit (@NonNull final StringBuilder output) {
    @NonNull final Expression<?> exited = _walker.exit();

    if (exited instanceof Constant) {
      exitConstant((Constant<?>) exited, output);
    } else if (exited instanceof Operation) {
      exitOperation((Operation<?>) exited, output);
    }

    if (_walker.hasCurrent() && _walker.current() instanceof Operation<?>) {
      @NonNull final Operation<?> operation = (Operation<?>) _walker.current();

      if (operation.getOperator().equals(Range.getOperator())) {
        if (exited == Range.getValue(operation)) {
          output.append(" BETWEEN ");
        } else if (exited == Range.getMinimum(operation)) {
          output.append(" AND ");
        }
      }
    }

    return exited;
  }

  /**
   * Called when this compiler exit an operation.
   *
   * @param operation The operation that was exited.
   * @param output The string builder to fill with the compiled content.
   */
  private <T> void exitOperation(
      @NonNull final Operation<T> operation,
      @NonNull final StringBuilder output
  ) {
    if (doViolatePrecedence()) {
      output.append(')');
    }

    _operators.remove(_operators.size() - 1);
  }

  /**
   * Called when this compiler exit a constant.
   *
   * @param expression The exited expression.
   * @param output The string builder to fill with the compiled content.
   *
   * @param <T> DataType of the constant expression.
   */
  private <T> void exitConstant(
      @NonNull final Constant<T> expression,
      @NonNull final StringBuilder output
  ) {
    if (Boolean.class.isAssignableFrom(expression.getResultType().getJavaClass())) {
      exitConstant((Boolean) expression.getValue(), output);
    } else if (Number.class.isAssignableFrom(expression.getResultType().getJavaClass())) {
      exitConstant((Number) expression.getValue(), output);
    } else if (Character.class.isAssignableFrom(expression.getResultType().getJavaClass())) {
      exitConstant((Character) expression.getValue(), output);
    } else if (String.class.isAssignableFrom(expression.getResultType().getJavaClass())) {
      exitConstant((String) expression.getValue(), output);
    } else {
      throw new Error("Unhandled constant type " + expression.getResultType().toString());
    }
  }

  /**
   * Output a numeric value.
   *
   * @param value A numeric value to output.
   * @param output The string builder to fill with the compiled content.
   */
  private void exitConstant(@Nullable final Number value, @NonNull final StringBuilder output) {
    output.append(value == null ? "NULL" : value.toString());
  }

  /**
   * Output a boolean value.
   *
   * @param value A boolean value to output.
   * @param output The string builder to fill with the compiled content.
   */
  private void exitConstant(@Nullable final Boolean value, @NonNull final StringBuilder output) {
    output.append(value == null ? "NULL" : (value ? "1" : "0"));
  }

  /**
   * Output a string value.
   *
   * @param value A string value to output.
   * @param output The string builder to fill with the compiled content.
   */
  private void exitConstant(@Nullable final String value, @NonNull final StringBuilder output) {
    if (value == null) {
      output.append("NULL");
    } else {
      output.append('"');
      output.append(StringEscapeUtils.escapeJava(value));
      output.append('"');
    }
  }

  /**
   * Output a character value.
   *
   * @param value A character value to output.
   * @param output The string builder to fill with the compiled content.
   */
  private void exitConstant(@Nullable final Character value, @NonNull final StringBuilder output) {
    if (value == null) {
      output.append("NULL");
    } else {
      output.append('\"');
      output.append(StringEscapeUtils.escapeJava(value.toString()));
      output.append('\"');
    }
  }

  /**
   * Called when the compiler goes back to an expression.
   *
   * @param output The string builder to fill with the compiled content.
   */
  public @NonNull Expression<?> back (@NonNull final StringBuilder output) {
    @NonNull final Expression<?> expression = _walker.current();

    if (expression instanceof Operation) {
      backOperation((Operation<?>) expression, output);
    }

    return expression;
  }

  /**
   * Called when the compiler goes back to an operation.
   *
   * @param operation The operation that was entered.
   * @param output The string builder to fill with the compiled content.
   */
  private <T> void backOperation(
      @NonNull final Operation<T> operation,
      @NonNull final StringBuilder output
  ) {
    if (BinaryOperation.isBinaryOperation(operation)) {
      if (_walker.canEnter()) {
        output.append(' ');
        output.append(SYMBOLS.get(operation.getOperator().ordinal()));
        output.append(' ');
      }
    }
  }

  /**
   * @return True if the current operation violates the operator precedence.
   */
  private boolean doViolatePrecedence() {
    return _operators.size() > 1 && (
        _operators.get(_operators.size() - 2).getPriority() <
            _operators.get(_operators.size() - 1).getPriority()
    );
  }

  /**
   * @return The current compiled expression.
   */
  public @Nullable Expression<?> getExpression() {
    return _walker.getRoot();
  }

  /**
   * Update the compiled expression, this method will reset the compiler state and moves it back to
   * the start of the given expression.
   *
   * @param expression An expression to compile.
   */
  public void setExpression(@Nullable final Expression<?> expression) {
    _walker.setRoot(expression);
    _operators.clear();
  }

  /**
   * @see TreeWalker#isAtLocation(TreeWalker)
   */
  public boolean isAtLocation(@NonNull final TreeWalker<Expression> walker) {
    return _walker.isAtLocation(walker);
  }

  /**
   * @see TreeWalker#isAtEnd()
   */
  public boolean isAtEnd() {
    return _walker.isAtEnd();
  }

  /**
   * @see TreeWalker#isAtStart()
   */
  public boolean isAtStart() {
    return _walker.isAtStart();
  }

  /**
   * @see TreeWalker#doesMoveForward()
   */
  public boolean doesMoveForward() {
    return _walker.doesMoveForward();
  }

  /**
   * @see TreeWalker#setMovingForward(boolean)
   */
  public void setMovingForward(final boolean forward) {
    _walker.setMovingForward(forward);
  }

  /**
   * @see TreeWalker#getPath()
   */
  public @NonNull List<@NonNull Expression> getPath() {
    return _walker.getPath();
  }
}
