package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ShortType
  implements Type<@NonNull Short>
{
  @NonNull
  public final static Type<@NonNull Short> INSTANCE = new ShortType();

  @NonNull
  private final ByteBuffer _buffer;

  public ShortType () {
    _buffer = ByteBuffer.allocate(Short.BYTES);
  }

  @Override
  public @NonNull Class<Short> getJavaClass () {
    return Short.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Short.BYTES;
  }

  @Override
  public @NonNull Short read (final @NonNull BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getShort(0);
  }

  @Override
  public void write (
    @NonNull final Short value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer.putShort(0, value);
    stream.write(_buffer.array());
  }
}
