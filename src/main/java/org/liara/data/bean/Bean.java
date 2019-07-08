package org.liara.data.bean;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.support.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class Bean
{
  @NonNull
  private final Class _beanClass;

  @NonNull
  private final String[] _attributes;

  @NonNull
  private final View<@NonNull String> _attributesView;

  @NonNull
  private final View<@NonNull Type> _typesView;

  @NonNull
  private final View<@Nullable Method> _gettersView;

  @NonNull
  private final View<@Nullable Method> _settersView;

  /**
   * Instantiate a new bean object from an existing java class.
   *
   * @param beanClass A java class to use in order to create the new bean object.
   *
   * @return A new bean object that describe the given class.
   */
  public static @NonNull Bean instantiate (@NonNull final Class<?> beanClass) {
    @NonNull final BeanBuilder builder = new BeanBuilder();
    builder.capture(beanClass);

    return new Bean(builder);
  }

  /**
   * Instantiate a bean by using a builder.
   *
   * @param builder A builder to use for initializing this bean object.
   */
  public Bean (@NonNull final BeanBuilder builder) {
    _beanClass = Objects.requireNonNull(builder.getBeanClass());
    _attributes = builder.getAttributes().toArray(new String[0]);
    @NonNull final Type[] types = new Type[_attributes.length];
    @NonNull final Method[] getters = new Method[_attributes.length];
    @NonNull final Method[] setters = new Method[_attributes.length];

    for (int index = 0, size = _attributes.length; index < size; ++index) {
      @NonNull final String attribute = _attributes[index];

      types[index] = builder.getTypes().get(attribute);
      getters[index] = builder.getGetters().getOrDefault(attribute, null);
      setters[index] = builder.getSetters().getOrDefault(attribute, null);
    }

    _attributesView = View.readonly(String.class, _attributes);
    _typesView = View.readonly(Type.class, types);
    _gettersView = View.readonly(Method.class, getters);
    _settersView = View.readonly(Method.class, setters);
  }

  /**
   * Return true if this bean has the given attribute.
   *
   * @param name Name of the attribute to search.
   *
   * @return True if this bean has the given attribute.
   */
  public boolean hasAttribute (@NonNull final String name) {
    return Arrays.binarySearch(_attributes, name) >= 0;
  }

  /**
   * Return the index of the given attribute or a negative integer if the given attribute does
   * not exists.
   *
   * @param name Name of the attribute to search.
   *
   * @return the index of the given attribute or a negative integer if the given attribute does not exists.
   */
  public int getAttributeIndex (@NonNull final String name) {
    return Arrays.binarySearch(_attributes, name);
  }

  /**
   * Return the type of the given attribute.
   *
   * @param name Attribute to search.
   *
   * @return The type of the given attribute.
   */
  public @NonNull Type getTypeOf (@NonNull final String name) {
    return _typesView.get(getAttributeIndex(name));
  }

  /**
   * Return the getter of the given attribute if exists, null otherwise.
   *
   * @param name Attribute to search.
   *
   * @return The getter of the given attribute if exists, null otherwise.
   */
  public @Nullable Method getGetterOf (@NonNull final String name) {
    return _gettersView.get(getAttributeIndex(name));
  }

  /**
   * Return the setter of the given attribute if exists, null otherwise.
   *
   * @param name Attribute to search.
   *
   * @return The setter of the given attribute if exists, null otherwise.
   */
  public @Nullable Method getSetterOf (@NonNull final String name) {
    return _settersView.get(getAttributeIndex(name));
  }

  /**
   * Return true if a getter exists for the given attribute.
   *
   * @param name Attribute to search.
   *
   * @return True if a getter exists for the given attribute.
   */
  public boolean isReadable (@NonNull final String name) {
    return _gettersView.get(getAttributeIndex(name)) != null;
  }

  /**
   * Return true if a getter exists for the given attribute.
   *
   * @param attribute Identifier of the attribute to check.
   *
   * @return True if a getter exists for the given attribute.
   */
  public boolean isReadable (@NonNegative final int attribute) {
    return _gettersView.get(attribute) != null;
  }

  /**
   * Return true if a setter exists for the given attribute.
   *
   * @param name Attribute to search.
   *
   * @return True if a setter exists for the given attribute.
   */
  public boolean isWritable (@NonNull final String name) {
    return _settersView.get(getAttributeIndex(name)) != null;
  }

  /**
   * Return true if a setter exists for the given attribute.
   *
   * @param attribute Identifier of the attribute to check.
   *
   * @return True if a setter exists for the given attribute.
   */
  public boolean isWritable (@NonNegative final int attribute) {
    return _settersView.get(attribute) != null;
  }

  /**
   * Return the value of the given attribute for the given instance.
   *
   * @param instance Instance to manipulate.
   * @param attribute Attribute to get.
   *
   * @return The value of the requested attribute.
   */
  public @Nullable Object get (@NonNull final Object instance, @NonNull final String attribute) {
    return get(instance, getAttributeIndex(attribute));
  }

  /**
   * Return the value of the given attribute for the given instance.
   *
   * @param instance Instance to manipulate.
   * @param attribute Attribute to get.
   *
   * @return The value of the requested attribute.
   */
  public @Nullable Object get (@NonNull final Object instance, @NonNegative int attribute) {
    @Nullable final Method getter = _gettersView.get(attribute);

    if (getter == null) {
      throw new Error(
        "Unable to get attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() +
        " because the given attribute is not readable."
      );
    }

    try {
      return getter.invoke(instance);
    } catch (@NonNull final IllegalAccessException exception) {
      throw new Error(
        "Unable to get attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() +
        " because the given getter can't be accessed.", exception
      );
    } catch (@NonNull final InvocationTargetException exception) {
      throw new Error(
        "Unable to get attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() +
        " due to a nested exception thrown during the getter invocation.", exception
      );
    }
  }

  /**
   * Update the value of the given attribute for the given instance.
   *
   * @param instance Instance to manipulate.
   * @param attribute Attribute to set.
   * @param value Value to set.
   */
  public void set (
    @NonNull final Object instance,
    @NonNull final String attribute,
    final Object value
  ) {
    set(instance, getAttributeIndex(attribute), value);
  }


  /**
   * Update the value of the given attribute for the given instance.
   *
   * @param instance Instance to manipulate.
   * @param attribute Attribute to set.
   * @param value Value to set.
   */
  public void set (@NonNull final Object instance, @NonNegative int attribute, final Object value) {
    @Nullable final Method setter = _settersView.get(attribute);

    if (setter == null) {
      throw new Error(
        "Unable to set attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() + " to " +
        Objects.toString(value) + " because the given attribute is not writable."
      );
    }

    try {
      setter.invoke(instance, value);
    } catch (@NonNull final IllegalAccessException exception) {
      throw new Error(
        "Unable to set attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() + " to " +
        Objects.toString(value) + " because the " +
        "given setter can't be accessed.", exception
      );
    } catch (@NonNull final InvocationTargetException exception) {
      throw new Error(
        "Unable to set attribute " + _attributes[attribute] + " of type " +
        _typesView.get(attribute).getTypeName() + " from object " + instance.toString() + " to " +
        Objects.toString(value) + " due to a nested exception thrown during the setter invocation.",
        exception
      );
    }
  }

  /**
   * @return A view over each attribute declared in this bean.
   */
  public @NonNull View<@NonNull String> getAttributes () {
    return _attributesView;
  }

  /**
   * @return A view over each type declared in this bean.
   */
  public @NonNull View<@NonNull Type> getTypes () {
    return _typesView;
  }

  /**
   * @return A view over each getter declared in this bean.
   */
  public @NonNull View<@Nullable Method> getGetters () {
    return _gettersView;
  }

  /**
   * @return A view over each setter declared in this bean.
   */
  public @NonNull View<@Nullable Method> getSetters () {
    return _settersView;
  }

  public @NonNegative int countGetters () {
    int result = 0;

    for (int index = 0, size = _gettersView.getSize(); index < size; ++index) {
      if(_gettersView.get(index) != null) result += 1;
    }

    return result;
  }

  public @NonNegative int countSetters () {
    int result = 0;

    for (int index = 0, size = _settersView.getSize(); index < size; ++index) {
      if(_settersView.get(index) != null) result += 1;
    }

    return result;
  }

  /**
   * @return The java class described by this bean object.
   */
  public @NonNull Class<?> getBeanClass () {
    return _beanClass;
  }

  @Override
  public boolean equals (@Nullable final Object other) {
    if (other == null) return false;
    if (other == this) return true;

    if (other instanceof Bean) {
      @NonNull final Bean otherBean = (Bean) other;

      return Objects.equals(_beanClass, otherBean.getBeanClass());
    }

    return false;
  }

  @Override
  public int hashCode () {
    return Objects.hash(_beanClass);
  }
}
