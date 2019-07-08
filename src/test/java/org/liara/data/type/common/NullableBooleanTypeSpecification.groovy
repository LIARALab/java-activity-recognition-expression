package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableBooleanTypeSpecification
  extends NullableTypeSpecification<Boolean>
{
  @Override
  Type getType () {
    return new NullableBooleanType()
  }

  @Override
  Boolean getRandomValue () {
    return random.nextBoolean()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    new BooleanType().javaClass == Boolean.class
  }
}
