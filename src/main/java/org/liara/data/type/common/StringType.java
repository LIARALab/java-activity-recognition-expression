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
import java.nio.charset.*;
import java.util.Objects;

public class StringType implements Type<@NonNull String>
{
  public static @NonNull Type<@NonNull String> utf8 (@NonNegative final int capacity) {
    return new StringType(StandardCharsets.UTF_8, capacity);
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

  public StringType (
    @NonNull final Charset charset,
    @NonNegative final int capacity
  ) {
    _capacity = capacity;
    _charset = charset;
    _encoder = _charset.newEncoder();
    _decoder = _charset.newDecoder();
    _buffer = ByteBuffer.allocate(
      Integer.BYTES + (int) Math.ceil(_encoder.maxBytesPerChar() * _capacity)
    );
    _charBuffer = CharBuffer.allocate(capacity);
  }

  @Override
  public @NonNull Class<String> getJavaClass () {
    return String.class;
  }

  @Override
  public @NonNegative int getSize () {
    return _buffer.capacity();
  }

  @Override
  public String read (@NonNull final BinaryInputStream stream) throws IOException {
    _buffer.clear();
    _charBuffer.clear();
    _decoder.reset();

    stream.read(_buffer.array());
    _buffer.limit(_buffer.getInt(0));

    if (_buffer.limit() == 0) return "";

    _buffer.position(Integer.BYTES);

    _decoder.decode(_buffer, _charBuffer, true);
    _decoder.flush(_charBuffer);
    _charBuffer.flip();

    return _charBuffer.toString();
  }

  @Override
  public void write (
    @NonNull final String value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    if (value.length() > _capacity) {
      throw new Error(
        "Trying to write a string of length " + value.length() + " into a field that can " +
        "store only strings of length up to " + _capacity + "."
      );
    }

    _buffer.clear();
    _charBuffer.clear();
    _encoder.reset();

    _charBuffer.put(value, 0, value.length());
    _charBuffer.flip();

    _buffer.putInt(0);
    _encoder.encode(_charBuffer, _buffer, true);
    _encoder.flush(_buffer);
    _buffer.flip();
    _buffer.putInt(0, _buffer.limit());

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

    if (other instanceof StringType) {
      @NonNull final StringType otherStringType = (StringType) other;

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
  public int hashCode () {
    return Objects.hash(_capacity, _charset);
  }
}
