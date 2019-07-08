package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class LongTypeSpecification
  extends TypeSpecification<Long>
{
  @Override
  Type getType () {
    return new LongType()
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
