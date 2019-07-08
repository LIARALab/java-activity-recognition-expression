package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableDoubleType
  implements Type<@Nullable Double>
{
  @NonNull
  public final static Type<@Nullable Double> INSTANCE = new NullableDoubleType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableDoubleType () {
    _buffer = ByteBuffer.allocate(Double.BYTES + 1);
  }

  @Override
  public @NonNull Class<Double> getJavaClass () {
    return Double.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Double.BYTES + 1;
  }

  @Override
  public @Nullable Double read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getDouble(1);
  }

  @Override
  public void write (
    @Nullable final Double value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putDouble(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
