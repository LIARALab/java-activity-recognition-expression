package org.liara.data.stream;

import org.checkerframework.checker.index.qual.NonNegative;

import java.io.IOException;

public interface BinaryOutputStream
{
  void write (byte[] bytes) throws IOException;

  void skip (@NonNegative int size) throws IOException;
}
