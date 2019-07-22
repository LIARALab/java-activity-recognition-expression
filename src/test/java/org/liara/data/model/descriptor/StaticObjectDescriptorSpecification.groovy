package org.liara.data.model.descriptor

import org.mockito.Mockito
import spock.lang.Specification


class StaticObjectDescriptorSpecification
  extends Specification {
  def "#StaticObjectDescriptor instantiate the description of an empty object" () {
    expect: "#StaticObjectDescriptor to instantiate the description of an empty object"
    new StaticObjectDescriptor().children.empty
    new StaticObjectDescriptor().keys.empty
  }

  def "#set set a field of the given object descriptor" () {
    given: "an empty object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()

    and: "some child descriptors"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "some field names"
    final List<String> fields = [
      "firstName",
      "lastName",
      "age",
      "gender",
      "job"
    ]

    when: "we set the given fields with the given child descriptors"
    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.set(fields[index], descriptors[index])
    }

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == descriptors.size()
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == descriptors.get(index)
      descriptor.keys.get(index) == fields.get(index)
      descriptor.getFieldOf(fields.get(index)) == index
    }
  }

  def "#set allows to replace an existing field value" () {
    given: "an empty object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()

    and: "some child descriptors"
    final List<ModelElementDescriptor> descriptors = [
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class),
      Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "some field names"
    final List<String> fields = [
      "firstName",
      "lastName",
      "age",
      "gender",
      "job"
    ]

    and: "a value to use as a replacement"
    final ModelElementDescriptor replacement = Mockito.mock(ModelElementDescriptor.class)

    when: "we set the given fields with the given child descriptors"
    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.set(fields[index], descriptors[index])
    }

    and: "when we reset the lastName field"
    descriptor.set("lastName", replacement)

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == descriptors.size()
    descriptor.children.size == descriptors.size()

    for (int index = 0; index < descriptors.size(); ++index) {
      descriptor.children.get(index) == (index == 1 ? replacement : descriptors.get(index))
      descriptor.keys.get(index) == fields.get(index)
      descriptor.getFieldOf(fields.get(index)) == index
    }
  }

  def "#rename allows to rename an existing field" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we rename a field of the descriptor"
    descriptor.rename("lastName", "familyName")

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == fields.size()
    descriptor.children.size == fields.size()

    descriptor.keys.get(0) == "firstName"
    descriptor.keys.get(1) == "familyName"
    descriptor.keys.get(2) == "age"

    descriptor.children.get(0) == fields["firstName"]
    descriptor.children.get(1) == fields["lastName"]
    descriptor.children.get(2) == fields["age"]

    !descriptor.containsKey("lastName")
    descriptor.containsKey("familyName")

    descriptor.getFieldOf("familyName") == 1
  }

  def "#rename throws an error if the field to rename does not exists" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we rename a field of the descriptor"
    descriptor.rename("pwet", "familyName")

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#rename throws an error if the new name of the field already exists" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we rename a field of the descriptor"
    descriptor.rename("lastName", "firstName")

    then: "we expect the method to throw"
    thrown(IllegalArgumentException.class)
  }

  def "#rename allows to rename an existing field by using its index" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we rename a field of the descriptor"
    descriptor.rename(1, "familyName")

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == fields.size()
    descriptor.children.size == fields.size()

    descriptor.keys.get(0) == "firstName"
    descriptor.keys.get(1) == "familyName"
    descriptor.keys.get(2) == "age"

    descriptor.children.get(0) == fields["firstName"]
    descriptor.children.get(1) == fields["lastName"]
    descriptor.children.get(2) == fields["age"]

    !descriptor.containsKey("lastName")
    descriptor.containsKey("familyName")

    descriptor.getFieldOf("familyName") == 1
  }

  def "#rename throws an error if the field index used does not exists" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we rename a field of the descriptor"
    descriptor.rename(50, "firstName")

    then: "we expect the method to throw"
    thrown(NoSuchElementException.class)
  }

  def "#remove remove a field of the object" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class),
      "job": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we remove a field of the descriptor"
    descriptor.remove("lastName")

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == fields.size() - 1
    descriptor.children.size == fields.size() - 1

    descriptor.keys.get(0) == "firstName"
    descriptor.keys.get(1) == "age"
    descriptor.keys.get(2) == "job"

    descriptor.children.get(0) == fields["firstName"]
    descriptor.children.get(1) == fields["age"]
    descriptor.children.get(2) == fields["job"]

    descriptor.getFieldOf("firstName") == 0
    descriptor.getFieldOf("age") == 1
    descriptor.getFieldOf("job") == 2

    !descriptor.containsKey("lastName")
  }

  def "#remove throws an error if the field to remove does not exist" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class),
      "job": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we remove a field of the descriptor"
    descriptor.remove("pwet")

    then: "we expect the object descriptor to throw"
    thrown(NoSuchElementException.class)
  }

  def "#remove allows to use a field index" () {
    given: "some fields"
    final Map<String, ModelElementDescriptor> fields = [
      "firstName": Mockito.mock(ModelElementDescriptor.class),
      "lastName": Mockito.mock(ModelElementDescriptor.class),
      "age": Mockito.mock(ModelElementDescriptor.class),
      "job": Mockito.mock(ModelElementDescriptor.class)
    ]

    and: "an initialized object descriptor"
    final StaticObjectDescriptor descriptor = new StaticObjectDescriptor()
    fields.forEach({ x, y -> descriptor.set(x, y) })

    when: "we remove a field of the descriptor"
    descriptor.remove(1)

    then: "we expect the object descriptor to mutate accordingly"
    descriptor.keys.size == fields.size() - 1
    descriptor.children.size == fields.size() - 1

    descriptor.keys.get(0) == "firstName"
    descriptor.keys.get(1) == "age"
    descriptor.keys.get(2) == "job"

    descriptor.children.get(0) == fields["firstName"]
    descriptor.children.get(1) == fields["age"]
    descriptor.children.get(2) == fields["job"]

    descriptor.getFieldOf("firstName") == 0
    descriptor.getFieldOf("age") == 1
    descriptor.getFieldOf("job") == 2

    !descriptor.containsKey("lastName")
  }
}
