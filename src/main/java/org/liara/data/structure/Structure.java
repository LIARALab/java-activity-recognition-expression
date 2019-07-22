package org.liara.data.structure;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.primitive.Primitive;
import org.liara.data.primitive.PrimitiveView;
import org.liara.support.view.View;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class Structure
{
  @NonNegative
  private final View<@NonNull Primitive> _fields;

  /**
   * Create a structure that the concatenation of other ones.
   *
   * @param structures Structures to concatenate.
   */
  public static @NonNull Structure concatenate (@NonNull final Structure ...structures) {
    @NonNull final LinkedList<@NonNegative @NonNull Primitive> primitives = new LinkedList<>();

    for (@NonNull final Structure structure : structures) {
      @NonNull final View<@NonNull Primitive> fields = structure.getFields();

      for (int index = 0, size = fields.getSize(); index < size; ++index) {
        primitives.add(fields.get(index));
      }
    }

    return new Structure(primitives);
  }

  /**
   * Create a structure that the concatenation of other ones.
   *
   * @param structures Structures to concatenate.
   */
  public static @NonNull Structure concatenate (
    @NonNull final Iterator<@NonNull Structure> structures
  ) {
    @NonNull final LinkedList<@NonNegative @NonNull Primitive> primitives = new LinkedList<>();

    while (structures.hasNext()) {
      @NonNull final Structure structure = structures.next();
      @NonNull final View<@NonNull Primitive> fields = structure.getFields();

      for (int index = 0, size = fields.getSize(); index < size; ++index) {
        primitives.add(fields.get(index));
      }
    }

    return new Structure(primitives);
  }

  /**
   * Create a structure that the concatenation of other ones.
   *
   * @param structures Structures to concatenate.
   */
  public static @NonNull Structure concatenate (
    @NonNull final List<@NonNull Structure> structures
  ) {
    @NonNull final LinkedList<@NonNegative @NonNull Primitive> primitives = new LinkedList<>();

    for (@NonNull final Structure structure : structures) {
      @NonNull final View<@NonNull Primitive> fields = structure.getFields();

      for (int index = 0, size = fields.getSize(); index < size; ++index) {
        primitives.add(fields.get(index));
      }
    }

    return new Structure(primitives);
  }

  /**
   * Create a structure that follows the given description.
   *
   * @param primitives A description of the structure to create.
   */
  public Structure (@NonNull final Primitive... primitives) {
    @NonNegative int[] raw = new int[primitives.length];

    for (int index = 0, size = primitives.length; index < size; ++index) {
      raw[index] = primitives[index].getIdentifier();
    }

    _fields = new PrimitiveView(raw);
  }

  /**
   * Create a structure that follows the given description.
   *
   * @param primitives A description of the structure to create.
   */
  public Structure (@NonNull final Iterator<@NonNull Primitive> primitives) {
    @NonNull final LinkedList<@NonNull Primitive> list = new LinkedList<>();
    primitives.forEachRemaining(list::add);

    @NonNegative int[] raw = new int[list.size()];

    for (int index = 0, size = list.size(); index < size; ++index) {
      raw[index] = list.removeFirst().getIdentifier();
    }

    _fields = new PrimitiveView(raw);
  }

  /**
   * Create a structure that follows the given description.
   *
   * @param primitives A description of the structure to create.
   */
  public Structure (@NonNull final List<@NonNull Primitive> primitives) {
    @NonNegative int[] raw = new int[primitives.size()];

    for (int index = 0, size = primitives.size(); index < size; ++index) {
      raw[index] = primitives.get(index).getIdentifier();
    }

    _fields = new PrimitiveView(raw);
  }

  /**
   * Create a structure that is a copy of an existing one.
   *
   * @param toCopy A structure to copy.
   */
  public Structure (@NonNull final Structure toCopy) {
    @NonNegative int[] raw = new int[toCopy.getFields().getSize()];

    for (int index = 0, size = toCopy.getFields().getSize(); index < size; ++index) {
      raw[index] = toCopy.getFields().get(index).getIdentifier();
    }

    _fields = new PrimitiveView(raw);
  }

  /**
   * @return A view over each fields of this structure.
   */
  public @NonNull View<@NonNull Primitive> getFields () {
    return _fields;
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof Structure) {
      @NonNull final Structure otherStructure = (Structure) other;

      return Objects.equals(_fields, otherStructure.getFields());
    }

    return false;
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode () {
    return Objects.hash(_fields);
  }
}
