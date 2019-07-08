package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class DoubleType
  implements Type<@NonNull Double>
{
  @NonNull
  public static final Type<@NonNull Double> INSTANCE = new DoubleType();

  @NonNull
  private final ByteBuffer _buffer;

  public DoubleType () {
    _buffer = ByteBuffer.allocate(Double.BYTES);
  }

  @Override
  public @NonNull Class<Double> getJavaClass () {
    return Double.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Double.BYTES;
  }

  @Override
  public @NonNull Double read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getDouble(0);
  }

  @Override
  public void write (
    @NonNull final Double value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.putDouble(0, value);
    stream.write(_buffer.array());
  }
}
