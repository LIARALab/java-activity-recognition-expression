package org.liara.data.type.common;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.stream.BinaryInputStream;
import org.liara.data.stream.BinaryOutputStream;
import org.liara.data.type.Type;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CharacterType
  implements Type<@NonNull Character>
{
  @NonNull
  public static final Type<@NonNull Character> INSTANCE = new CharacterType();

  @NonNull
  private final ByteBuffer _buffer;

  public CharacterType () {
    _buffer = ByteBuffer.allocate(Character.BYTES);
  }

  @Override
  public @NonNull Class<Character> getJavaClass () {
    return Character.class;
  }

  @Override
  public @NonNegative int getSize () {
    return Character.BYTES;
  }

  @Override
  public @NonNull Character read (final @NonNull BinaryInputStream stream)
  throws IOException {
    stream.read(_buffer.array());
    return _buffer.getChar(0);
  }

  @Override
  public void write (
    @NonNull final Character value,
    @NonNull final BinaryOutputStream stream
  ) throws IOException {
    _buffer.putChar(0, value);
    stream.write(_buffer.array());
  }
}
