package org.liara.support;

import java.util.Comparator;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BaseComparators {

  @NonNull
  public static final Comparator<@NonNull String> STRING_COMPARATOR = (
      Comparator.comparing((@NonNull final String x) -> x)
  );

}
