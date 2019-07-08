package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableShortTypeSpecification
  extends NullableTypeSpecification<Short>
{
  @Override
  Type getType () {
    return new NullableShortType()
  }

  @Override
  Short getRandomValue () {
    return (short) random.nextInt()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Short.class
  }
}
