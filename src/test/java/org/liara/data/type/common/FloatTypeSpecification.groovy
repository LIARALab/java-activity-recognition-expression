package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class FloatTypeSpecification
  extends TypeSpecification<Float>
{
  @Override
  Type getType () {
    return new FloatType()
  }

  @Override
  Float getRandomValue () {
    return random.nextFloat()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Float.class
  }
}
