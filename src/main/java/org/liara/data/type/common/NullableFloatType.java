package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableFloatType
  implements Type<@Nullable Float>
{
  @NonNull
  public final static Type<@Nullable Float> INSTANCE = new NullableFloatType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableFloatType () {
    _buffer = ByteBuffer.allocate(Float.BYTES + 1);
  }

  @Override
  public @NonNull Class<Float> getJavaClass () {
    return Float.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Float.BYTES + 1;
  }

  @Override
  public @Nullable Float read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getFloat(1);
  }

  @Override
  public void write (
    @Nullable final Float value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putFloat(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
