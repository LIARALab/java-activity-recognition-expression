package org.liara.support;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Comparator;

public final class BaseComparators
{
  @NonNull
  public static final Comparator<@NonNull String> STRING_COMPARATOR = (
    Comparator.comparing((@NonNull final String x) -> x)
  );

}
