package org.liara.data.mapping;

import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.mapping.implementation.ImmutableMapping;
import org.liara.data.mapping.implementation.MutableMapping;
import org.liara.support.view.View;

/**
 * A mapping of an external data structure.
 */
public interface Mapping {
  /**
   * @return A new mutable mapping instance.
   */
  static @NonNull MutableMapping mutable() {
    return new MutableMapping();
  }

  /**
   * Instantiate a new immutable mapping instance from another mapping instance.
   *
   * @param mapping A mapping instance to copy as an immutable one.
   *
   * @return A new immutable mapping instance that are equivalent to the given one.
   */
  static @NonNull Mapping immutable (@NonNull final Mapping mapping) {
    return mapping instanceof ImmutableMapping ? mapping : new ImmutableMapping(mapping);
  }

  /**
   * @return The name associated to this mapping.
   */
  @NonNull String getName();

  /**
   * @return A view over all mapped structures.
   */
  @NonNull View<@NonNull ? extends Structure> getStructures();

  /**
   * @return A view over all mapped fields.
   */
  @NonNull View<@NonNull ? extends Field> getFields();

  /**
   * Return the mapped structure with the given name.
   *
   * @param name Name of the structure to retrieve.
   * @return The mapped structure with the given name.
   *
   * @throws NoSuchElementException If no mapped structure exists with the given name.
   */
  @NonNull Structure getStructureWithName(@NonNull final String name) throws NoSuchElementException;

  /**
   * Return the mapped structure with the given identifier.
   *
   * @param identifier Identifier of the structure to retrieve.
   * @return The mapped structure with the given identifier.
   *
   * @throws NoSuchElementException If no mapped structure exists with the given identifier.
   */
  @NonNull Structure getStructureWithIdentifier(@NonNegative final int identifier) throws NoSuchElementException;

  /**
   * Return a mapped field with the given identifier.
   *
   * @param identifier Identifier of the field to retrieve.
   * @return The mapped field with the given identifier.
   *
   * @throws NoSuchElementException If no mapped field exists with the given identifier.
   */
  @NonNull Field getFieldWithIdentifier(
      @NonNegative final int identifier
  ) throws NoSuchElementException;

  /**
   * Return true if a structure with the given name exists.
   *
   * @param name Name of the structure to search.
   * @return True if a structure with the given name exists.
   */
  boolean containsStructureWithName(@NonNull final String name);

  /**
   * Return true if a structure with the given identifier exists.
   *
   * @param identifier Identifier of the structure to search.
   * @return True if a structure with the given identifier exists.
   */
  boolean containsStructureWithIdentifier(@NonNegative final int identifier);

  /**
   * Return true if a field with the given identifier exists.
   *
   * @param identifier Identifier of the field to search.
   * @return True if a field with the given identifier exists.
   */
  boolean containsFieldWithIdentifier(@NonNegative final int identifier);
}
