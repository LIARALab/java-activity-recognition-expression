package org.liara.data.type;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.common.*;

import java.io.IOException;

public interface Type<T>
{
  /**
   * @return A nullable boolean field type.
   */
  static @NonNull Type<@Nullable Boolean> nullableBoolean () {
    return NullableBooleanType.INSTANCE;
  }

  /**
   * @return A nullable byte field type.
   */
  static @NonNull Type<@Nullable Byte> nullableByte () { return NullableByteType.INSTANCE; }

  /**
   * @return A nullable double field type.
   */
  static @NonNull Type<@Nullable Double> nullableDouble () { return NullableDoubleType.INSTANCE; }

  /**
   * @return A nullable float field type.
   */
  static @NonNull Type<@Nullable Float> nullableFloat () { return NullableFloatType.INSTANCE; }

  /**
   * @return A nullable integer field type.
   */
  static @NonNull Type<@Nullable Integer> nullableInteger () {
    return NullableIntegerType.INSTANCE;
  }

  /**
   * @return A nullable long field type.
   */
  static @NonNull Type<@Nullable Long> nullableLong () { return NullableLongType.INSTANCE; }

  /**
   * @return A nullable short field type.
   */
  static @NonNull Type<@Nullable Short> nullableShort () { return NullableShortType.INSTANCE; }

  /**
   * @return A nullable character field type.
   */
  static @NonNull Type<@Nullable Character> nullableCharacter () {
    return NullableCharacterType.INSTANCE;
  }

  /**
   * Create a nullable utf-8 string field type with the given capacity.
   *
   * @param capacity Maximum length of strings handled by this type.
   *
   * @return A nullable utf-8 string field type with the given capacity.
   */
  static @NonNull Type<@Nullable String> nullableString (@NonNegative final int capacity) {
    return NullableStringType.utf8(capacity);
  }

  /**
   * @return A non-null boolean field type.
   */
  static @NonNull Type<@NonNull Boolean> nonNullBoolean () { return BooleanType.INSTANCE; }

  /**
   * @return A non-null byte field type.
   */
  static @NonNull Type<@NonNull Byte> nonNullByte () { return ByteType.INSTANCE; }

  /**
   * @return A non-null double field type.
   */
  static @NonNull Type<@NonNull Double> nonNullDouble () { return DoubleType.INSTANCE; }

  /**
   * @return A non-null float field type.
   */
  static @NonNull Type<@NonNull Float> nonNullFloat () { return FloatType.INSTANCE; }

  /**
   * @return A non-null integer field type.
   */
  static @NonNull Type<@NonNull Integer> nonNullInteger () { return IntegerType.INSTANCE; }

  /**
   * @return A non-null long field type.
   */
  static @NonNull Type<@NonNull Long> nonNullLong () { return LongType.INSTANCE; }

  /**
   * @return A non-null short field type.
   */
  static @NonNull Type<@NonNull Short> nonNullShort () { return ShortType.INSTANCE; }

  /**
   * @return A non-null char field type.
   */
  static @NonNull Type<@NonNull Character> nonNullCharacter () {
    return CharacterType.INSTANCE;
  }

  /**
   * Create a non-null utf-8 string field type with the given capacity.
   *
   * @param capacity Maximum length of strings handled by this type.
   *
   * @return A non-null utf-8 string field type with the given capacity.
   */
  static @NonNull Type<@NonNull String> nonNullString (@NonNegative final int capacity) {
    return StringType.utf8(capacity);
  }

  /**
   * @return The java class of object that can store this type.
   */
  @NonNull Class<T> getJavaClass ();

  /**
   * @return The size of this type in bytes.
   */
  @NonNegative int getSize ();

  /**
   * Read an instance of the given type from the given stream.
   *
   * @param stream A binary stream to read.
   *
   * @return An instance of this type extracted of the given stream.
   */
  T read (@NonNull final BinaryInputStream stream)
  throws IOException;

  /**
   * Write an instance of the given type into the given blob.
   *
   * @param value  The value to write.
   * @param stream A binary stream to update.
   */
  void write (final T value, @NonNull final BinaryOutputStream stream)
  throws IOException;
}
