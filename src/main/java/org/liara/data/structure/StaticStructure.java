package org.liara.data.structure;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.type.Type;
import org.liara.support.view.View;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class StaticStructure implements Structure
{
  /**
   * Compute the offsets of the given fields.
   *
   * @param fields An array of fields from which computing byte offsets.
   *
   * @return An array of byte offsets for each given fields.
   */
  public static int[] offsets (@NonNull final Type<?>[] fields) {
    int[] offsets = new int[fields.length];

    if (fields.length > 0) {
      offsets[0] = 0;
    }

    for (@NonNegative int index = 1, size = fields.length; index < size; ++index) {
      offsets[index] = offsets[index - 1] + fields[index - 1].getSize();
    }

    return offsets;
  }

  @NonNull
  private final Type[] _fields;

  @NonNegative
  private final int[] _offsets;

  @NonNull
  private final View<@NonNull Type> _fieldsView;

  @NonNull
  private final View<@NonNull @NonNegative Integer> _offsetsView;

  /**
   * Create a new static structure from a given iterator of fields.
   *
   * @param fields An iterator over each field to add to this structure.
   */
  public StaticStructure (@NonNull final Iterator<@NonNull Type> fields) {
    @NonNull final LinkedList<@NonNull Type> fieldList = new LinkedList<>();

    while (fields.hasNext()) fieldList.add(fields.next());

    _fields = fieldList.toArray(new Type[0]);
    _offsets = StaticStructure.offsets(_fields);
    _fieldsView = View.readonly(Type.class, _fields);
    _offsetsView = View.readonly(_offsets);
  }

  /**
   * Create a new static structure from a given collection of fields.
   *
   * @param fields A collection of fields to add to this structure.
   */
  public StaticStructure (@NonNull final Collection<@NonNull Type> fields) {
    _fields = fields.toArray(new Type[0]);
    _offsets = StaticStructure.offsets(_fields);
    _fieldsView = View.readonly(Type.class, _fields);
    _offsetsView = View.readonly(_offsets);
  }

  /**
   * Create a new static structure from a given array of fields.
   *
   * @param fields An array of fields to add to this structure.
   */
  public StaticStructure (@NonNull final Type ...fields) {
    _fields = Arrays.copyOf(fields, fields.length);
    _offsets = StaticStructure.offsets(_fields);
    _fieldsView = View.readonly(Type.class, _fields);
    _offsetsView = View.readonly(_offsets);
  }

  @Override
  public @NonNegative int getSize () {
    return _fields.length > 0 ? _offsets[_fields.length - 1] + _fields[_fields.length -1].getSize()
                              : 0;
  }

  @Override
  public @NonNull View<@NonNegative @NonNull Integer> getOffsets () {
    return _offsetsView;
  }

  @Override
  public @NonNull View<@NonNull Type> getFields () {
    return _fieldsView;
  }
}
