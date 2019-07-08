package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class DoubleTypeSpecification
  extends TypeSpecification<Double>
{
  @Override
  Type getType () {
    return new DoubleType()
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
