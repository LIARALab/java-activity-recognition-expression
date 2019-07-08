package org.liara.data.type.common

import org.liara.data.type.Type
import org.liara.data.type.TypeSpecification

class IntegerTypeSpecification
  extends TypeSpecification<Integer>
{
  @Override
  Type getType () {
    return new IntegerType()
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
