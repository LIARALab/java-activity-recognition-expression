package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableDoubleTypeSpecification
  extends NullableTypeSpecification<Double>
{
  @Override
  Type getType () {
    return new NullableDoubleType()
  }

  @Override
  Double getRandomValue () {
    return random.nextDouble()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Double.class
  }
}
