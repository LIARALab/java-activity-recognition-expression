package org.liara.data.mapping;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.bean.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanMappingBuilder
{
  @Nullable
  private Bean _bean;

  @NonNull
  private final List<@Nullable @NonNegative Integer> _mapping;

  @NonNull
  private final List<@Nullable @NonNegative Integer> _readonlyMapping;

  public BeanMappingBuilder () {
    _mapping = new ArrayList<>();
    _readonlyMapping = Collections.unmodifiableList(_mapping);
    _bean = null;
  }

  public @NonNull BeanMapping build () {
    return new BeanMapping(this);
  }

  public void unmap (@NonNull final String attribute) {
    if (_bean == null) {
      throw new Error(
        "Unable to unmap the bean attribute \"" + attribute + "\" because no bean object were set."
      );
    }

    if (!_bean.hasAttribute(attribute)) {
      throw new Error(
        "Unable to unmap the bean attribute \"" + attribute +
        "\" because the given attribute does not exists."
      );
    }

    _mapping.set(_bean.getAttributeIndex(attribute), null);
  }

  public void unmap (@NonNegative final int attribute, @NonNegative final int field) {
    if (_bean == null) {
      throw new Error(
        "Unable to unmap the " + attribute + "th bean attribute because no bean object were set."
      );
    }

    if (_bean.getAttributes().getSize() >= attribute) {
      throw new Error(
        "Unable to unmap the " + attribute +
        "th bean attribute because the given attribute does not exists."
      );
    }

    _mapping.set(attribute, null);
  }

  public void map (@NonNull final String attribute, @NonNegative final int field) {
    if (_bean == null) {
      throw new Error(
        "Unable to map the bean attribute \"" + attribute + "\" to the " + field + "th" +
        " structure field because no bean object were set."
      );
    }

    if (!_bean.hasAttribute(attribute)) {
      throw new Error(
        "Unable to map the bean attribute \"" + attribute + "\" to the " + field + "th" +
        " structure field because the given attribute does not exists."
      );
    }

    _mapping.set(_bean.getAttributeIndex(attribute), field);
  }

  public void map (@NonNegative final int attribute, @NonNegative final int field) {
    if (_bean == null) {
      throw new Error(
        "Unable to map the " + attribute + "th bean attribute to the " + field + "th" +
        " structure field because no bean object were set."
      );
    }

    if (_bean.getAttributes().getSize() >= attribute) {
      throw new Error(
        "Unable to map the " + attribute + "th bean attribute to the " + field + "th" +
        " structure field because the given attribute does not exists."
      );
    }

    _mapping.set(attribute, field);
  }

  /**
   * @return The bean object that is mapped.
   */
  public @Nullable Bean getBean () {
    return _bean;
  }

  /**
   * Update the mapped bean object.
   *
   * This operation will reset the current mapping configuration.
   *
   * @param bean The new bean object to map.
   */
  public void setBean (@Nullable final Bean bean) {
    _bean = bean;
    _mapping.clear();

    if (_bean != null) {
      for (int index = 0, size = _bean.getAttributes().getSize(); index < size; ++index) {
        _mapping.add(null);
      }
    }
  }

  /**
   * @return The current attribute mapping.
   */
  public @NonNull List<@Nullable @NonNegative Integer> getMapping () {
    return _readonlyMapping;
  }

  public @NonNegative int getMaximumFieldIndex () {
    int result = 0;

    for (int index = 0, size = _mapping.size(); index < size; ++index) {
      @Nullable final Integer value = _mapping.get(index);
      if (value != null && value > result) {
        result = value;
      }
    }

    return result;
  }

  public void clear () {
    _bean = null;
    _mapping.clear();
  }
}
