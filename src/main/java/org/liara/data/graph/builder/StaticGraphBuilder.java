package org.liara.data.graph.builder;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StaticGraphBuilder
{
  @NonNull
  private final List<@NonNull TableBuilder> _tables;

  @NonNull
  private final List<@NonNull String> _tablesNames;

  public StaticGraphBuilder () {
    this(16);
  }

  public StaticGraphBuilder (@NonNegative final int capacity) {
    _tables = new ArrayList<>(capacity);
    _tablesNames = new ArrayList<>(capacity);
  }


}
