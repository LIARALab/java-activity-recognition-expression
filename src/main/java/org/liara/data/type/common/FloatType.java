package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FloatType
  implements Type<@NonNull Float>
{
  @NonNull
  public static final Type<@NonNull Float> INSTANCE = new FloatType();

  @NonNull
  private final ByteBuffer _buffer;

  public FloatType () {
    _buffer = ByteBuffer.allocate(Float.BYTES);
  }

  @Override
  public @NonNull Class<Float> getJavaClass () {
    return Float.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Float.BYTES;
  }

  @Override
  public @NonNull Float read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getFloat(0);
  }

  @Override
  public void write (
    @NonNull final Float value, @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer.putFloat(0, value);
    stream.write(_buffer.array());
  }
}
