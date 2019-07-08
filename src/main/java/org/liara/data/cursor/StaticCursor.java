package org.liara.data.cursor;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.Data;

import java.io.IOException;

public class StaticCursor implements Cursor
{
  @NonNegative
  private int _offset;

  @NonNull
  private Data _target;

  public StaticCursor (@NonNull final Data target) {
    _target = target;
  }

  @Override
  public @NonNegative int getOffset () {
    return _offset;
  }

  @Override
  public void setOffset (@NonNegative final int offset) {
    _offset = offset;
  }

  @Override
  public @NonNull Data getTarget () {
    return _target;
  }

  @Override
  public void setTarget (@NonNull final Data target) {
    _offset = 0;
    _target = target;
  }

  @Override
  public void read (final byte[] bytes)
  throws IOException {
    _offset += _target.read(_offset, bytes);
  }

  @Override
  public void write (final byte[] bytes)
  throws IOException {
    _offset += _target.write(_offset, bytes);
  }

  @Override
  public void skip (@NonNegative final int size) {
    _offset += size;
  }
}
