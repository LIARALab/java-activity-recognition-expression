package org.liara.expression


import org.liara.data.type.DataType
import spock.lang.Specification

class PlaceholderSpecification extends Specification {
  def "#Placeholder allows to instantiate a new placeholder of a given type" () {
    expect: "to instantiate a new constant value of a given type"
    new Placeholder<>(DataType.nonNullBoolean()).resultType == DataType.nonNullBoolean()
    new Placeholder<>(DataType.nullableInteger()).resultType == DataType.nullableInteger()
  }
}
