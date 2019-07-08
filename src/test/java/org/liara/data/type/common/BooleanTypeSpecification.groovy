package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class BooleanTypeSpecification extends TypeSpecification<Boolean> {
  @Override
  Type getType () {
    return new BooleanType()
  }

  @Override
  Boolean getRandomValue () {
    return random.nextBoolean()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Boolean.class
  }
}
