package org.liara.expression.sql;

import org.apache.commons.text.StringEscapeUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitives;
import org.liara.data.type.DataType;
import org.liara.data.type.DataTypes;
import org.liara.expression.Constant;
import org.liara.expression.Expression;
import org.liara.expression.ExpressionWalker;
import org.liara.expression.operation.Operation;
import org.liara.expression.operation.Operator;
import org.liara.expression.operation.CommonOperator;

import java.util.*;

public class SQLExpressionTranspiler
{
  @NonNull
  private static final Expression<?> DEFAULT_EXPRESSION = new Constant<>(Primitives.INTEGER, 0);

  static {
    CommonOperator.load();
  }

  @NonNull
  private static final List<@NonNegative @NonNull Integer> PRECEDENCE;

  static {
    PRECEDENCE = new ArrayList<>(Operator.getCount());

    for (int index = 0; index < Operator.getCount(); ++index) PRECEDENCE.add(0);

    PRECEDENCE.set(CommonOperator.MINUS.getIdentifier(), 1);
    PRECEDENCE.set(CommonOperator.BITWISE_NOT.getIdentifier(), 1);
    PRECEDENCE.set(CommonOperator.BITWISE_XOR.getIdentifier(), 2);
    PRECEDENCE.set(CommonOperator.MULTIPLICATION.getIdentifier(), 3);
    PRECEDENCE.set(CommonOperator.DIVISION.getIdentifier(), 3);
    PRECEDENCE.set(CommonOperator.MODULUS.getIdentifier(), 3);
    PRECEDENCE.set(CommonOperator.SUBTRACTION.getIdentifier(), 4);
    PRECEDENCE.set(CommonOperator.ADDITION.getIdentifier(), 4);
    PRECEDENCE.set(CommonOperator.SHIFT_LEFT.getIdentifier(), 5);
    PRECEDENCE.set(CommonOperator.SHIFT_RIGHT.getIdentifier(), 5);
    PRECEDENCE.set(CommonOperator.BITWISE_AND.getIdentifier(), 6);
    PRECEDENCE.set(CommonOperator.BITWISE_OR.getIdentifier(), 7);
    PRECEDENCE.set(CommonOperator.EQUAL.getIdentifier(), 8);
    PRECEDENCE.set(CommonOperator.GREATER_THAN_OR_EQUAL.getIdentifier(), 8);
    PRECEDENCE.set(CommonOperator.GREATER_THAN.getIdentifier(), 8);
    PRECEDENCE.set(CommonOperator.LESS_THAN_OR_EQUAL.getIdentifier(), 8);
    PRECEDENCE.set(CommonOperator.LESS_THAN.getIdentifier(), 8);
    PRECEDENCE.set(CommonOperator.NOT_EQUAL.getIdentifier(),8);
    PRECEDENCE.set(CommonOperator.NOT.getIdentifier(), 9);
    PRECEDENCE.set(CommonOperator.AND.getIdentifier(), 10);
    PRECEDENCE.set(CommonOperator.XOR.getIdentifier(), 11);
    PRECEDENCE.set(CommonOperator.OR.getIdentifier(), 12);
  }

  @NonNull
  private static final List<@NonNull String> SYMBOLS;

  static {
    SYMBOLS = new ArrayList<>(Operator.getCount());

    for (int index = 0; index < Operator.getCount(); ++index) SYMBOLS.add("");

    SYMBOLS.set(CommonOperator.BITWISE_XOR.getIdentifier(), "^");
    SYMBOLS.set(CommonOperator.MULTIPLICATION.getIdentifier(), "*");
    SYMBOLS.set(CommonOperator.DIVISION.getIdentifier(), "/");
    SYMBOLS.set(CommonOperator.MODULUS.getIdentifier(), "%");
    SYMBOLS.set(CommonOperator.SUBTRACTION.getIdentifier(), "-");
    SYMBOLS.set(CommonOperator.ADDITION.getIdentifier(), "+");
    SYMBOLS.set(CommonOperator.SHIFT_LEFT.getIdentifier(), "<<");
    SYMBOLS.set(CommonOperator.SHIFT_RIGHT.getIdentifier(), ">>");
    SYMBOLS.set(CommonOperator.BITWISE_AND.getIdentifier(), "&");
    SYMBOLS.set(CommonOperator.BITWISE_OR.getIdentifier(), "|");
    SYMBOLS.set(CommonOperator.EQUAL.getIdentifier(), "=");
    SYMBOLS.set(CommonOperator.GREATER_THAN_OR_EQUAL.getIdentifier(), ">=");
    SYMBOLS.set(CommonOperator.GREATER_THAN.getIdentifier(), ">");
    SYMBOLS.set(CommonOperator.LESS_THAN_OR_EQUAL.getIdentifier(), "<=");
    SYMBOLS.set(CommonOperator.LESS_THAN.getIdentifier(), "<");
    SYMBOLS.set(CommonOperator.NOT_EQUAL.getIdentifier(), "!=");
    SYMBOLS.set(CommonOperator.AND.getIdentifier(), "AND");
    SYMBOLS.set(CommonOperator.XOR.getIdentifier(), "XOR");
    SYMBOLS.set(CommonOperator.OR.getIdentifier(), "OR");
    SYMBOLS.set(CommonOperator.PLUS.getIdentifier(), "+");
    SYMBOLS.set(CommonOperator.MINUS.getIdentifier(), "-");
    SYMBOLS.set(CommonOperator.NOT.getIdentifier(), "NOT");
    SYMBOLS.set(CommonOperator.BITWISE_NOT.getIdentifier(), "~");
  }

  @NonNull
  private final List<@NonNull Operator> _operators;

  @NonNull
  private final StringBuilder _result;

  @NonNull
  private final ExpressionWalker _walker;

  public SQLExpressionTranspiler () {
    _operators = new LinkedList<>();
    _result = new StringBuilder();
    _walker = new ExpressionWalker(DEFAULT_EXPRESSION);
  }

  public @NonNull String transpile (@NonNull final Expression<?> expression) {
    _walker.movesForward();
    _walker.setRoot(expression);

    while (!_walker.isAtEnd()) {
      while (_walker.canEnter()) {
        enter(_walker.enter());
      }

      while (!_walker.canEnter()) {
        exit(_walker.exit());
      }
    }

    @NonNull final String result = _result.toString();

    _result.setLength(0);

    return result;
  }

  private void enter (@NonNull final Expression<?> expression) {
    if (expression instanceof Operation) {
      enterOperation((Operation<?>) expression);
    }
  }

  private <T> void enterOperation (final Operation<T> expression) {
    _operators.add(expression.getOperator());
    if (doViolatePrecedence()) _result.append('(');

    if (
      expression.getOperator() == CommonOperator.PLUS ||
      expression.getOperator() == CommonOperator.MINUS ||
      expression.getOperator() == CommonOperator.BITWISE_NOT ||
      expression.getOperator() == CommonOperator.NOT
    ) {
      enterUnitaryOperation(expression);
    }
  }

  private <T> void enterUnitaryOperation (@NonNull final Operation<T> expression) {
    _result.append(SYMBOLS.get(expression.getOperator().getIdentifier()));
  }

  private void exit (@NonNull final Expression<?> expression) {
    if (expression instanceof Constant) {
      exitConstant((Constant<?>) expression);
    }
  }

  /**
   * Called when the transpiler exit a constant expression.
   *
   * @param expression The exited expression.
   *
   * @param <T> DataType of the constant expression.
   */
  private <T> void exitConstant (@NonNull final Constant<T> expression) {
    /*if (Boolean.class.isAssignableFrom(expression.getResultType().getGeneric().getType())) {
      output((Boolean) expression.getValue());
    } else if (Number.class.isAssignableFrom(expression.getResultType().getGeneric())) {
      output((Number) expression.getValue());
    } else if (Character.class.isAssignableFrom(expression.getResultType().getGeneric())) {
      output((Character) expression.getValue());
    } else if (String.class.isAssignableFrom(expression.getResultType().getGeneric())) {
      output((String) expression.getValue());
    } else {*/
      throw new Error("Unhandled constant type " + expression.getResultType().toString());
    //}
  }

  private void output (@Nullable final Number number) {
    _result.append(number == null ? "NULL" : number.toString());
  }

  private void output (@Nullable final Boolean value) {
    _result.append(value == null ? "NULL" : (value ? "1" : "0"));
  }

  private void output (@Nullable final String value) {
    if (value == null) {
      _result.append("NULL");
    } else {
      _result.append('"');
      _result.append(StringEscapeUtils.escapeJava(value));
      _result.append('"');
    }
  }

  private void output (@Nullable final Character value) {
    if (value == null) {
      _result.append("NULL");
    } else {
      _result.append('\'');
      _result.append(value.toString());
      _result.append('\'');
    }
  }

  /*
  protected void visit (@NonNull final Operation<?> expression) {


    visit(expression.getChildren().get(0));

    for (int index = 1, size = expression.getChildren().getBytes(); index < size; ++index) {
      _result.append(' ');
      _result.append(symbol);
      _result.append(' ');
      visit(expression.getChildren().get(index));
    }

    if (violatePrecedence) _result.append(')');

    _operators.remove(_operators.size() - 1);
  }
  */

  /*
  @Override
  protected void visit (@NonNull final UnaryExpression<?> expression) {
    _operators.add(expression.getOperator());

    final boolean violatePrecedence = doViolatePrecedence();
    @NonNull final String symbol = SYMBOLS.get(expression.getOperator().getIdentifier());

    if (violatePrecedence) _result.append('(');

    _result.append(symbol);
    _result.append(' ');
    visit(expression.getChild(0));

    if (violatePrecedence) _result.append(')');

    _operators.remove(_operators.size() - 1);
  }
  */

  private boolean doViolatePrecedence () {
    return _operators.size() > 1 && (
      PRECEDENCE.get(_operators.get(_operators.size() - 2).getIdentifier())
    ) < PRECEDENCE.get(_operators.get(_operators.size() - 1).getIdentifier());
  }
}
