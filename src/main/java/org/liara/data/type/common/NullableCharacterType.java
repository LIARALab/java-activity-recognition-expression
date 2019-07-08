package org.liara.data.type.common;


import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NullableCharacterType
  implements Type<@Nullable Character>
{
  @NonNull
  public final static Type<@Nullable Character> INSTANCE = new NullableCharacterType();

  @NonNull
  private final ByteBuffer _buffer;

  public NullableCharacterType () {
    _buffer = ByteBuffer.allocate(Character.BYTES + 1);
  }

  @Override
  public @NonNull Class<Character> getJavaClass () {
    return Character.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Character.BYTES + 1;
  }

  @Override
  public @Nullable Character read (@NonNull final BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.get(0) == 0 ? null : _buffer.getChar(1);
  }

  @Override
  public void write (
    @Nullable final Character value,
    @NonNull final BinaryOutputStream stream
  )
  throws IOException {
    _buffer.put(0, (byte) (value == null ? 0 : 1));
    _buffer.putChar(1, value == null ? 0 : value);
    stream.write(_buffer.array());
  }
}
