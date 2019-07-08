package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;

public class BooleanType implements Type<@NonNull Boolean>
{
  @NonNull
  public static final Type<@NonNull Boolean> INSTANCE = new BooleanType();

  private final byte[] _buffer;

  public BooleanType () {
    _buffer = new byte[1];
  }

  @Override
  public @NonNull Class<Boolean> getJavaClass () {
    return Boolean.class;
  }

  @Override
  public @NonNegative int getSize () {
    return 1;
  }

  @Override
  public @NonNull Boolean read (@NonNull final BinaryInputStream stream) throws IOException {
    stream.read(_buffer);
    return _buffer[0] > 0;
  }

  @Override
  public void write (
    @NonNull final Boolean value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer[0] = (byte) (value ? 1 : 0);
    stream.write(_buffer);
  }
}
