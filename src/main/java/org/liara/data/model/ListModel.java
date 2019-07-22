package org.liara.data.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface ListModel extends ModelElement
{
  @NonNull ModelElement getValue ();
}
