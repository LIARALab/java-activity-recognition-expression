package org.liara.expression

import org.liara.data.type.Type
import spock.lang.Specification

class PlaceholderSpecification extends Specification {
  def "#Placeholder allows to instantiate a new placeholder of a given type" () {
    expect: "to instantiate a new constant value of a given type"
    new Placeholder<>(Type.nonNullBoolean()).resultType == Type.nonNullBoolean()
    new Placeholder<>(Type.nullableInteger()).resultType == Type.nullableInteger()
  }
}
