package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableLongType
  implements Type<@Nullable Long>
{
  @NonNull
  public final static Type<@Nullable Long> INSTANCE = new NullableLongType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableLongType () {
    _buffer = ByteBuffer.allocate(Long.BYTES + 1);
  }

  @Override
  public @NonNull Class<Long> getJavaClass () {
    return Long.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Long.BYTES + 1;
  }

  @Override
  public @Nullable Long read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getLong(1);
  }

  @Override
  public void write (
    @Nullable final Long value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putLong(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
