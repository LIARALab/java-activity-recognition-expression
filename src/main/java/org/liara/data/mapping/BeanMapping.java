package org.liara.data.mapping;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.bean.Bean;

import java.util.Arrays;
import java.util.Objects;

public class BeanMapping implements Mapping<String>
{
  @NonNull
  private static BeanMappingBuilder BUILDER = new BeanMappingBuilder();

  public static @NonNull BeanMapping forward (@NonNull final Bean bean) {
    BUILDER.setBean(bean);

    @NonNegative int cursor = 0;

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.getGetters().get(index) != null) {
        BUILDER.map(index, cursor);
        cursor += 1;
      }
    }

    return BUILDER.build();
  }

  public static @NonNull BeanMapping backward (@NonNull final Bean bean) {
    BUILDER.setBean(bean);

    @NonNegative int cursor = bean.countGetters() - 1;

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.getGetters().get(index) != null) {
        BUILDER.map(index, cursor);
        cursor -= 1;
      }
    }

    return BUILDER.build();
  }

  @NonNull
  private final Bean _bean;

  private final int[] _forward;

  private final int[] _backward;

  @NonNegative
  private final int _size;

  public BeanMapping (@NonNull final BeanMappingBuilder builder) {
    _bean = Objects.requireNonNull(builder.getBean());

    _forward = new int[_bean.getAttributes().getSize()];
    _backward = new int[builder.getMaximumFieldIndex() + 1];

    @NonNegative int mapped = 0;

    Arrays.fill(_backward, -1);

    for (int index = 0, size = builder.getMapping().size(); index < size; ++index) {
      @NonNegative @Nullable final Integer value = builder.getMapping().get(index);

      if (value != null) {
        _forward[index] = value;
        _backward[value] = index;
        mapped += 1;
      } else {
        _forward[index] = -1;
      }
    }

    _size = mapped;
  }

  @Override
  public @NonNegative int getField (@NonNull final String attribute) {
    return _forward[_bean.getAttributeIndex(attribute)];
  }

  public @NonNegative int getField (@NonNegative final int attribute) {
    return _forward[attribute];
  }

  @Override
  public @NonNull String getValue (@NonNegative final int field) {
    return _bean.getAttributes().get(_backward[field]);
  }

  @Override
  public boolean isValueMapped (@NonNull final String attribute) {
    return _forward[_bean.getAttributeIndex(attribute)] >= 0;
  }

  public boolean isValueMapped (@NonNegative final int attribute) {
    return _forward[attribute] >= 0;
  }

  @Override
  public boolean isFieldMapped (@NonNegative final int field) {
    return field < _backward.length && _backward[field] >= 0;
  }

  public @NonNull Bean getBean () {
    return _bean;
  }

  @Override
  public @NonNegative int getSize () {
    return _size;
  }

  @Override
  public @NonNull Class<String> getMappedClass () {
    return String.class;
  }
}
