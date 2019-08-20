package org.liara.data.type.common;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.ComparableDataType;
import org.liara.data.type.DataType;
import org.liara.support.generic.Generic;
import org.liara.support.generic.Generics;

public class StringDataType implements DataType<@NonNull String>, ComparableDataType {

  @NonNegative
  private final int _capacity;
  @NonNull
  private final Charset _charset;
  @NonNull
  private final CharsetEncoder _encoder;
  @NonNull
  private final CharsetDecoder _decoder;
  @NonNull
  private final CharBuffer _charBuffer;
  @NonNegative
  private final int _bytes;
  @NonNull
  private final Mutable<@NonNull String> _left = new MutableObject<>("");
  @NonNull
  private final Mutable<@NonNull String> _right = new MutableObject<>("");


  public StringDataType(
      @NonNull final Charset charset,
      @NonNegative final int capacity
  ) {
    _capacity = capacity;
    _charset = charset;
    _encoder = _charset.newEncoder();
    _decoder = _charset.newDecoder();
    _bytes = Integer.BYTES + (int) Math.ceil(_encoder.maxBytesPerChar() * _capacity);
    _charBuffer = CharBuffer.allocate(capacity);
  }

  public static @NonNull DataType<@NonNull String> utf8(@NonNegative final int capacity) {
    return new StringDataType(StandardCharsets.UTF_8, capacity);
  }

  /**
   * @see ComparableDataType#compare(ByteBuffer, int, ByteBuffer, int)
   */
  @Override
  public int compare(
      @NonNull final ByteBuffer leftBuffer,
      @NonNegative final int leftOffset,
      @NonNull final ByteBuffer rightBuffer,
      @NonNegative final int rightOffset
  ) {
    read(leftBuffer, leftOffset, _left);
    read(rightBuffer, rightOffset, _right);

    return _left.getValue().compareTo(_right.getValue());
  }

  /**
   * @see DataType#getGeneric()
   */
  @Override
  public @NonNull Generic<@NonNull String> getGeneric() {
    return Generics.STRING;
  }

  /**
   * @see DataType#getBytes()
   */
  @Override
  public @NonNegative int getBytes() {
    return _bytes;
  }

  /**
   * @see DataType#read(ByteBuffer, int, Mutable)
   */
  @Override
  public void read(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final Mutable<@NonNull String> output
  ) {
    _charBuffer.clear();
    _decoder.reset();

    final int oldPosition = buffer.position();
    final int oldLimit = buffer.limit();

    buffer.position(offset);
    buffer.limit(buffer.position() + Integer.BYTES);

    final int length = buffer.getInt();

    buffer.position(offset + Integer.BYTES);
    buffer.limit(buffer.position() + length);

    if (buffer.limit() <= 0) {
      output.setValue("");
    } else {
      _decoder.decode(buffer, _charBuffer, true);
      _decoder.flush(_charBuffer);
      _charBuffer.flip();
      output.setValue(_charBuffer.toString());
    }

    buffer.limit(oldLimit);
    buffer.position(oldPosition);
  }

  /**
   * @see DataType#write(ByteBuffer, int, Object)
   */
  @Override
  public void write(
      @NonNull final ByteBuffer buffer,
      @NonNegative final int offset,
      @NonNull final String value
  ) {
    if (value.length() > _capacity) {
      throw new Error(
          "Trying to write a string of length " + value.length() + " into a field that can " +
              "store only strings of length up to " + _capacity + "."
      );
    }

    final int oldPosition = buffer.position();
    final int oldLimit = buffer.limit();

    _charBuffer.clear();
    _encoder.reset();

    _charBuffer.put(value, 0, value.length());
    _charBuffer.flip();

    buffer.position(offset + Integer.BYTES);
    buffer.limit(buffer.position());

    _encoder.encode(_charBuffer, buffer, true);
    _encoder.flush(buffer);

    buffer.putInt(buffer.limit() - buffer.position(), offset);

    buffer.position(oldPosition);
    buffer.limit(oldLimit);
  }

  /**
   * @return The maximum number of character that can be stored into a value of this type.
   */
  public @NonNegative int getCapacity() {
    return _capacity;
  }

  /**
   * @return The charset used for encoding / decoding this type.
   */
  public @NonNull Charset getCharset() {
    return _charset;
  }

  @Override
  public boolean equals(@Nullable final Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }

    if (other instanceof StringDataType) {
      @NonNull final StringDataType otherStringType = (StringDataType) other;

      return (
          Objects.equals(
              _capacity,
              otherStringType.getCapacity()
          ) &&
              Objects.equals(
                  _charset,
                  otherStringType.getCharset()
              )
      );
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(_capacity, _charset);
  }
}
