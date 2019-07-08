package org.liara.data.structure;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.bean.Bean;
import org.liara.data.type.Type;
import org.liara.data.type.resolver.TypeResolver;
import org.liara.support.view.View;

import java.util.Collection;
import java.util.Iterator;

public interface Structure
{
  static @NonNull Structure forward (
    @NonNull final Bean bean,
    @NonNull final TypeResolver resolver
  ) {
    @NonNull final Type[] types = new Type[bean.countGetters()];
    @NonNegative int cursor = 0;

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.getGetters().get(index) != null) {
        types[cursor] = resolver.resolveOrThrow(bean.getTypes().get(index));
        cursor += 1;
      }
    }

    return of(types);
  }

  static @NonNull Structure backward (
    @NonNull final Bean bean,
    @NonNull final TypeResolver resolver
  ) {
    @NonNull final Type[] types = new Type[bean.countGetters()];
    @NonNegative int cursor = bean.countGetters() - 1;

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.getGetters().get(index) != null) {
        types[cursor] = resolver.resolveOrThrow(bean.getTypes().get(index));
        cursor -= 1;
      }
    }

    return of(types);
  }

  /**
   * Create a structure with the given types.
   *
   * @param types Type to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Structure of (@NonNull final Type ...types) {
    return new StaticStructure(types);
  }

  /**
   * Create a structure with the given types.
   *
   * @param types Type to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Structure of (@NonNull final Iterator<@NonNull Type> types) {
    return new StaticStructure(types);
  }

  /**
   * Create a structure with the given types.
   *
   * @param types Type to add to the structure to create.
   *
   * @return A structure with the given types.
   */
  static @NonNull Structure of (@NonNull final Collection<@NonNull Type> types) {
    return new StaticStructure(types);
  }

  /**
   * Concatenate the given structures into a unique one.
   *
   * @param structures Structures to concatenate.
   *
   * @return A new static structure that is the concatenation of the given structures.
   */
  static @NonNull Structure concatenate (@NonNull final Structure ...structures) {
    if (structures.length <= 0) return new StaticStructure();
    else if (structures.length == 1) return structures[0];

    @NonNull final Type[] types = new Type[countFieldsOf(structures)];
    @NonNegative int offset = 0;

    for (int sindex = 0, ssize = structures.length; sindex < ssize; ++sindex) {
      @NonNull final Structure structure = structures[sindex];
      @NonNull final View<@NonNull Type> fields = structure.getFields();

      for (int index = 0, size = fields.getSize(); index < size; ++index) {
        types[index + offset] = fields.get(index);
      }

      offset += fields.getSize();
    }

    return new StaticStructure(types);
  }

  /**
   * Return the number of fields of the given structures.
   *
   * @param structures Structures to count.
   *
   * @return The number of fields of the given structures.
   */
  static @NonNegative int countFieldsOf (@NonNull final Structure ...structures) {
    @NonNegative int fields = 0;

    for (int index = 0, size = structures.length; index < size; ++index) {
      fields += structures[index].getFields().getSize();
    }

    return fields;
  }

  /**
   * @return The size of this structure in bytes.
   */
  @NonNegative int getSize ();

  /**
   * @return A view over each offsets of this structure.
   */
  @NonNull View<@NonNegative @NonNull Integer> getOffsets ();

  /**
   * @return A view over each fields of this structure.
   */
  @NonNull View<@NonNull Type> getFields ();
}
