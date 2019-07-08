package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableIntegerTypeSpecification
  extends NullableTypeSpecification<Integer>
{
  @Override
  Type getType () {
    return new NullableIntegerType()
  }

  @Override
  Integer getRandomValue () {
    return random.nextInt()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Integer.class
  }
}
