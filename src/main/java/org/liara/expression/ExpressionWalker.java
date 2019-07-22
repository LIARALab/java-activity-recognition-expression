package org.liara.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.tree.TreeWalker;

import java.util.*;

public class ExpressionWalker extends TreeWalker<Expression>
{
  public ExpressionWalker (@NonNull final Expression root) {
    super(Expression.class, root);
  }

  public ExpressionWalker (final @NonNull TreeWalker<Expression> toCopy) {
    super(toCopy);
  }
}
