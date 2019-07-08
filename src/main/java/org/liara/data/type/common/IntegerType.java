package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class IntegerType implements Type<@NonNull Integer>
{
  @NonNull
  public static final Type<@NonNull Integer> INSTANCE = new IntegerType();

  @NonNull
  private final ByteBuffer _buffer;

  public IntegerType () {
    _buffer = ByteBuffer.allocate(Integer.BYTES);
  }

  @Override
  public @NonNull Class<Integer> getJavaClass () {
    return Integer.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Integer.BYTES;
  }

  @Override
  public Integer read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getInt(0);
  }

  @Override
  public void write (
    @NonNull final Integer value, @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.putInt(0, value);
    stream.write(_buffer.array());
  }
}
