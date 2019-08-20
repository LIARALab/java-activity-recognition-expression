package org.liara.data.blueprint;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

/**
 * An object that describe a static and predefined data structure. A blueprint describe a
 * composition of objects, fields or lists that are generic concepts extracted from different data
 * representation tools like json, yaml or xml.
 *
 * A blueprint can be used to map rows of data to a json, yaml or xml flux or to initialize a bean
 * instance, it can also be used as a tool for rendering documentation.
 *
 * A blueprint intent to be a tool for storing and referencing geographical information about a data
 * structure.
 */
public interface Blueprint {

  /**
   * @return A view over each elements of this model.
   */
  @NonNull View<@NonNull BlueprintElement> getElements();

  /**
   * Return the parent element of a given model element if any.
   *
   * @param element The element to check.
   * @return The parent element of the given model element if any, null otherwise.
   */
  @Nullable BlueprintElement getParentOf(@NonNull BlueprintElement element);

  /**
   * @return The root element of this model.
   */
  @NonNull BlueprintElement getRootElement();
}
