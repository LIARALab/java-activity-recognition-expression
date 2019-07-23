package org.liara.data.type;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.common.*;
import org.liara.support.generic.Generics;
import org.liara.support.generic.StaticGeneric;

import java.nio.charset.Charset;

public final class DataTypes
{
  @NonNull
  public static final DataType<@NonNull Boolean> BOOLEAN = new BooleanDataType();

  @NonNull
  public static final DataType<@NonNull Byte> BYTE = new ByteDataType();

  @NonNull
  public static final DataType<@NonNull Character> CHARACTER = new CharacterDataType();

  @NonNull
  public static final DataType<@NonNull Double> DOUBLE = new DoubleDataType();

  @NonNull
  public static final DataType<@NonNull Float> FLOAT = new FloatDataType();

  @NonNull
  public static final DataType<@NonNull Integer> INTEGER = new IntegerDataType();

  @NonNull
  public static final DataType<@NonNull Long> LONG = new LongDataType();

  @NonNull
  public static final DataType<@NonNull Short> SHORT = new ShortDataType();

  @NonNull
  public static final DataType<@Nullable Boolean> NULLABLE_BOOLEAN = (
    new NullableDataType<>(new StaticGeneric<@Nullable Boolean>() {}, BOOLEAN)
  );

  @NonNull
  public static final DataType<@Nullable Byte> NULLABLE_BYTE = (
    new NullableDataType<>(new StaticGeneric<@Nullable Byte>() {}, BYTE)
  );

  @NonNull
  public static final DataType<@Nullable Character> NULLABLE_CHARACTER = (
    new NullableDataType<>(new StaticGeneric<@Nullable Character>() {}, CHARACTER)
  );

  @NonNull
  public static final DataType<@Nullable Double> NULLABLE_DOUBLE = (
    new NullableDataType<>(new StaticGeneric<@Nullable Double>() {}, DOUBLE)
  );

  @NonNull
  public static final DataType<@Nullable Float> NULLABLE_FLOAT = (
    new NullableDataType<>(new StaticGeneric<@Nullable Float>() {}, FLOAT)
  );

  @NonNull
  public static final DataType<@Nullable Integer> NULLABLE_INTEGER = (
    new NullableDataType<>(new StaticGeneric<@Nullable Integer>() {}, INTEGER)
  );

  @NonNull
  public static final DataType<@Nullable Long> NULLABLE_LONG = (
    new NullableDataType<>(new StaticGeneric<@Nullable Long>() {}, LONG)
  );

  @NonNull
  public static final DataType<@Nullable Short> NULLABLE_SHORT = (
    new NullableDataType<>(new StaticGeneric<@Nullable Short>() {}, SHORT)
  );

  /**
   * Make a non-null string type nullable.
   *
   * @param string A non-null string type to make nullable.
   *
   * @return The given string type as a nullable string.
   */
  public static @NonNull DataType<@Nullable String> nullable (
    @NonNull final DataType<@NonNull String> string
  ) { return new NullableDataType<>(Generics.NULLABLE_STRING, string); }

  /**
   * Create a non-null utf-8 string field type with the given capacity.
   *
   * @param capacity Maximum length of strings handled by this type.
   *
   * @return A non-null utf-8 string field type with the given capacity.
   */
  public static @NonNull DataType<@NonNull String> string (@NonNegative final int capacity) {
    return StringDataType.utf8(capacity);
  }

  /**
   * Create a non-null string field type with the given capacity and charset.
   *
   * @param charset Charset to use for encoding / decoding each string of this type.
   * @param capacity Maximum length of strings handled by this type.
   *
   * @return A non-null string field type with the given capacity and charset.
   */
  public static @NonNull DataType<@NonNull String> string (
    @NonNull final Charset charset,
    @NonNegative final int capacity
  ) { return new StringDataType(charset, capacity); }
}
