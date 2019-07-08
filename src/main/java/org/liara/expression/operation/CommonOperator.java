package org.liara.expression.operation;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CommonOperator
  extends Operator
{
  public static void load () {}

  @NonNull
  public static final CommonOperator MINUS = new CommonOperator();

  @NonNull
  public static final CommonOperator PLUS = new CommonOperator();

  @NonNull
  public static final CommonOperator NOT = new CommonOperator();

  @NonNull
  public static final CommonOperator BITWISE_NOT = new CommonOperator();

  @NonNull
  public static final CommonOperator OR = new CommonOperator();

  @NonNull
  public static final CommonOperator AND = new CommonOperator();

  @NonNull
  public static final CommonOperator XOR = new CommonOperator();

  @NonNull
  public static final CommonOperator SHIFT_LEFT = new CommonOperator();

  @NonNull
  public static final CommonOperator SHIFT_RIGHT = new CommonOperator();

  @NonNull
  public static final CommonOperator EQUAL = new CommonOperator();

  @NonNull
  public static final CommonOperator NOT_EQUAL = new CommonOperator();

  @NonNull
  public static final CommonOperator GREATER_THAN = new CommonOperator();

  @NonNull
  public static final CommonOperator GREATER_THAN_OR_EQUAL = new CommonOperator();

  @NonNull
  public static final CommonOperator LESS_THAN = new CommonOperator();

  @NonNull
  public static final CommonOperator LESS_THAN_OR_EQUAL = new CommonOperator();

  @NonNull
  public static final CommonOperator ADDITION = new CommonOperator();

  @NonNull
  public static final CommonOperator SUBTRACTION = new CommonOperator();

  @NonNull
  public static final CommonOperator DIVISION = new CommonOperator();

  @NonNull
  public static final CommonOperator MULTIPLICATION = new CommonOperator();

  @NonNull
  public static final CommonOperator POWER = new CommonOperator();

  @NonNull
  public static final CommonOperator MODULUS = new CommonOperator();

  @NonNull
  public static final CommonOperator BITWISE_AND = new CommonOperator();

  @NonNull
  public static final CommonOperator BITWISE_OR = new CommonOperator();

  @NonNull
  public static final CommonOperator BITWISE_XOR = new CommonOperator();
}
