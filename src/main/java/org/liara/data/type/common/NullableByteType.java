package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableByteType
  implements Type<@Nullable Byte>
{
  @NonNull
  public final static Type<@Nullable Byte> INSTANCE = new NullableByteType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableByteType () {
    _buffer = ByteBuffer.allocate(2);
  }

  @Override
  public @NonNull Class<Byte> getJavaClass () {
    return Byte.class;
  }

  @Override
  public @NonNegative int getSize () {
    return 2;
  }

  @Override
  public @Nullable Byte read (final @NonNull BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());

    return _buffer.get(0) == 0 ? null : _buffer.get(1);
  }

  @Override
  public void write (
    @Nullable final Byte value, final @NonNull BinaryOutputStream stream
  ) throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.put(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
