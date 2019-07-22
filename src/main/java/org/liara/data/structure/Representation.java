package org.liara.data.structure;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.bean.Bean;
import org.liara.data.type.DataType;
import org.liara.data.type.resolver.TypeResolver;
import org.liara.support.view.View;

import java.util.Collection;
import java.util.Iterator;

public interface Representation
{
  /**
   * Create a structure with the given types.
   *
   * @param types DataType to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Representation of (@NonNull final DataType...types) {
    return new StaticRep(types);
  }

  /**
   * Create a structure with the given types.
   *
   * @param types DataType to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Representation of (@NonNull final Iterator<@NonNull DataType> types) {
    return new StaticRep(types);
  }

  /**
   * Create a structure with the given types.
   *
   * @param types DataType to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Representation of (@NonNull final Collection<@NonNull DataType> types) {
    return new StaticRep(types);
  }

  /**
   * Concatenate the given structures into a unique one.
   *
   * @param structures Structures to concatenate.
   *
   * @return A new static structure that is the concatenation of the given structures.
   */
  static @NonNull Representation concatenate (@NonNull final Representation...structures) {
    if (structures.length <= 0) return new StaticRep();
    else if (structures.length == 1) return structures[0];

    @NonNull final DataType[] types  = new DataType[countFieldsOf(structures)];
    @NonNegative int          offset = 0;

    for (int sindex = 0, ssize = structures.length; sindex < ssize; ++sindex) {
      @NonNull final Representation          structure = structures[sindex];
      @NonNull final View<@NonNull DataType> fields    = structure.getFields();

      for (int index = 0, size = fields.getSize(); index < size; ++index) {
        types[index + offset] = fields.get(index);
      }

      offset += fields.getSize();
    }

    return new StaticRep(types);
  }

  /**
   * Return the number of fields of the given structures.
   *
   * @param structures Structures to count.
   *
   * @return The number of fields of the given structures.
   */
  static @NonNegative int countFieldsOf (@NonNull final Representation...structures) {
    @NonNegative int fields = 0;

    for (int index = 0, size = structures.length; index < size; ++index) {
      fields += structures[index].getFields().getSize();
    }

    return fields;
  }

  /**
   * @return The number of bytes required to store this structure.
   */
  @NonNegative int getBytes ();

  /**
   * @return A view over each offsets of this structure.
   */
  @NonNull View<@NonNegative @NonNull Integer> getOffsets ();

  /**
   * @return A view over each fields of this structure.
   */
  @NonNull View<@NonNull DataType> getFields ();
}
