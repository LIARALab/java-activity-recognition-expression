package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class NullableFloatTypeSpecification
  extends TypeSpecification<Float>
{
  @Override
  Type getType () {
    return new NullableFloatType()
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
