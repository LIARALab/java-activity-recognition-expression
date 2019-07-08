package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;

public class NullableBooleanType
  implements Type<@Nullable Boolean>
{
  @NonNull
  public final static Type<@Nullable Boolean> INSTANCE = new NullableBooleanType();

  private final byte[] _buffer;

  public NullableBooleanType () {
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
  public @Nullable Boolean read (@NonNull final BinaryInputStream stream) throws IOException {
    stream.read(_buffer);
    switch (_buffer[0]) {
      case 0: return null;
      case 1: return false;
      default: return true;
    }
  }

  @Override
  public void write (
    @Nullable final Boolean value, @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer[0] = (byte) (value == null ? 0 : (value ? 2 : 1));
    stream.write(_buffer);
  }
}
