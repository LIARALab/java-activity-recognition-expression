package org.liara.data.model.descriptor

import org.mockito.Mockito
import spock.lang.Specification

class StaticTupleDescriptorSpecification
  extends Specification {
  def "#StaticTupleDescriptor instantiate the description of an empty tuple" () {
    expect: "#StaticTupleDescriptor to instantiate the description of an empty tuple"
    new StaticTupleDescriptor().children.empty
  }

  def "#setChildren allows set the children collection of this tuple by using an iterator" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we set the children of the empty tuple by using an iterator over the given descriptors"
    descriptor.setChildren(descriptors.iterator())

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
    }
  }

  def "#setChildren allows set the children collection of this tuple by using a collection of children" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we set the children of the empty tuple by using an iterator over the given descriptors"
    descriptor.setChildren(descriptors)

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
    }
  }

  def "#append append a children at the end of the tuple" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we append each children element at the end of the empty tuple"
    for (final ModelElementDescriptor child : descriptors) {
      descriptor.append(child)
    }

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
    }
  }

  def "#insert insert a children at a given location" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we insert each children element into the empty tuple"
    final int[] indexes = 0..(descriptors.size() - 1)

    Collections.shuffle(Arrays.asList(indexes))

    for (final int index : indexes) {
      if (index >= descriptor.children.size) {
        descriptor.insert(descriptor.children.size, descriptors.get(index))
      } else {
        descriptor.insert(index, descriptors.get(index))
      }
    }

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
    }
  }

  def "#remove remove a children of the tuple" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we add all children to the empty tuple"
    descriptor.setChildren(descriptors)

    and: "remove the third child of the tuple"
    descriptor.remove(2)

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptors.remove(2)
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
    }
  }

  def "#clear remove all children of the tuple" () {
    given: "an empty static tuple descriptor"
    final StaticTupleDescriptor descriptor = new StaticTupleDescriptor()

    and: "a collection of child element"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    when: "we add all children to the empty tuple"
    descriptor.setChildren(descriptors)

    and: "clear the resulting descriptor"
    descriptor.clear()

    then: "we expect the children view of the tuple descriptor to have been updated in accordance"
    descriptor.children.empty
  }
}
