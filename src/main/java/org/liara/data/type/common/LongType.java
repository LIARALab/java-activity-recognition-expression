package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class LongType
  implements Type<@NonNull Long>
{
  @NonNull
  public final static Type<@NonNull Long> INSTANCE = new LongType();

  @NonNull
  private final ByteBuffer _buffer;

  public LongType () {
    _buffer = ByteBuffer.allocate(Long.BYTES);
  }

  @Override
  public @NonNull Class<@NonNull Long> getJavaClass () {
    return Long.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Long.BYTES;
  }

  @Override
  public @NonNull Long read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getLong(0);
  }

  @Override
  public void write (
    @NonNull final Long value, @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.putLong(0, value);
    stream.write(_buffer.array());
  }
}
