package org.liara.data.primitive;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

public final class Primitives {

  @NonNull
  private final static ArrayList<@NonNull Primitive> PRIMITIVES = new ArrayList<>();

  @NonNull
  private final static View<@NonNull Primitive> VIEW = View.readonly(Primitive.class, PRIMITIVES);

  @NonNull
  public final static Primitive<@NonNull Boolean> BOOLEAN = new Primitive<>(Boolean.class,
      "boolean");

  @NonNull
  public final static Primitive<@Nullable Boolean> NULLABLE_BOOLEAN = (
      new NullablePrimitive<>(BOOLEAN)
  );

  @NonNull
  public final static Primitive<@NonNull Byte> BYTE = new Primitive<>(Byte.class, "byte");

  @NonNull
  public final static Primitive<@Nullable Byte> NULLABLE_BYTE = new NullablePrimitive<>(BYTE);

  @NonNull
  public final static Primitive<@NonNull Short> SHORT = new Primitive<>(Short.class, "short");

  @NonNull
  public final static Primitive<@Nullable Short> NULLABLE_SHORT = new NullablePrimitive<>(SHORT);

  @NonNull
  public final static Primitive<@NonNull Integer> INTEGER = new Primitive<>(Integer.class,
      "integer");

  @NonNull
  public final static Primitive<@Nullable Integer> NULLABLE_INTEGER = (
      new NullablePrimitive<>(INTEGER)
  );

  @NonNull
  public final static Primitive<@NonNull Long> LONG = new Primitive<>(Long.class, "long");

  @NonNull
  public final static Primitive<@Nullable Long> NULLABLE_LONG = new NullablePrimitive<>(LONG);

  @NonNull
  public final static Primitive<@NonNull Float> FLOAT = new Primitive<>(Float.class, "float");

  @NonNull
  public final static Primitive<@Nullable Float> NULLABLE_FLOAT = new NullablePrimitive<>(FLOAT);

  @NonNull
  public final static Primitive<@NonNull Double> DOUBLE = new Primitive<>(Double.class, "double");

  @NonNull
  public final static Primitive<@Nullable Double> NULLABLE_DOUBLE = new NullablePrimitive<>(DOUBLE);

  @NonNull
  public final static Primitive<@NonNull Character> CHARACTER = new Primitive<>(Character.class,
      "character");

  @NonNull
  public final static Primitive<@Nullable Character> NULLABLE_CHARACTER = (
      new NullablePrimitive<>(CHARACTER)
  );

  @NonNull
  public final static Primitive<@NonNull String> STRING = new Primitive<>(String.class, "string");

  @NonNull
  public final static Primitive<@Nullable String> NULLABLE_STRING = (
      new NullablePrimitive<>(STRING)
  );

  @NonNull
  public final static Primitive<@NonNull ZonedDateTime> DATE_TIME = (
      new Primitive<>(ZonedDateTime.class, "datetime")
  );

  @NonNull
  public final static Primitive<@Nullable ZonedDateTime> NULLABLE_DATE_TIME = (
      new NullablePrimitive<>(DATE_TIME)
  );

  @NonNull
  public final static Primitive<@NonNull LocalTime> TIME = (
      new Primitive<>(LocalTime.class, "time")
  );

  @NonNull
  public final static Primitive<@Nullable LocalTime> NULLABLE_TIME = (
      new NullablePrimitive<>(TIME)
  );

  @NonNull
  public final static Primitive<@NonNull LocalDate> DATE = (
      new Primitive<>(LocalDate.class, "date")
  );

  @NonNull
  public final static Primitive<@Nullable LocalDate> NULLABLE_DATE = (
      new NullablePrimitive<>(DATE)
  );

  /**
   * Register the given primitive and assign an identifier to it.
   *
   * @param primitive The primitive to register.
   * @return An identifier for the given primitive.
   */
  public static @NonNegative int register(@NonNull final Primitive<?> primitive) {
    @NonNegative final int identifier;

    identifier = PRIMITIVES.size();
    PRIMITIVES.add(primitive);

    return identifier;
  }

  /**
   * @return A view over each registered primitive.
   */
  public static @NonNull View<@NonNull Primitive> getPrimitives() {
    return VIEW;
  }
}
