package org.liara.data.model.descriptor;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liara.data.primitive.Primitive;

import java.util.*;

public class ModelDescriptorFactory
{
  /**
   * @return A description of a null value.
   */
  public @NonNull NullDescriptor empty () {
    return StaticNullDescriptor.INSTANCE;
  }

  /**
   * Instantiate a description of a placeholder that expect a primitive of the given type.
   *
   * @param type Primitive type expected at the given location.
   *
   * @return A description of a placeholder that expect a primitive of the given type.
   */
  public @NonNull ValueDescriptor value (@NonNull final Primitive<?> type) {
    return new StaticValueDescriptor(type);
  }

  /**
   * Instantiate a description of a tuple with the given children in order.
   *
   * @param children Children of the tuple description to create.
   *
   * @return A description of a tuple with the given children in order.
   */
  public @NonNull TupleDescriptor tuple (@NonNull final ModelElementDescriptor... children) {
    @NonNull final StaticTupleDescriptor descriptor = new StaticTupleDescriptor();
    descriptor.setChildren(Arrays.asList(children));
    return descriptor;
  }

  /**
   * Instantiate a description of a tuple with the given children in order.
   *
   * @param children Children of the tuple description to create.
   *
   * @return A description of a tuple with the given children in order.
   */
  public @NonNull TupleDescriptor tuple (
    @NonNull final Iterator<ModelElementDescriptor> children
  ) {
    @NonNull final StaticTupleDescriptor descriptor = new StaticTupleDescriptor();

    descriptor.setChildren(children);

    return descriptor;
  }

  /**
   * Instantiate a description of a tuple with the given children in order.
   *
   * @param children Children of the tuple description to create.
   *
   * @return A description of a tuple with the given children in order.
   */
  public @NonNull TupleDescriptor tuple (
    @NonNull final Collection<ModelElementDescriptor> children
  ) {
    @NonNull final StaticTupleDescriptor descriptor = new StaticTupleDescriptor();

    descriptor.setChildren(children);

    return descriptor;
  }

  /**
   * Instantiate a description of an object that contains the given fields in order.
   *
   * @param keys The keys that identify each field of the object descriptor to create in order.
   * @param descriptors Descriptors of each field of the object descriptor to create in order.
   *
   * @return A description of an object that contains the given fields in order.
   */
  public @NonNull ObjectDescriptor object (
    @NonNull final String[] keys,
    @NonNull final ModelElementDescriptor[] descriptors
  ) {
    if (keys.length != descriptors.length) {
      throw new IllegalArgumentException(
        "Unable to create a description of an object with the given configuration because the " +
        "array of keys and the array of descriptors of each fields of the object descriptor to " +
        "create does not have the same length (" + keys.length + " key(s) for " +
        descriptors.length + " descriptor(s))."
      );
    }

    @NonNull final StaticObjectDescriptor descriptor = new StaticObjectDescriptor();

    for (int index = 0, size = keys.length; index < size; ++index) {
      if (descriptor.containsKey(keys[index])) {
        throw new IllegalArgumentException(
          "Unable to create a description of an object with the given configuration because the " +
          "array of keys contains duplicates of the key \"" + keys[index] + "\"."
        );
      }
      descriptor.set(keys[index], descriptors[index]);
    }

    return descriptor;
  }

  /**
   * Instantiate a description of an object that contains the given fields in order.
   *
   * @param keys The keys that identify each field of the object descriptor to create in order.
   * @param descriptors Descriptors of each field of the object descriptor to create in order.
   *
   * @return A description of an object that contains the given fields in order.
   */
  public @NonNull ObjectDescriptor object (
    @NonNull final List<@NonNull String> keys,
    @NonNull final List<@NonNull ModelElementDescriptor> descriptors
  ) {
    if (keys.size() != descriptors.size()) {
      throw new IllegalArgumentException(
        "Unable to create a description of an object with the given configuration because the " +
        "array of keys and the array of descriptors of each fields of the object descriptor to " +
        "create does not have the same length (" + keys.size() + " key(s) for " +
        descriptors.size() + " descriptor(s))."
      );
    }

    @NonNull final StaticObjectDescriptor descriptor = new StaticObjectDescriptor();

    for (int index = 0, size = keys.size(); index < size; ++index) {
      if (descriptor.containsKey(keys.get(index))) {
        throw new IllegalArgumentException(
          "Unable to create a description of an object with the given configuration because the " +
          "array of keys contains duplicates of the key \"" + keys.get(index) + "\"."
        );
      }
      descriptor.set(keys.get(index), descriptors.get(index));
    }

    return descriptor;
  }

  /**
   * Instantiate a description of an object that contains the given fields in order.
   *
   * @param fields A map that describe each fields of the given object.
   *
   * @return A description of an object that contains the given fields in order.
   */
  public @NonNull ObjectDescriptor object (
    @NonNull final Map<@NonNull String, @NonNull ModelElementDescriptor> fields
  ) {
    @NonNull final StaticObjectDescriptor descriptor = new StaticObjectDescriptor();

    fields.forEach(descriptor::set);

    return descriptor;
  }

  /**
   * Instantiate a description of an object that contains the given fields in order.
   *
   * @param keys The keys that identify each field of the object descriptor to create in order.
   * @param descriptors Descriptors of each field of the object descriptor to create in order.
   *
   * @return A description of an object that contains the given fields in order.
   */
  public @NonNull ObjectDescriptor object (
    @NonNull final Iterator<@NonNull String> keys,
    @NonNull final Iterator<@NonNull ModelElementDescriptor> descriptors
  ) {
    @NonNull final StaticObjectDescriptor descriptor = new StaticObjectDescriptor();

    while (keys.hasNext()) {
      if (!descriptors.hasNext()) {
        throw new IllegalArgumentException(
          "Unable to create a description of an object with the given configuration because the " +
          "iterator of keys and the iterator of descriptors of each fields of the object " +
          "descriptor to create does not have the same length, there are more keys than " +
          "descriptors."
        );
      }

      @NonNull final String key = keys.next();

      if (descriptor.containsKey(key)) {
        throw new IllegalArgumentException(
          "Unable to create a description of an object with the given configuration because the " +
          "array of keys contains duplicates of the key \"" + key + "\"."
        );
      }

      descriptor.set(key, descriptors.next());
    }

    if (descriptors.hasNext()) {
      throw new IllegalArgumentException(
        "Unable to create a description of an object with the given configuration because the " +
        "iterator of keys and the iterator of descriptors of each fields of the object " +
        "descriptor to create does not have the same length, there are more descriptors than " +
        "keys."
      );
    }

    return descriptor;
  }
}
