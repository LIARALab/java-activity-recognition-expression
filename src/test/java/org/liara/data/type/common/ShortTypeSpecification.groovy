package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class ShortTypeSpecification
  extends TypeSpecification<Short>
{
  @Override
  Type getType () {
    return new ShortType()
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
