package org.liara.data.model.descriptor

import org.liara.data.primitive.Primitives
import spock.lang.Specification

class StaticValueDescriptorSpecification extends Specification {
  def "#StaticValueDescriptor instantiate the description of a placeholder that await a value of a given type" () {
    expect: "#StaticValueDescriptor to instantiate the description of a placeholder that await a value of a given type"
    new StaticValueDescriptor(Primitives.STRING).type == Primitives.STRING
    new StaticValueDescriptor(Primitives.INTEGER).type == Primitives.INTEGER
  }

  def "#getChildren to return an empty view" () {
    expect: "#getChildren to return an empty view"
    new StaticValueDescriptor(Primitives.STRING).children.empty
    new StaticValueDescriptor(Primitives.INTEGER).children.empty
    new StaticValueDescriptor(Primitives.NULLABLE_BOOLEAN).children.empty
  }

  def "#setType allows to mutate the type of the descriptor" () {
    given: "a static value descriptor instance that await a value of a given type"
    final StaticValueDescriptor descriptor = new StaticValueDescriptor(Primitives.STRING)

    when: "we mutate the placeholder expected value type"
    descriptor.type = Primitives.FLOAT

    then: "we expect the type to have been muted"
    descriptor.type == Primitives.FLOAT
  }
}
