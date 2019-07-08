package org.liara.data.type.common

import org.liara.data.type.NullableTypeSpecification
import org.liara.data.type.Type

class NullableLongTypeSpecification
  extends NullableTypeSpecification<Long>
{
  @Override
  Type getType () {
    return new NullableLongType()
  }

  @Override
  Long getRandomValue () {
    return random.nextLong()
  }

  def "#getJavaClass return the java type able to store this type of value" () {
    expect: "#getJavaClass to return the java type able to store this type of value"
    type.javaClass == Long.class
  }
}
