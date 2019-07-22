package org.liara.data.model.descriptor

import org.liara.data.primitive.Primitives
import org.mockito.Mockito
import spock.lang.Specification

class ModelDescriptorFactorySpecification extends Specification {
  def "#empty creates an null descriptor" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    expect: "#empty to return a null descriptor"
    factory.empty() instanceof NullDescriptor
    factory.empty().children.empty
  }

  def "#value creates a value descriptor" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    expect: "#empty to return a value descriptor"
    factory.value(Primitives.FLOAT) instanceof ValueDescriptor
    factory.value(Primitives.STRING) instanceof ValueDescriptor
    factory.value(Primitives.FLOAT).type == Primitives.FLOAT
    factory.value(Primitives.STRING).type == Primitives.STRING
    factory.value(Primitives.FLOAT).children.empty
    factory.value(Primitives.STRING).children.empty
  }

  def "#tuple return a tuple descriptor from an array of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #tuple with the given array of descriptors"
    final TupleDescriptor descriptor = factory.tuple(descriptors)

    then: "we expect to get a valid tuple descriptor"
    descriptor.children.size == descriptors.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
    }
  }

  def "#tuple return a tuple descriptor from an iterator of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #tuple with an iterator over the given array of descriptors"
    final TupleDescriptor descriptor = factory.tuple(Arrays.asList(descriptors).iterator())

    then: "we expect to get a valid tuple descriptor"
    descriptor.children.size == descriptors.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
    }
  }

  def "#tuple return a tuple descriptor from a collection of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #tuple with the given array of descriptors as a collection"
    final TupleDescriptor descriptor = factory.tuple(Arrays.asList(descriptors))

    then: "we expect to get a valid tuple descriptor"
    descriptor.children.size == descriptors.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
    }
  }

  def "#object return an object descriptor from an array of keys and an array of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays"
    final ObjectDescriptor descriptor = factory.object(keys, descriptors)

    then: "we expect to get a valid object descriptor"
    descriptor.children.size == descriptors.length
    descriptor.keys.size == keys.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
      descriptor.keys.get(index) == keys[index]
    }
  }

  def "#object throws if the key array does not have the same length than the descriptor array" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays"
    factory.object(keys, descriptors)

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the descriptor array does not have the same length than the key array" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays"
    factory.object(keys, descriptors)

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the key array contains duplicates" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays"
    factory.object(keys, descriptors)

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object return an object descriptor from a list of keys and a list of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as lists"
    final ObjectDescriptor descriptor = factory.object(
      Arrays.asList(keys),
      Arrays.asList(descriptors)
    )

    then: "we expect to get a valid object descriptor"
    descriptor.children.size == descriptors.length
    descriptor.keys.size == keys.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
      descriptor.keys.get(index) == keys[index]
    }
  }

  def "#object throws if the key list does not have the same length than the descriptor list" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as lists"
    factory.object(
      Arrays.asList(keys),
      Arrays.asList(descriptors)
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the descriptor list does not have the same length than the key list" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as lists"
    factory.object(
      Arrays.asList(keys),
      Arrays.asList(descriptors)
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the key list contains duplicates" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as lists"
    factory.object(
      Arrays.asList(keys),
      Arrays.asList(descriptors)
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object return an object descriptor from an iterator of keys and an iterator of descriptors" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as iterators"
    final ObjectDescriptor descriptor = factory.object(
      Arrays.asList(keys).iterator(),
      Arrays.asList(descriptors).iterator()
    )

    then: "we expect to get a valid object descriptor"
    descriptor.children.size == descriptors.length
    descriptor.keys.size == keys.length

    for (int index = 0; index < descriptor.children.size; ++index) {
      descriptor.children.get(index) == descriptors[index]
      descriptor.keys.get(index) == keys[index]
    }
  }

  def "#object throws if the key iterator does not have the same length than the descriptor iterator" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as iterators"
    factory.object(
      Arrays.asList(keys).iterator(),
      Arrays.asList(descriptors).iterator()
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the descriptor iterator does not have the same length than the key iterator" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as iterators"
    factory.object(
      Arrays.asList(keys).iterator(),
      Arrays.asList(descriptors).iterator()
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object throws if the key iterator contains duplicates" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "an array of keys"
    final String[] keys = [
      "first",
      "second",
      "third",
      "third",
      "last"
    ]

    and: "an array of descriptors"
    final ModelElementDescriptor[] descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given arrays as iterators"
    factory.object(
      Arrays.asList(keys).iterator(),
      Arrays.asList(descriptors).iterator()
    )

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#object return an object descriptor from a map" () {
    given: "a model descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "a map of descriptors"
    final Map<String, ModelElementDescriptor> fields = [
      "first": Mockito.mock(ModelElementDescriptor.class),
      "second": Mockito.mock(ModelElementDescriptor.class),
      "third": Mockito.mock(ModelElementDescriptor.class),
      "fourth": Mockito.mock(ModelElementDescriptor.class),
      "last": Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we call #object with the given map of fields"
    final ObjectDescriptor descriptor = factory.object(fields)

    then: "we expect to get a valid object descriptor"
    descriptor.children.size == fields.size()
    descriptor.keys.size == fields.size()

    final Iterator<Map.Entry<String, ModelElementDescriptor>> iterator = fields.entrySet().iterator()
    int index = 0

    while (iterator.hasNext()) {
      final Map.Entry<String, ModelElementDescriptor> entry = iterator.next()

      descriptor.keys.get(index) == entry.key
      descriptor.children.get(index) == entry.value

      index += 1
    }
  }
}
