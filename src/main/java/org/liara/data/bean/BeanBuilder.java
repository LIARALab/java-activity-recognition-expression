package org.liara.data.bean;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.tainting.qual.Untainted;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class BeanBuilder
{
  @Nullable
  private Class<?> _beanClass;

  @NonNull
  private final Set<@NonNull String>                  _attributes;

  @NonNull
  private final Map<@NonNull String, @NonNull Method> _getters;

  @NonNull
  private final Map<@NonNull String, @NonNull Method> _setters;

  @NonNull
  private final Map<@NonNull String, @NonNull Type> _types;

  @NonNull
  private final Set<@NonNull String>                  _readonlyAttributes;

  @NonNull
  private final Map<@NonNull String, @NonNull Method> _readonlyGetters;

  @NonNull
  private final Map<@NonNull String, @NonNull Method> _readonlySetters;

  @NonNull
  private final Map<@NonNull String, @NonNull Type>  _readonlyTypes;

  /**
   * Create a new empty bean builder.
   */
  public BeanBuilder () {
    _beanClass = null;

    _attributes = new TreeSet<>();
    _getters = new HashMap<>();
    _setters = new HashMap<>();
    _types = new HashMap<>();

    _readonlyAttributes = Collections.unmodifiableSet(_attributes);
    _readonlyGetters = Collections.unmodifiableMap(_getters);
    _readonlySetters = Collections.unmodifiableMap(_setters);
    _readonlyTypes = Collections.unmodifiableMap(_types);
  }

  /**
   * Create a bean builder that is a copy of another one.
   *
   * @param toCopy A bean builder instance to copy.
   */
  public BeanBuilder (@NonNull final BeanBuilder toCopy) {
    _beanClass = toCopy.getBeanClass();

    _attributes = new TreeSet<>(toCopy.getAttributes());
    _getters = new HashMap<>(toCopy.getGetters());
    _setters = new HashMap<>(toCopy.getSetters());
    _types = new HashMap<>(toCopy.getTypes());

    _readonlyAttributes = Collections.unmodifiableSet(_attributes);
    _readonlyGetters = Collections.unmodifiableMap(_getters);
    _readonlySetters = Collections.unmodifiableMap(_setters);
    _readonlyTypes = Collections.unmodifiableMap(_types);
  }

  /**
   * Capture information about the given bean class in order to build a bean object.
   *
   * @param beanClass A bean class to capture.
   */
  public void capture (@NonNull final Class<?> beanClass) {
    clear();

    _beanClass = beanClass;

    for (@NonNull final Method method : beanClass.getMethods()) {
      if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
        if (isGetter(method)) {
          captureGetter(method);
        } else if (isSetter(method)) {
          captureSetter(method);
        }
      }
    }
  }

  /**
   * Return true if the given method is a valid getter.
   *
   * @param method A method to check.
   *
   * @return True if the given method is a valid getter.
   */
  private boolean isGetter (@NonNull final Method method) {
    return method.getParameterCount() == 0 &&
           method.getReturnType() != void.class &&
           method.getReturnType() != Void.class &&
           method.getDeclaringClass() != Object.class && (
             (method.getName().startsWith("is") && method.getName().length() > 2) ||
             (method.getName().startsWith("get") && method.getName().length() > 3)
           );
  }

  /**
   * Return true if the given method is a valid setter.
   *
   * @param method A method to check.
   *
   * @return True if the given method is a valid setter.
   */
  private boolean isSetter (@NonNull final Method method) {
    return method.getParameterCount() == 1 &&
           method.getParameterTypes()[0] != void.class &&
           method.getParameterTypes()[0] != Void.class &&
           method.getReturnType() == void.class &&
           method.getDeclaringClass() != Object.class &&
           method.getName().startsWith("set") &&
           method.getName().length() > 3;
  }

  /**
   * Capture the given method as a getter.
   *
   * The given method to capture must be a valid getter, all required assertions must be done
   * before a call to this method.
   *
   * @param method A valid getter to capture.
   */
  private void captureGetter (@Untainted @NonNull final Method method) {
    @NonNull final String attribute = getGetterAttributeName(method);

    if (_getters.containsKey(attribute)) {
      @NonNull final Method existingGetter = _getters.get(attribute);

      throw new Error(
        "Unable to capture getter of attribute " + attribute + " (" + method.getDeclaringClass() +
        "#" + method.getName() + "() " + method.getReturnType().getName() + ") because another " +
        "getter was already registered for this attribute (" + existingGetter.getDeclaringClass() +
        "#" + existingGetter.getName() + "() " + existingGetter.getReturnType().getName() + ")."
      );
    }

    @NonNull final Type type = new BeanPropertyType(method.getAnnotatedReturnType());

    if (_types.containsKey(attribute) && !_types.get(attribute).equals(type)) {
      throw new Error(
        "Unable to capture getter of attribute " + attribute + " (" + method.getDeclaringClass() +
        "#" + method.getName() + "() " + method.getReturnType().getName() + ") because the " +
        "declared getter type does not match its setter type that is \"" +
        _types.get(attribute).getTypeName() + "\"."
      );
    }

    _attributes.add(attribute);
    _getters.put(attribute, method);
    _types.put(attribute, type);
  }

  /**
   * Return the attribute name of the given getter method.
   *
   * The method to process must be a valid getter, all required assertions must be done
   * before a call to this method.
   *
   * @param method A valid getter to transform.
   *
   * @return The attribute name of the given getter method.
   */
  private @NonNull String getGetterAttributeName (@Untainted @NonNull final Method method) {
    if (method.getName().startsWith("is")) {
      return Character.toLowerCase(method.getName().charAt(2)) + method.getName().substring(3);
    } else {
      return Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4);
    }
  }

  /**
   * Capture the given method as a setter.
   *
   * The given method to capture must be a valid setter, all required assertions must be done
   * before a call to this method.
   *
   * @param method A valid setter to capture.
   */
  private void captureSetter (@NonNull final Method method) {
    @NonNull final String attribute = getSetterAttributeName(method);

    if (_setters.containsKey(attribute)) {
      @NonNull final Method existingSetter = _setters.get(attribute);

      throw new Error(
        "Unable to capture setter of attribute " + attribute + " (" + method.getDeclaringClass() +
        "#" + method.getName() + " : " + method.getParameterTypes()[0].getName() + ") because " +
        "another setter was already registered for this attribute (" +
        existingSetter.getDeclaringClass() + "#" + existingSetter.getName() + "(" +
        existingSetter.getParameterTypes()[0].getName() + "))."
      );
    }

    @NonNull final Type type = new BeanPropertyType(method.getAnnotatedParameterTypes()[0]);

    if (_types.containsKey(attribute) && !_types.get(attribute).equals(type)) {
      throw new Error(
        "Unable to capture setter of attribute " + attribute + " (" + method.getDeclaringClass() +
        "#" + method.getName() + "(" + method.getParameterTypes()[0].getName() + ")) because the " +
        "declared setter type does not match its getter type that is \"" +
        _types.get(attribute).getTypeName() + "\"."
      );
    }

    _attributes.add(attribute);
    _setters.put(attribute, method);
    _types.put(attribute, type);
  }

  /**
   * Return the attribute name of the given setter method.
   *
   * The method to process must be a valid setter, all required assertions must be done
   * before a call to this method.
   *
   * @param method A valid setter to transform.
   *
   * @return The attribute name of the given setter method.
   */
  private @NonNull String getSetterAttributeName (@NonNull final Method method) {
    return Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4);
  }

  /**
   * @return A readonly set of attributes name declared by the captured bean.
   */
  public @NonNull Set<@NonNull String> getAttributes () {
    return _readonlyAttributes;
  }

  /**
   * @return A readonly map of attributes getter declared by the captured bean.
   */
  public @NonNull Map<@NonNull String, @NonNull Method> getGetters () {
    return _readonlyGetters;
  }

  /**
   * @return A readonly map of attributes setter declared by the captured bean.
   */
  public @NonNull Map<@NonNull String, @NonNull Method> getSetters () {
    return _readonlySetters;
  }

  /**
   * @return A readonly map of attributes type declared by the captured bean.
   */
  public @NonNull Map<@NonNull String, @NonNull Type> getTypes () {
    return _readonlyTypes;
  }

  /**
   * @return The bean class at the origin of this builder state.
   */
  public @Nullable Class<?> getBeanClass () {
    return _beanClass;
  }

  /**
   * Clear this builder inner state.
   */
  public void clear () {
    _beanClass = null;
    _attributes.clear();
    _getters.clear();
    _setters.clear();
    _types.clear();
  }
}
