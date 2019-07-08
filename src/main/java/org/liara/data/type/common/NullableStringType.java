package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NullableStringType
  implements Type<@Nullable String>
{
  public static @NonNull Type<@Nullable String> utf8 (@NonNegative final int capacity) {
    return new NullableStringType(StandardCharsets.UTF_8, capacity);
  }

  @NonNegative
  private final int _capacity;

  @NonNull
  private final Charset _charset;

  @NonNull
  private final CharsetEncoder _encoder;

  @NonNull
  private final CharsetDecoder _decoder;

  @NonNull
  private final ByteBuffer _buffer;

  @NonNull
  private final CharBuffer _charBuffer;

  public NullableStringType (
    @NonNull final Charset charset,
    @NonNegative final int capacity
  ) {
    _capacity = capacity;
    _charset = charset;
    _encoder = _charset.newEncoder();
    _decoder = _charset.newDecoder();
    _buffer = ByteBuffer.allocate(
      (int) Math.ceil(_encoder.maxBytesPerChar() * (_capacity + 1)) + 1
    );
    _charBuffer = CharBuffer.allocate(capacity);
  }

  @Override
  public @NonNull Class<String> getJavaClass () {
    return String.class;
  }

  @Override
  public @NonNegative int getSize () {
    return _buffer.array().length;
  }

  @Override
  public @Nullable String read (@NonNull final BinaryInputStream stream) throws IOException {
    stream.read(_buffer.array());

    if (_buffer.get(0) == 0) {
      return null;
    } else {
      _decoder.decode(_buffer, _charBuffer, true);
      return _charBuffer.toString();
    }
  }

  @Override
  public void write (
    @Nullable final String value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer.clear();
    _charBuffer.clear();

    if (value == null) {
      _buffer.put(0, (byte) 0);
    } else {
      _buffer.put(0, (byte) 1);
      _charBuffer.put(value, 0, value.length());
      _encoder.encode(_charBuffer, _buffer, true);
    }

    stream.write(_buffer.array());
  }

  public @NonNegative int getCapacity () {
    return _capacity;
  }

  public @NonNull Charset getCharset () {
    return _charset;
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof NullableStringType) {
      @NonNull final NullableStringType otherNullableStringType = (NullableStringType) other;

      return (
        Objects.equals(
          _capacity,
          otherNullableStringType.getCapacity()
        ) &&
        Objects.equals(
          _charset,
          otherNullableStringType.getCharset()
        )
      );
    }

    return false;
  }

  @Override
  public int hashCode () {
    return Objects.hash(_capacity, _charset);
  }
}
