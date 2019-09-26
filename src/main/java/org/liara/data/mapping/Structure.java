package org.liara.data.mapping;

import java.util.NoSuchElementException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.support.view.View;

/**
 * An external data structure.
 */
public interface Structure {
  /**
   * @return The parent mapping of this structure.
   */
  @NonNull Mapping getMapping();

  /**
   * @return A non-negative integer that fully identify this structure into its parent mapping.
   */
  @NonNegative int getIdentifier();

  /**
   * @return The name of this structure.
   */
  @NonNull String getName();

  /**
   * @return A view over all fields of this structure.
   */
  @NonNull View<@NonNull ? extends Field> getFields();

  /**
   * Retrieve a field of this structure by using its name.
   *
   * @param name The name of the field to retrieve.
   * @return The field with the given name.
   *
   * @throws  NoSuchElementException If no fields exists with the given name.
   */
  @NonNull Field getFieldWithName(@NonNull final String name) throws NoSuchElementException;

  /**
   * Retrieve a field of this structure by using its identifier.
   *
   * @param identifier The identifier of the field to retrieve.
   * @return The field with the given identifier.
   *
   * @throws  NoSuchElementException If no fields exists with the given identifier.
   */
  @NonNull Field getFieldWithIdentifier(@NonNegative final int identifier) throws NoSuchElementException;

  /**
   * Returns true if a field with the given name exists into this structure.
   *
   * @param name The name of the field to retrieve.
   * @return True if a field with the given name exists into this structure, false otherwise.
   */
  boolean containsFieldWithName(@NonNull final String name);

  /**
   * Returns true if a field with the given identifier exists into this structure.
   *
   * @param identifier The identifier of the field to retrieve.
   * @return True if a field with the given identifier exists into this structure, false otherwise.
   */
  boolean containsFieldWithIdentifier(@NonNegative final int identifier);
}
