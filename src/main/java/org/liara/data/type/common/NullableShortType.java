package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableShortType
  implements Type<@Nullable Short>
{
  @NonNull
  public final static Type<@Nullable Short> INSTANCE = new NullableShortType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableShortType () {
    _buffer = ByteBuffer.allocate(Short.BYTES + 1);
  }

  @Override
  public @NonNull Class<Short> getJavaClass () {
    return Short.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Short.BYTES + 1;
  }

  @Override
  public @Nullable Short read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getShort(1);
  }

  @Override
  public void write (
    @Nullable final Short value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putShort(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
