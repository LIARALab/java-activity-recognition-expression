package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;

public class ByteType
  implements Type<@NonNull Byte>
{
  @NonNull
  public static final Type<@NonNull Byte> INSTANCE = new ByteType();

  private final byte[] _buffer;

  public ByteType () {
    _buffer = new byte[1];
  }

  @Override
  public @NonNull Class<Byte> getJavaClass () {
    return Byte.class;
  }

  @Override
  public @NonNegative int getSize () {
    return 1;
  }

  @Override
  public @NonNull Byte read (final @NonNull BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer);
    return _buffer[0];
  }

  @Override
  public void write (
    @NonNull final Byte value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer[0] = value;
    stream.write(_buffer);
  }
}
