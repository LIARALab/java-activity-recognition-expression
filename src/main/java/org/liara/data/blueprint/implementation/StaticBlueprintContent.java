package org.liara.data.blueprint.implementation;

import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.blueprint.BlueprintElement;

public class StaticBlueprintContent {

  @NonNull
  private final ArrayList<@NonNull BlueprintElement> _elements;

  @NonNull
  private final ArrayList<@Nullable BlueprintElement> _parents;

  @NonNull
  private BlueprintElement _root;

  /**
   * Instantiate a new empty blueprint content?
   */
  public StaticBlueprintContent() {
    this(16);
  }

  /**
   * Instantiate a new empty blueprint content with a given capacity.
   *
   * @param capacity The capacity of the blueprint content to instantiate.
   */
  public StaticBlueprintContent(@NonNegative final int capacity) {
    _parents = new ArrayList<>(capacity);
    _elements = new ArrayList<>(capacity);
    _root = UndefinedBlueprintElement.INSTANCE;
  }

  /**
   * @return A mutable list of all elements of this blueprint.
   */
  public @NonNull List<@NonNull BlueprintElement> getElements() {
    return _elements;
  }

  /**
   * @return A mutable list of all parents of each elements of this blueprint.
   */
  public @NonNull List<@Nullable BlueprintElement> getParents() {
    return _parents;
  }

  /**
   * @return The root element of this blueprint.
   */
  public @NonNull BlueprintElement getRoot() {
    return _root;
  }

  /**
   * Change the root element of this blueprint.
   *
   * @param root The new root element of this blueprint.
   */
  public void setRoot(@NonNull final BlueprintElement root) {
    _root = root;
  }
}
