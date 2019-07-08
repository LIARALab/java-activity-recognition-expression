package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableIntegerType
  implements Type<@Nullable Integer>
{
  @NonNull
  public final static Type<@Nullable Integer> INSTANCE = new NullableIntegerType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableIntegerType () {
    _buffer = ByteBuffer.allocate(Integer.BYTES + 1);
  }

  @Override
  public @NonNull Class<Integer> getJavaClass () {
    return Integer.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Integer.BYTES + 1;
  }

  @Override
  public @Nullable Integer read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getInt(1);
  }

  @Override
  public void write (
    @Nullable final Integer value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putInt(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
