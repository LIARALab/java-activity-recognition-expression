package org.liara.expression

import org.liara.data.primitive.Primitives
import spock.lang.Specification

class PlaceholderSpecification extends Specification {
  def "#Placeholder allows to instantiate a new placeholder of a given type" () {
    expect: "to instantiate a new constant value of a given type"
    new StaticPlaceholder<>(Primitives.BOOLEAN).resultType == Primitives.BOOLEAN
    new StaticPlaceholder<>(Primitives.NULLABLE_INTEGER).resultType == Primitives.NULLABLE_INTEGER
  }
}
